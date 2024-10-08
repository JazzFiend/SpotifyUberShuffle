package com.PD.uberShuffle.httpAdapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.PD.mocks.OkHttpCallerMock;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class OkHttpHttpRequestAdapterTest {
  private final String accessToken = "Bearer 123";
  private HttpRequestAdapter http;
  private OkHttpCaller mockCaller;
  private OkHttpHttpRequestAdapter httpWithMock;
  private final String url = "https://www.dummy.com/";

  @BeforeEach
  public void setup() {
    http = new OkHttpHttpRequestAdapter(new OkHttpCallerMock());
    http.setAccessToken(accessToken);
    mockCaller = mock();
    when(mockCaller.makeRequest(any(Request.class))).thenReturn(new JSONObject());
    httpWithMock = new OkHttpHttpRequestAdapter(mockCaller);
    httpWithMock.setAccessToken(accessToken);
  }

  @Nested
  class GetTests {
    @Test
    void getRequestNoHeaders() {
      Request expectedRequest = new Builder()
          .get()
          .url(url)
          .header("Authorization", accessToken)
          .build();

      httpWithMock.makeGetRequest(url);
      Request actualRequest = checkMakeRequestAndExtractParameter();

      assertRequestsEqual(expectedRequest, actualRequest);
      assertThat(expectedRequest.body(), is(nullValue()));
    }

    @Test
    void getRequestWithHeaderAddAuthenticationTest() {
      Request expectedRequest = new Builder()
          .get()
          .url(url)
          .header("Authorization", accessToken)
          .header("Host", "DummyHost")
          .build();
      Map<String, String> headers = new HashMap<>();
      headers.put("Host", "DummyHost");

      httpWithMock.makeGetRequest(url, headers);
      Request actualRequest = checkMakeRequestAndExtractParameter();

      assertRequestsEqual(expectedRequest, actualRequest);
      assertThat(expectedRequest.body(), is(nullValue()));
    }

    // Complete coverage with get request
    @Test
    void getRequestRewriteAuthenticationTest() {
      Request expectedRequest = new Builder()
          .get()
          .url(url)
          .header("Authorization", accessToken)
          .build();
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", accessToken);

      httpWithMock.makeGetRequest(url, headers);
      Request actualRequest = checkMakeRequestAndExtractParameter();

      assertRequestsEqual(expectedRequest, actualRequest);
      assertThat(expectedRequest.body(), is(nullValue()));
    }
  }

  @Nested
  class PostTests {
    final MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");

    @Test
    void postRequestTest() throws IOException {
      RequestBody body = createBodyParameters(new HashMap<>());
      Request expectedRequest = new Builder()
          .post(body)
          .url(url)
          .header("Authorization", accessToken)
          .build();
      httpWithMock.makePostRequest(url);

      Request actualRequest = checkMakeRequestAndExtractParameter();
      assertRequestsEqual(expectedRequest, actualRequest);
      assertBodyParamsEqual(expectedRequest, actualRequest);
    }

    @Test
    void postRequestWithBodyParamsTest() throws IOException {
      Map<String, String> bodyMap = new HashMap<>();
      bodyMap.put("BodyParam1", "val1");
      RequestBody body = createBodyParameters(bodyMap);
      Request expectedRequest = new Builder()
          .post(body)
          .url(url)
          .header("Authorization", accessToken)
          .build();
      httpWithMock.makePostRequest(url, bodyMap);

      Request actualRequest = checkMakeRequestAndExtractParameter();
      assertRequestsEqual(expectedRequest, actualRequest);
      assertBodyParamsEqual(expectedRequest, actualRequest);
    }

    @Test
    void postRequestWithHeadersTest() throws IOException {
      Map<String, String> bodyMap = new HashMap<>();
      bodyMap.put("param", "value");
      Map<String, String> headerMap = new HashMap<>();
      headerMap.put("Authorization", accessToken);
      headerMap.put("Host", "Value");

      final MediaType json = MediaType.get("application/json; charset=utf-8");
      RequestBody expectedBody = RequestBody.create(new JSONObject(bodyMap).toString(), json);
      Request expectedRequest = new Builder()
          .post(expectedBody)
          .header("Authorization", accessToken)
          .header("Host", "Value")
          .url(url)
          .build();

      httpWithMock.makePostRequest(url, bodyMap, headerMap);
      Request actualRequest = checkMakeRequestAndExtractParameter();

      assertRequestsEqual(expectedRequest, actualRequest);
      assertBodyParamsEqual(expectedRequest, actualRequest);
    }

    private RequestBody createBodyParameters(Map<String, String> bodyPairs) {
      return RequestBody.create(new JSONObject(bodyPairs).toString(), jsonMediaType);
    }
  }

  private Request checkMakeRequestAndExtractParameter() {
    ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
    verify(mockCaller, times(1)).makeRequest(requestCaptor.capture());
    return requestCaptor.getValue();
  }

  private static void assertRequestsEqual(Request expectedRequest, Request actualRequest) {
    assertThat(actualRequest.url(), is(expectedRequest.url()));
    assertThat(actualRequest.method(), is(expectedRequest.method()));
    assertThat(actualRequest.headers().toMultimap(), is(expectedRequest.headers().toMultimap()));
  }

  private static void assertBodyParamsEqual(Request expectedRequest, Request actualRequest) throws IOException {
    assertThat(actualRequest.body().contentType(), is(expectedRequest.body().contentType()));
    assertThat(actualRequest.body().contentLength(), is(expectedRequest.body().contentLength()));
  }
}
