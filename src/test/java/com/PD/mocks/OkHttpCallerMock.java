package com.PD.mocks;

import com.PD.model.httpAdapter.HttpCaller;
import okhttp3.Request;
import org.json.JSONObject;
import org.json.*;

import java.io.StringWriter;

public class OkHttpCallerMock implements HttpCaller {

    @Override
    public JSONObject makeRequest(Request request) {
        StringWriter sw = new StringWriter();
        new JSONWriter(sw)
                .object()
                .key("Authorization")
                .value(request.headers().get("Authorization").toString())
                .key("url")
                .value(request.url().toString())
                .key("method")
                .value(request.method())
                .endObject();

        return new JSONObject(sw.toString());
    }
}
