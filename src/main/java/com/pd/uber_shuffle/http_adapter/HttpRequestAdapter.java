package com.pd.uber_shuffle.http_adapter;

import org.json.JSONObject;

import java.util.Map;

// FIXME: This interface is getting pretty big. Once the authorization stuff is done, see if we can slim this down.

public interface HttpRequestAdapter {
  JSONObject makeGetRequest(String url);

  JSONObject makeGetRequest(String url, Map<String, String> headers);

  JSONObject makePostRequest(String url);

  JSONObject makePostRequest(String url, Map<String, String> body);

  JSONObject makePostRequest(String url, Map<String, String> bodyParams, Map<String, String> headers);

  JSONObject makePostRequestNoAuth(String url, Map<String, String> bodyParams, Map<String, String> headers);

  void setAccessToken(String accessToken);
}
