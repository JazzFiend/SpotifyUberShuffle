package com.PD.uberShuffle.httpAdapter;

import static org.hamcrest.MatcherAssert.assertThat;
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

public class OkHttpHttpRequestAdapterTest {
  private final String accessToken = "Bearer 123";
  private HttpRequestAdapter http;

  @BeforeEach
  public void setup() {
    http = new OkHttpHttpRequestAdapter(new OkHttpCallerMock());
    http.setAccessToken(accessToken);
  }

  @Test
  public void getRequestTest() {
    final String url = "http://www.getrequest.com/";

    StringWriter sw = new StringWriter();
    new JSONWriter(sw)
        .object()
        .key("Authorization")
        .value(accessToken)
        .key("url")
        .value(url)
        .key("method")
        .value("GET")
        .endObject();
    JSONObject expected = new JSONObject(sw.toString());
    JSONObject actual = http.makeGetRequest(url);

    assertEquals(actual.get("Authorization"), expected.get("Authorization"));
    assertEquals(actual.get("url"), expected.get("url"));
    assertEquals(actual.get("method"), expected.get("method"));
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

  @Test
  void postRequestWithHeadersTest() throws IOException {
    OkHttpCaller mockCaller = mock();
    when(mockCaller.makeRequest(any(Request.class))).thenReturn(new JSONObject());

    OkHttpHttpRequestAdapter httpWithMock = new OkHttpHttpRequestAdapter(mockCaller);

    final String url = "https://www.postrequest.com/";
    Map<String, String> bodyParams = new HashMap<>();
    bodyParams.put("param", "value");
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", accessToken);
    headers.put("AnotherHeader", "Value");

    final MediaType json = MediaType.get("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(new JSONObject(bodyParams).toString(), json);
    Request expectedRequest = new Builder()
        .post(body)
        .header("Authorization", accessToken)
        .header("AnotherHeader", "Value")
        .url(url)
        .build();

    httpWithMock.makePostRequest(url, bodyParams, headers);

    ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
    verify(mockCaller, times(1)).makeRequest(requestCaptor.capture());
    Request actualRequest = requestCaptor.getValue();

    assertThat(expectedRequest.url(), is(actualRequest.url()));
    assertThat(expectedRequest.method(), is(actualRequest.method()));
    assertThat(expectedRequest.headers().toMultimap(), is(actualRequest.headers().toMultimap()));
    assertThat(expectedRequest.body().contentType(), is(actualRequest.body().contentType()));
    assertThat(expectedRequest.body().contentLength(), is(actualRequest.body().contentLength()));
  }
}
