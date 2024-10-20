package com.PD.uberShuffle.httpAdapter;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// FIXME: I think a lot of the problems with this class are stemming from the fact that we are
//  storing the accessToken here. Soon we'll have an authentication class that will be much better
//  suited for holding it. Once that is done, look to extract the authentication from here.
public class OkHttpHttpRequestAdapter implements HttpRequestAdapter {

  public static final String AUTH_HEADER = "Authorization";
  public static final String APPLICATION_JSON_CHARSET = "application/json; charset=utf-8";
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
    final MediaType jsonMediaType = MediaType.get(APPLICATION_JSON_CHARSET);
    RequestBody body = RequestBody.create(new JSONObject(bodyParameters).toString(), jsonMediaType);
    Request request = new Request.Builder()
        .post(body)
        .header(AUTH_HEADER, accessToken)
        .url(url)
        .build();

    return makeRequest(request);
  }

  public JSONObject makePostRequest(String url, Map<String, String> bodyParameters, Map<String, String> headers) {
    Map<String, String> headersWithAccessToken = addAccessTokenToHeaders(headers);
    final MediaType json = MediaType.get(APPLICATION_JSON_CHARSET);
    RequestBody body = RequestBody.create(new JSONObject(bodyParameters).toString(), json);
    Request request = new Request.Builder()
        .post(body)
        .headers(Headers.of(headersWithAccessToken))
        .url(url)
        .build();

    return makeRequest(request);
  }

  @Override
  public JSONObject makePostRequestNoAuth(String url, Map<String, String> bodyParams,
      Map<String, String> headers) {
//    final MediaType json = MediaType.get(APPLICATION_JSON_CHARSET);
//    final MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
    FormBody.Builder formBuilder = new FormBody.Builder();
    bodyParams.forEach(formBuilder::add);
    RequestBody body = formBuilder.build();
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
