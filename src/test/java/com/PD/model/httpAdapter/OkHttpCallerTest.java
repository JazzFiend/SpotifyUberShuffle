package com.PD.model.httpAdapter;

import static com.PD.mocks.MockHumbleOkHttpCaller.createMock429HttpCaller;
import static com.PD.mocks.MockHumbleOkHttpCaller.createMock500HttpCaller;
import static com.PD.mocks.MockHumbleOkHttpCaller.createMockIOExceptionHttpCaller;
import static com.PD.mocks.MockHumbleOkHttpCaller.createStandardMockHttpCaller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OkHttpCallerTest {

  private OkHttpCaller createOkHttpCallerWithMock(HumbleOkHttpCaller mock) {
    return new OkHttpCaller(mock);
  }

  @NotNull
  private static Request buildGenericRequest() {
    return new Request.Builder().url("https://www.fake.com").build();
  }

  @Test
  public void noErrors() {
    OkHttpCaller http = createOkHttpCallerWithMock(createStandardMockHttpCaller());
    Request request = buildGenericRequest();
    JSONObject response = http.makeRequest(request);
    assertEquals(new JSONObject().toString(), response.toString());
  }

  // TODO: Put something legit into the console messages.
  @Test
  @DisplayName("IOException is swallowed.")
  public void requestThrowsIOException() {
    OkHttpCaller http = createOkHttpCallerWithMock(createMockIOExceptionHttpCaller());
    Request request = buildGenericRequest();
    RuntimeException e = assertThrows(RuntimeException.class, () -> {
      http.makeRequest(request);
    });
    assertEquals(e.getMessage(), "JSON Response was never populated");
  }

  @Test
  public void retryAfter429Response() {
    OkHttpCaller http = createOkHttpCallerWithMock(createMock429HttpCaller());
    Request request = buildGenericRequest();
    JSONObject response = http.makeRequest(request);
    assertEquals(new JSONObject().toString(), response.toString());
  }

  @Test
  public void otherErrorsThrowRuntimeException() {
    OkHttpCaller http = createOkHttpCallerWithMock(createMock500HttpCaller());
    Request request = buildGenericRequest();
    RuntimeException e = assertThrows(RuntimeException.class, () -> {
      http.makeRequest(request);
    });
    assertEquals(e.getMessage(), "500 - mockMessage");
  }
}