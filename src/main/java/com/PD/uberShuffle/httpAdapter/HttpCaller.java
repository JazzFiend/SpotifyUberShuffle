package com.PD.uberShuffle.httpAdapter;

import okhttp3.Request;
import org.json.JSONObject;

// TODO: This is kind of a dumb adapter. We're still depending on OKHTTP in it. Let's completely
//  abstract out the HTTP Caller. Figure out how the SpotifyAPI class is calling everything.
public interface HttpCaller {
     JSONObject makeRequest(Request request);
}
