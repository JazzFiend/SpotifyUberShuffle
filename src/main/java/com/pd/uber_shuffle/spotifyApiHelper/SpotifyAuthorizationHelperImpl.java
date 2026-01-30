package com.pd.uber_shuffle.spotifyApiHelper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SpotifyAuthorizationHelperImpl implements SpotifyAuthorizationHelper {

  @Override
  public String authorize(String state, String codeChallenge, String clientId) {
    StringBuilder result = new StringBuilder();

    result.append("https://accounts.spotify.com/authorize?");
    result.append("client_id=").append(clientId).append("&");
    result.append("response_type=code").append("&");
    result.append("redirect_uri=").append(
        URLEncoder.encode("http://127.0.0.1:8080", StandardCharsets.UTF_8)).append("&");
    result.append("state=").append(state).append("&");
    result.append("scope=playlist-modify-private%20playlist-modify-public%20user-library-read").append("&");
    result.append("code_challenge_method=S256").append("&");
    result.append("code_challenge=").append(codeChallenge);

    return result.toString();
  }
}
