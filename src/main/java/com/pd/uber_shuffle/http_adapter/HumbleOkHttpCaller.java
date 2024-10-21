package com.pd.uber_shuffle.http_adapter;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

public interface HumbleOkHttpCaller {
  public Response makeNewCall(Request request) throws IOException;
}
