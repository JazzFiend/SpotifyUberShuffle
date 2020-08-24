package com.PD.helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectHelper {
    public static JSONObject generateGetResponse() {
        return generateJSON("{\"key1\": \"get\",\"key47\": 47, \"key99\": true}");
    }

    public static JSONObject generatePostResponse() {
        return generateJSON("{\"key1\": \"post\",\"key47\": 47, \"key99\": true}");
    }

    public static JSONObject generatePostWithBodyParamsResponse() {
        return generateJSON("{\"key1\": \"post\",\"key47\": 47,\"bodyParams\": \"present\"}");
    }

    public static JSONObject generateBadBodyResponse() {
        return generateJSON("{\"bodyParams\": \"Not Correct\"}");
    }

    public static JSONObject generateRateLimitResponse() {
        return generateJSON( "{\"key1\": \"rateLimited\"}");
    }

    private static JSONObject generateJSON(String json) {
        try {
            return new JSONObject(json);
        } catch(JSONException e) {
            // The test data is correct and shouldn't change much so we should never throw a JSON Exception.
            // If we do, then the null will cause a null pointer exception.
            return null;
        }
    }
}
