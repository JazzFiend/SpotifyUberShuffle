package com.example.spotifyubershuffle.httpAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.spotifyubershuffle.App.getContext;

public class HttpVolleyRequestSender implements HttpRequestSender {
    private final String accessToken;
    private final RequestQueue requestQueue;

    public HttpVolleyRequestSender(String accessToken) {
        this.accessToken = accessToken;
        this.requestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public JSONObject sendRequest(String url, int requestMethodInteger, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = createJsonObjectRequest(url, future, requestMethodInteger, bodyParameters);
        requestQueue.add(request);
        return future.get();
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
