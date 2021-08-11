package com.PD.httpAdapter;

import org.json.JSONObject;

import java.util.Map;

public interface HttpRequestAdapter {
    JSONObject makeGetRequest(String url);

    JSONObject makePostRequest(String url);

    JSONObject makePostRequest(String url, Map<String, String> body);
}
