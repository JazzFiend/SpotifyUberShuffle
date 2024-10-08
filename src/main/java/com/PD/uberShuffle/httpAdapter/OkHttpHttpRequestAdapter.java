package com.PD.uberShuffle.httpAdapter;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OkHttpHttpRequestAdapter implements HttpRequestAdapter {

  public static final String AUTH_HEADER = "Authorization";
  private final HttpCaller httpCaller;
  private String accessToken;

  public OkHttpHttpRequestAdapter(HttpCaller httpCaller) {
    this.httpCaller = httpCaller;
    this.accessToken = "";
  }

  @Override
  public JSONObject makeGetRequest(String url) {
    return makeGetRequest(url, new HashMap<>());
  }

  @Override
  public JSONObject makeGetRequest(String url, Map<String, String> headers) {
    Map<String, String> headersWithAccessToken = addAccessTokenToHeaders(headers);
    Request request = new Request.Builder()
        .get()
        .headers(Headers.of(headersWithAccessToken))
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
    final MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(new JSONObject(bodyParameters).toString(), jsonMediaType);
    Request request = new Request.Builder()
        .post(body)
        .header(AUTH_HEADER, accessToken)
        .url(url)
        .build();

    return makeRequest(request);
  }

  public JSONObject makePostRequest(String url, Map<String, String> bodyParameters, Map<String, String> headers) {
    final MediaType json = MediaType.get("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(new JSONObject(bodyParameters).toString(), json);
    Request request = new Request.Builder()
        .post(body)
        .headers(Headers.of(headers))
        .url(url)
        .build();

    return makeRequest(request);
  }

  @Override
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  private JSONObject makeRequest(Request request) {
    return httpCaller.makeRequest(request);
  }

  @NotNull
  private Map<String, String> addAccessTokenToHeaders(Map<String, String> headers) {
    Map<String, String> headersWithAccessToken = new HashMap<>(headers);
    headersWithAccessToken.put(AUTH_HEADER, accessToken);
    return headersWithAccessToken;
  }
}
