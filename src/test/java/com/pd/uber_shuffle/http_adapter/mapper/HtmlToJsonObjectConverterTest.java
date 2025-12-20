package com.pd.uber_shuffle.http_adapter.mapper;

import static org.junit.jupiter.api.Assertions.*;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;

class HtmlToJsonObjectConverterTest {
  @Test
  void dummyTest() {

    ResponseBody responseBody = ResponseBody.create(json, MediaType.get("application/json"));
    Response jsonResponse = new Response.Builder()
        .code(200)
        .message("OK")
        .protocol(Protocol.HTTP_1_1)
        .request(new okhttp3.Request.Builder().url("http://localhost/").build())
        .body(responseBody)
        .build();

    HtmlToJsonObjectConverter.convertToJsonObject(jsonResponse);
  }
}
