package com.PD.uberShuffle.httpAdapter;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

public interface HumbleOkHttpCaller {
  public Response makeNewCall(Request request) throws IOException;
}
