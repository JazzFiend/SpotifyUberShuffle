package com.pd.uber_shuffle.http_adapter;

import okhttp3.Request;
import org.json.JSONObject;

public interface HttpCaller {
     JSONObject makeRequest(Request request);
}
