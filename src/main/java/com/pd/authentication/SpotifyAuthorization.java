package com.pd.authentication;

import com.pd.uber_shuffle.http_adapter.HttpRequestAdapter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import org.json.JSONObject;

public class SpotifyAuthorization {
  private static final Random rng = new Random();
  private static final String POSSIBLE_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
  private static final String REDIRECT_URI = "http://localhost:8080";
  @Getter private final String state;
  @Getter private String authenticationCode;
  @Getter private String accessToken;
  @Getter private final String codeVerifier;
  @Getter private final String codeChallenge;
  private final String clientId;

  public SpotifyAuthorization(String clientId) {
    authenticationCode = "";
    this.state = generateRandomString(16);
    this.codeVerifier = generateRandomString(128);
    this.codeChallenge = generateCodeChallenge(codeVerifier);
    this.clientId = clientId;
  }

  public AuthorizationUrl generateAuthorizationUrl() {
    return new AuthorizationUrl(clientId, state, codeChallenge, REDIRECT_URI);
  }

  public void giveAuthorizationResponse(AuthorizationResponse authResponse)
      throws IncorrectStateException {
    if(!authResponse.getState().equals(state)) { throw new IncorrectStateException(
        "Response state did not match the requested state"); }

    this.authenticationCode = authResponse.getAuthenticationCode();
  }

  public void requestAccessToken(HttpRequestAdapter http) {
    String getAccessTokenEndpoint = "https://accounts.spotify.com/api/token";

    Map<String, String> bodyParams = new HashMap<>();
    bodyParams.put("grant_type", "authorization_code");
    bodyParams.put("code", authenticationCode);
    bodyParams.put("redirect_uri", REDIRECT_URI);
    bodyParams.put("client_id", clientId);
    bodyParams.put("code_verifier", codeVerifier);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/x-www-form-urlencoded");

    JSONObject response = http.makePostRequestNoAuth(getAccessTokenEndpoint, bodyParams, headers);

    this.accessToken = response.getString("token_type") + " " + response.getString("access_token");
    // TODO: Also getting a Refresh Token here, but I'll deal with that later.
  }

  private static String generateCodeChallenge(String codeVerifier) {
    try {
      byte[] data = codeVerifier.getBytes(StandardCharsets.US_ASCII);
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] digest = messageDigest.digest(data);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    } catch(NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private static String generateRandomString(int size) {
    return generateRandomStringRecursive("", size);
  }

  private static String generateRandomStringRecursive(String code, int counter) {
    if(counter == 0) { return code; }

    return generateRandomStringRecursive(code + POSSIBLE_VALUES.charAt(rng.nextInt(POSSIBLE_VALUES.length())), counter - 1);
  }
}
