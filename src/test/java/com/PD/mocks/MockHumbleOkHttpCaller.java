package com.PD.mocks;

import com.PD.uberShuffle.httpAdapter.HumbleOkHttpCaller;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockHumbleOkHttpCaller {
  public static HumbleOkHttpCaller createStandardMockHttpCaller() {
    return request -> {
      return createSuccessfulResponse(request);
    };
  }

  private static Response createSuccessfulResponse(Request request) {
    ResponseBody responseBody = ResponseBody.create("{}", MediaType.get("application/json; charset=utf-8"));
    return new Response.Builder()
      .code(200)
      .request(request)
      .protocol(Protocol.HTTP_2)
      .message("Mock")
      .body(responseBody)
      .build();
  }

  public static HumbleOkHttpCaller createMockIOExceptionHttpCaller() {
    return request -> {
      throw new IOException("MockIOExceptionHttpCaller");
    };
  }

  public static HumbleOkHttpCaller createMock429HttpCaller() {
    return new HumbleOkHttpCaller() {
      private int callCount = 0;

      @Override
      public Response makeNewCall(Request request) throws IOException {
        ResponseBody errorResponseBody = ResponseBody.create("{error: {status: 429}}", MediaType.get("application/json; charset=utf-8"));
        Response mockResponse = createSuccessfulResponse(request);
        Response retryResponse = new Response.Builder()
            .code(429)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message("Mock")
            .body(errorResponseBody)
            .header("Retry-After", "0")
            .build();

        if(send429()) {
          return retryResponse;
        }
        return mockResponse;
      }

      private boolean send429() {
        if(callCount < 2) {
          callCount += 1;
          return true;
        } else {
          return false;
        }
      }
    };
  }

  public static HumbleOkHttpCaller createMock500HttpCaller() {
    return request -> {
      ResponseBody errorResponseBody = ResponseBody.create("{error: {status: 500, message: mockMessage}}",
          MediaType.get("application/json; charset=utf-8"));
      return new Response.Builder()
          .code(500)
          .request(request)
          .protocol(Protocol.HTTP_2)
          .message("Mock")
          .body(errorResponseBody)
          .build();
    };
  }
}
