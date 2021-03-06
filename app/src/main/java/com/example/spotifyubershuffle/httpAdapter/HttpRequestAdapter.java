package com.example.spotifyubershuffle.httpAdapter;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface HttpRequestAdapter {
    JSONObject makeGetRequest(String url) throws InterruptedException, ExecutionException;

    JSONObject makePostRequest(String url) throws InterruptedException, ExecutionException;

    JSONObject makePostRequest(String url, Map<String, String> body) throws InterruptedException, ExecutionException;
}
