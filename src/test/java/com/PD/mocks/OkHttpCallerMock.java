package com.PD.mocks;

import com.PD.uberShuffle.httpAdapter.HttpCaller;
import java.io.StringWriter;
import okhttp3.Request;
import org.json.JSONObject;
import org.json.JSONWriter;

public class OkHttpCallerMock implements HttpCaller {

    @Override
    public JSONObject makeRequest(Request request) {
        StringWriter sw = new StringWriter();
        new JSONWriter(sw)
                .object()
                .key("Authorization")
                .value(request.headers().get("Authorization"))
                .key("url")
                .value(request.url().toString())
                .key("method")
                .value(request.method())
                .endObject();

        return new JSONObject(sw.toString());
    }
}
