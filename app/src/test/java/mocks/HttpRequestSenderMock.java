package mocks;

import com.android.volley.ClientError;
import com.android.volley.Header;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.example.spotifyubershuffle.httpAdapter.HttpRequestSender;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import helpers.JSONObjectHelper;

public class HttpRequestSenderMock implements HttpRequestSender {
    private boolean retry;

    public HttpRequestSenderMock() {
        retry = false;
    }

    @Override
    public JSONObject sendRequest(String url, int requestMethodInteger, Map<String, String> bodyParameters) throws InterruptedException, ExecutionException {
        NetworkResponse rateLimitError;
        switch(url) {
            case "http://www.get.com":
                return JSONObjectHelper.generateGetResponse();
            case "http://www.post.com":
                return JSONObjectHelper.generatePostResponse();
            case "http://www.postwithbody.com":
                if(bodyParameters.get("Param1").equals("Value1") && bodyParameters.get("Param2").equals("Value2")) {
                    return JSONObjectHelper.generatePostWithBodyParamsResponse();
                }
                else {
                    return JSONObjectHelper.generateBadBodyResponse();
                }
            case "http://www.ratelimiterror.com":
                if(!retry) {
                    retry = true;
                    throw new ExecutionException("com.android.volley.ClientError", new ClientError(createRateLimitResponse()));
                } else {
                    return JSONObjectHelper.generateRateLimitResponse();
                }
            case "http://www.genericclienterror.com":
                rateLimitError = new NetworkResponse(500, new byte[10], false, 1000, new ArrayList<>());
                throw new ExecutionException("Generic Client Error", new ClientError(rateLimitError));
            case "http://www.genericnetworkerror.com":
                throw new ExecutionException("Generic Network Error", new NetworkError());
            case "http://www.timeout.com":
                throw new ExecutionException("com.android.volley.ClientError", new ClientError(createRateLimitResponse()));
            default:
                throw new RuntimeException("Bad Test URL Seen");
        }
    }

    private NetworkResponse createRateLimitResponse() {
        List<Header> retryHeader = new ArrayList<>();
        retryHeader.add(new Header("Retry-After", "0"));
        return new NetworkResponse(429, new byte[10], false, 1000, retryHeader);
    }
}
