package com.example.spotifyubershuffle.httpAdapter;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface HttpRequestSender {
    JSONObject sendRequest(String url, int requestMethodInteger, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException;
}
