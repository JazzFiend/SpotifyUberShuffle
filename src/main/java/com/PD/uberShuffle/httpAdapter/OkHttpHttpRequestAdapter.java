package com.PD.uberShuffle.httpAdapter;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OkHttpHttpRequestAdapter implements HttpRequestAdapter {
    private final HttpCaller httpCaller;
    private String accessToken;

    public OkHttpHttpRequestAdapter(HttpCaller httpCaller) {
        this.httpCaller = httpCaller;
        this.accessToken = "";
    }

    @Override
    public JSONObject makeGetRequest(String url) {
        Request request = new Request.Builder()
            .get()
            .header("Authorization", accessToken)
            .url(url)
            .build();

        return makeRequest(request);
    }

    @Override
    public JSONObject makePostRequest(String url) {
        return makePostRequest(url, new HashMap<>());
    }

    @Override
    public JSONObject makePostRequest(String url, Map<String, String> bodyParameters) {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(new JSONObject(bodyParameters).toString(), JSON);
        Request request = new Request.Builder()
          .post(body)
          .header("Authorization", accessToken)
          .url(url)
          .build();

        return makeRequest(request);
    }

    private JSONObject makeRequest(Request request) {
        return httpCaller.makeRequest(request);
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
