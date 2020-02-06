package com.example.spotifyubershuffle.httpAdapter;

import com.android.volley.ClientError;

public class VolleyErrorChecker {
    public static boolean isClientError(Exception e) {
        return e.getCause().toString().equals("com.android.volley.ClientError");
    }

    public static boolean isRateLimitError(ClientError clientError) {
        return clientError.networkResponse.statusCode == 429 && clientError.networkResponse.headers.containsKey("Retry-After");
    }
}
