package com.PD.model.httpAdapter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONObject;

public interface HttpCaller {
     JSONObject makeRequest(Request request);
}
