package com.PD.uberShuffle.httpAdapter;

import org.json.JSONObject;

import java.util.Map;

public interface HttpRequestAdapter {
    JSONObject makeGetRequest(String url);

    JSONObject makeGetRequest(String url, Map<String, String> headers);

    JSONObject makePostRequest(String url);

    JSONObject makePostRequest(String url, Map<String, String> body);

    JSONObject makePostRequest(String url, Map<String, String> bodyParams, Map<String, String> headers);

    void setAccessToken(String accessToken);
}
