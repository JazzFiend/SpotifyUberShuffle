package com.PD.uberShuffle.httpAdapter;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HumbleOkHttpCallerImpl implements HumbleOkHttpCaller {
  OkHttpClient http;

  public HumbleOkHttpCallerImpl() {
     this.http = new OkHttpClient();
  }

  @Override
  public Response makeNewCall(Request request) throws IOException {
    return http.newCall(request).execute();
  }
}
