package com.example.spotifyubershuffle.HttpAdapter;

import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.spotifyubershuffle.App.getContext;

public class HttpAdapterVolleyImpl implements HttpAdapter {
    private final String accessToken;
    private final RequestQueue requestQueue;

    public HttpAdapterVolleyImpl(String accessToken) {
        this.accessToken = accessToken;
        this.requestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public JSONObject makeGetRequest(String url) throws InterruptedException, ExecutionException {
        return makeRequest(url, Request.Method.GET);
    }

    @Override
    public JSONObject makePostRequest(String url) throws InterruptedException, ExecutionException {
        return makePostRequest(url, new HashMap<>());
    }

    @Override
    public JSONObject makePostRequest(String url, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException {
        return makeRequest(url, Request.Method.POST, bodyParameters);
    }

    private JSONObject makeRequest(String url, int requestMethodInteger) throws InterruptedException, ExecutionException {
        return makeRequest(url, requestMethodInteger, new HashMap<>());
    }

    private JSONObject makeRequest(String url, int requestMethodInteger, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException {
        int retryCountMax = 5;
        for(int i = 0; i < retryCountMax; i++) {
            try {
                return sendRequest(url, requestMethodInteger, bodyParameters);
            } catch(InterruptedException | ExecutionException e) {
                if(isClientError(e)) {
                    ClientError clientError = (ClientError)e.getCause();
                    if(isRateLimitError(clientError)) {
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

    private JSONObject sendRequest(String url, int requestMethodInteger, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = createJsonObjectRequest(url, future, requestMethodInteger, bodyParameters);
        requestQueue.add(request);
        return future.get();
    }

    private boolean isClientError(Exception e) {
        return e.getCause().toString().equals("com.android.volley.ClientError");
    }

    private void sleepBasedOnRetry(ClientError clientError) throws InterruptedException {
        int secondsToSleep = Integer.parseInt(clientError.networkResponse.headers.get("Retry-After"));
        Thread.sleep(secondsToSleep * 1000);
    }

    private boolean isRateLimitError(ClientError clientError) {
        return clientError.networkResponse.statusCode == 429 && clientError.networkResponse.headers.containsKey("Retry-After");
    }

    private JsonObjectRequest createJsonObjectRequest(String url, RequestFuture<JSONObject> future, int requestMethodInteger, Map<String, String> bodyParams) {
        JSONObject jsonBody = new JSONObject(bodyParams);
        return new JsonObjectRequest(requestMethodInteger, url, jsonBody, future, future) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", accessToken);
                return params;
            }
        };
    }
}
