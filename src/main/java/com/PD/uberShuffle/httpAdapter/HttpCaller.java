package com.PD.uberShuffle.httpAdapter;

import okhttp3.Request;
import org.json.JSONObject;

public interface HttpCaller {
     JSONObject makeRequest(Request request);
}
