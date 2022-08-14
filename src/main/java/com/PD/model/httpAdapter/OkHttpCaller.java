package com.PD.model.httpAdapter;

import java.io.IOException;
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
                String responseBody = response.body().string();
                JSONObject jsonRes = new JSONObject(responseBody);
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

    private boolean isErrorPresent(JSONObject jsonRes) {
        return !jsonRes.isNull("error");
    }

    private void handleResponseError(Response response, JSONObject jsonResponse) {
        JSONObject error = (JSONObject) jsonResponse.get("error");
        if ((Integer) error.get("status") == 429) {
            if (response.headers().get("Retry-After") != null) {
                sleepBasedOnRetry(response.headers().get("Retry-After"));
            }
        } else {
            throw new RuntimeException(String.format("%s - %s", error.get("status").toString(), error.get("message").toString()));
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
