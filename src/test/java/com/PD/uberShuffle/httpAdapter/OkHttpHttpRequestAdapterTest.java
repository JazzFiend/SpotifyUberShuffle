package com.PD.uberShuffle.httpAdapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.PD.mocks.OkHttpCallerMock;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class OkHttpHttpRequestAdapterTest {
  private final String accessToken = "Bearer 123";
  private HttpRequestAdapter http;
  private OkHttpCaller mockCaller;
  private OkHttpHttpRequestAdapter httpWithMock;

  @BeforeEach
  public void setup() {
    http = new OkHttpHttpRequestAdapter(new OkHttpCallerMock());
    http.setAccessToken(accessToken);
    mockCaller = mock();
    when(mockCaller.makeRequest(any(Request.class))).thenReturn(new JSONObject());
    httpWithMock = new OkHttpHttpRequestAdapter(mockCaller);
    httpWithMock.setAccessToken(accessToken);
  }

  // Complete coverage with get request
  @Test
  void getRequestTest() {
    final String url = "https://www.getrequest.com/";
    Request expectedRequest = new Builder()
        .get()
        .url(url)
        .header("Authorization", "Bearer 123")
        .build();
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer 123");

    httpWithMock.makeGetRequest(url, headers);
    Request actualRequest = checkMakeRequestAndExtractParameter();

    assertRequestsEqual(expectedRequest, actualRequest);
    assertThat(expectedRequest.body(), is(nullValue()));
  }

  @Test
  public void postRequestTest() {
    final String url = "http://www.postrequest.com/";

    StringWriter sw = new StringWriter();
    new JSONWriter(sw)
        .object()
        .key("Authorization")
        .value(accessToken)
        .key("url")
        .value(url)
        .key("method")
        .value("POST")
        .endObject();
    JSONObject expected = new JSONObject(sw.toString());
    JSONObject actual = http.makePostRequest(url);

    assertEquals(actual.get("Authorization"), expected.get("Authorization"));
    assertEquals(actual.get("url"), expected.get("url"));
    assertEquals(actual.get("method"), expected.get("method"));
  }

  @Test
  public void postRequestWithBodyParamsTest() {
    final String url = "http://www.postrequest.com/";

    StringWriter sw = new StringWriter();
    new JSONWriter(sw)
        .object()
        .key("Authorization")
        .value(accessToken)
        .key("url")
        .value(url)
        .key("method")
        .value("POST")
        .endObject();
    JSONObject expected = new JSONObject(sw.toString());
    Map<String, String> bodyParams = new HashMap<>();
    bodyParams.put("param", "value");

    JSONObject actual = http.makePostRequest(url, bodyParams);

    assertEquals(actual.get("Authorization"), expected.get("Authorization"));
    assertEquals(actual.get("url"), expected.get("url"));
    assertEquals(actual.get("method"), expected.get("method"));
  }

  // Okay, this test is working. But the rest of the tests aren't actually checking anything. I'm
  // going to fix up the other tests using Mockito.
  @Test
  void postRequestWithHeadersTest() throws IOException {
    final String url = "https://www.postrequest.com/";
    Map<String, String> bodyParams = new HashMap<>();
    bodyParams.put("param", "value");
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", accessToken);
    headers.put("AnotherHeader", "Value");

    final MediaType json = MediaType.get("application/json; charset=utf-8");
    RequestBody expectedBody = RequestBody.create(new JSONObject(bodyParams).toString(), json);
    Request expectedRequest = new Builder()
        .post(expectedBody)
        .header("Authorization", accessToken)
        .header("AnotherHeader", "Value")
        .url(url)
        .build();

    httpWithMock.makePostRequest(url, bodyParams, headers);
    Request actualRequest = checkMakeRequestAndExtractParameter();

    assertRequestsEqual(expectedRequest, actualRequest);
    assertBodyParamsEqual(expectedRequest, actualRequest);
  }

  private Request checkMakeRequestAndExtractParameter() {
    ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
    verify(mockCaller, times(1)).makeRequest(requestCaptor.capture());
    return requestCaptor.getValue();
  }

  private static void assertRequestsEqual(Request expectedRequest, Request actualRequest) {
    assertThat(expectedRequest.url(), is(actualRequest.url()));
    assertThat(expectedRequest.method(), is(actualRequest.method()));
    assertThat(expectedRequest.headers().toMultimap(), is(actualRequest.headers().toMultimap()));
  }

  private static void assertBodyParamsEqual(Request expectedRequest, Request actualRequest) throws IOException {
    assertThat(expectedRequest.body().contentType(), is(actualRequest.body().contentType()));
    assertThat(expectedRequest.body().contentLength(), is(actualRequest.body().contentLength()));
  }

}
