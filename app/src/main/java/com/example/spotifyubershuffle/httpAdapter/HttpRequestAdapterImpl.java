package com.example.spotifyubershuffle.httpAdapter;

import com.android.volley.ClientError;
import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HttpRequestAdapterImpl implements HttpRequestAdapter {
    private final HttpRequestSender requestSender;

    public HttpRequestAdapterImpl(HttpRequestSender requestSender) {
        this.requestSender = requestSender;
    }

    @Override
    public JSONObject makeGetRequest(String url) throws InterruptedException, ExecutionException {
        return makeRequest(url, Request.Method.GET, new HashMap<>());
    }

    @Override
    public JSONObject makePostRequest(String url) throws InterruptedException, ExecutionException {
        return makePostRequest(url, new HashMap<>());
    }

    @Override
    public JSONObject makePostRequest(String url, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException {
        return makeRequest(url, Request.Method.POST, bodyParameters);
    }

    private JSONObject makeRequest(String url, int requestMethodInteger, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException {
        int retryCountMax = 5;
        for (int i = 0; i < retryCountMax; i++) {
            try {
                return requestSender.sendRequest(url, requestMethodInteger, bodyParameters);
            } catch (InterruptedException | ExecutionException e) {
                //TODO: The ErrorChecker relies on Volley and this class is supposed to be abstracted from this class.
                if (VolleyErrorChecker.isClientError(e)) {
                    ClientError clientError = (ClientError) e.getCause();
                    if (VolleyErrorChecker.isRateLimitError(clientError)) {
                        sleepBasedOnRetry(clientError);
                    } else {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
        throw new RuntimeException("JSON Response was never populated");
    }

    private void sleepBasedOnRetry(ClientError clientError) throws InterruptedException {
        int secondsToSleep = Integer.parseInt(clientError.networkResponse.headers.get("Retry-After"));
        Thread.sleep(secondsToSleep * 1000 + 250);
    }
}
