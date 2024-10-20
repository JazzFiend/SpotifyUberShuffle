package com.PD.uberShuffle.httpAdapter;

import java.io.IOException;
import java.util.Objects;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class OkHttpCaller implements HttpCaller {
    private final HumbleOkHttpCaller humbleHttp;

    public OkHttpCaller(HumbleOkHttpCaller humbleHttp) {
        this.humbleHttp = humbleHttp;
    }

    @Override
    public JSONObject makeRequest(Request request) {
        int retryCountMax = 5;
        for (int i = 0; i < retryCountMax; i++) {
            try (Response response = humbleHttp.makeNewCall(request)) {

                // Can only pull body once... That's dumb.
//                System.out.println("Response Code: " + response.code());
//                System.out.println("Response Body: " + response.body().string());

                JSONObject jsonRes = extractBody(response);
                if (isErrorPresent(jsonRes)) {
                    handleResponseError(response, jsonRes);
                } else {
                    return jsonRes;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        throw new RuntimeException("JSON Response was never populated");
    }

    private static JSONObject extractBody(Response response) throws IOException {
        return new JSONObject(Objects.requireNonNull(response.body()).string());
    }

    private boolean isErrorPresent(JSONObject jsonRes) {
        return !jsonRes.isNull("error");
    }

    private void handleResponseError(Response response, JSONObject jsonResponse) {
        JSONObject error = (JSONObject) jsonResponse.get("error");
        if (isStatusTooManyRequests(error)) {
            pauseRequests(response);
        } else {
            throw new RuntimeException(String.format("%s - %s", error.get("status").toString(), error.get("message").toString()));
        }
    }

    private static boolean isStatusTooManyRequests(JSONObject error) {
        return (Integer) error.get("status") == 429;
    }

    private static void pauseRequests(Response response) {
        String retryTime = response.headers().get("Retry-After");
        if (retryTime != null) {
            sleepBasedOnRetry(retryTime);
        }
    }

    private static void sleepBasedOnRetry(String sleepTime) {
        int secondsToSleep = Integer.parseInt(sleepTime);
        try {
          Thread.sleep(secondsToSleep * 1000L + 250);
        } catch (InterruptedException e) {
          System.out.println(e.getMessage());
        }
    }
}
