package com.PD.httpAdapter;

import com.PD.mocks.OkHttpCallerMock;
import com.PD.model.httpAdapter.HttpRequestAdapter;
import com.PD.model.httpAdapter.OkHttpHttpRequestAdapter;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OkHttpHttpRequestAdapterTest {
    private final String accessToken = "Bearer 123";
    private final HttpRequestAdapter http = new OkHttpHttpRequestAdapter(new OkHttpCallerMock(), accessToken);

    @Test
    public void getRequestTest() {
        final String url = "http://www.getrequest.com/";

        StringWriter sw = new StringWriter();
        new JSONWriter(sw)
                .object()
                .key("Authorization")
                .value(accessToken)
                .key("url")
                .value(url)
                .key("method")
                .value("GET")
                .endObject();
        JSONObject expected = new JSONObject(sw.toString());
        JSONObject actual = http.makeGetRequest(url);

        assertEquals(actual.get("Authorization"), expected.get("Authorization"));
        assertEquals(actual.get("url"), expected.get("url"));
        assertEquals(actual.get("method"), expected.get("method"));
    }

    @Test
    public void postRequestTest() {
        final String url = "http://www.postrequest.com/";

        StringWriter sw = new StringWriter();
        new JSONWriter(sw)
                .object()
                .key("Authorization")
                .value(accessToken)
                .key("url")
                .value(url)
                .key("method")
                .value("POST")
                .endObject();
        JSONObject expected = new JSONObject(sw.toString());
        JSONObject actual = http.makePostRequest(url);

        assertEquals(actual.get("Authorization"), expected.get("Authorization"));
        assertEquals(actual.get("url"), expected.get("url"));
        assertEquals(actual.get("method"), expected.get("method"));
    }

    @Test
    public void postRequestWithBodyParamsTest() {
        final String url = "http://www.postrequest.com/";

        StringWriter sw = new StringWriter();
        new JSONWriter(sw)
                .object()
                .key("Authorization")
                .value(accessToken)
                .key("url")
                .value(url)
                .key("method")
                .value("POST")
                .endObject();
        JSONObject expected = new JSONObject(sw.toString());
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("param", "value");

        JSONObject actual = http.makePostRequest(url, bodyParams);

        assertEquals(actual.get("Authorization"), expected.get("Authorization"));
        assertEquals(actual.get("url"), expected.get("url"));
        assertEquals(actual.get("method"), expected.get("method"));
    }
}
