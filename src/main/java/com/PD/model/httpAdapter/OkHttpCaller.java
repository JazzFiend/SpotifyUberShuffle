package com.PD.model.httpAdapter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class OkHttpCaller implements HttpCaller {
    private final OkHttpClient http;

    // TODO: OkHttpCaller is meant for Java Android. Find a better package to use.
    public OkHttpCaller() {
        this.http = new OkHttpClient();
    }

    @Override
    public JSONObject makeRequest(Request request) {
        int retryCountMax = 5;
        for (int i = 0; i < retryCountMax; i++) {
            try (Response response = http.newCall(request).execute()) {
                String res = response.body().string();
                JSONObject jsonRes = new JSONObject(res);
                if (isError(jsonRes)) {
                    handleError(response, jsonRes);
                } else {
                    return jsonRes;
                }
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
        throw new RuntimeException("JSON Response was never populated");
    }

    private boolean isError(JSONObject jsonRes) {
        return !jsonRes.isNull("error");
    }

    private void handleError(Response response, JSONObject jsonRes) throws InterruptedException {
        JSONObject errorJson = (JSONObject) jsonRes.get("error");
        if ((Integer) errorJson.get("status") == 429) {
            if (response.headers().get("Retry-After") != null) {
                sleepBasedOnRetry(response.headers().get("Retry-After"));
            }
        } else {
            throw new RuntimeException(String.format("%s - %s", errorJson.get("status").toString(), errorJson.get("message").toString()));
        }
    }

    private static void sleepBasedOnRetry(String sleepTime) throws InterruptedException {
        int secondsToSleep = Integer.parseInt(sleepTime);
        Thread.sleep(secondsToSleep * 1000L + 250);
    }
}
