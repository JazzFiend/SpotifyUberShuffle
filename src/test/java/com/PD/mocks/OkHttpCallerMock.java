package com.PD.mocks;

import com.PD.uberShuffle.httpAdapter.HttpCaller;
import java.io.StringWriter;
import okhttp3.Request;
import org.json.JSONObject;
import org.json.JSONWriter;

public class OkHttpCallerMock implements HttpCaller {

    // This isn't right. I'm not mocking a response back. I'm just turning the request into a JSONObject.
    // It's a roundabout implementation of a Spy.
    // I'm going to use Mockito to mock this function and spy on the inputs. Then I'll just make sure
    // that something comes back. Maybe I'll fix up the whole test. We shall see.
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
