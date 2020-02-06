package com.example.spotifyubershuffle.httpAdapter;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import helpers.JSONObjectHelper;
import mocks.HttpRequestSenderMock;

import static org.junit.Assert.*;

public class HttpRequestAdapterImplTest {
    private HttpRequestAdapterImpl requestAdapter;

    @Before
    public void setup() {
        HttpRequestSender requestSender = new HttpRequestSenderMock();
        requestAdapter = new HttpRequestAdapterImpl(requestSender);
    }

    @Test
    public void testGetRequest() {
        try {
            JSONObject getResponse = requestAdapter.makeGetRequest("http://www.get.com");
            assertEquals(JSONObjectHelper.generateGetResponse().toString(), getResponse.toString());
        } catch (InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPostRequest() {
        try {
            JSONObject postResponse = requestAdapter.makePostRequest("http://www.post.com");
            assertEquals(JSONObjectHelper.generatePostResponse().toString(), postResponse.toString());
        } catch (InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPostRequestWithBodyParams() {
        try {
            Map<String, String> bodyParams = new HashMap<>();
            bodyParams.put("Param1", "Value1");
            bodyParams.put("Param2", "Value2");

            JSONObject postResponse = requestAdapter.makePostRequest("http://www.postwithbody.com", bodyParams);
            assertEquals(JSONObjectHelper.generatePostWithBodyParamsResponse().toString(), postResponse.toString());
        } catch (InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRateLimitError() {
        try {
            JSONObject getResponse = requestAdapter.makeGetRequest("http://www.ratelimiterror.com");
            assertEquals(JSONObjectHelper.generateRateLimitResponse().toString(), getResponse.toString());
        } catch (InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = ExecutionException.class)
    public void testNotRateLimitClientError() throws ExecutionException {
        try {
            requestAdapter.makeGetRequest("http://www.genericclienterror.com");
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = ExecutionException.class)
    public void testGenericNetworkError() throws ExecutionException {
        try {
            requestAdapter.makeGetRequest("http://www.genericnetworkerror.com");
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testTimeoutError() {
        try {
            requestAdapter.makeGetRequest("http://www.timeout.com");
        } catch (InterruptedException | ExecutionException e) {
            fail(e.getMessage());
        }
    }
}