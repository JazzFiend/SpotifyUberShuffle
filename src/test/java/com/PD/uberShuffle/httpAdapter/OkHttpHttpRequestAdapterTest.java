package com.PD.uberShuffle.httpAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.PD.mocks.OkHttpCallerMock;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  // The headers should be working now. But thanks to the mock object, am I actually testing anything here?
  @Test
  void postRequestWithHeadersTest() {
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
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", accessToken);
    headers.put("AnotherHeader", "Value");

    JSONObject actual = http.makePostRequest(url, bodyParams, headers);

    assertEquals(actual.get("Authorization"), expected.get("Authorization"));
    assertEquals(actual.get("url"), expected.get("url"));
    assertEquals(actual.get("method"), expected.get("method"));
  }
}
