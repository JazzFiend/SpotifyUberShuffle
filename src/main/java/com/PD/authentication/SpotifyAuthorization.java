package com.PD.authentication;

import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import org.json.JSONObject;

// TODO: Is this named correctly?
public class SpotifyAuthorization {
  private static final Random rng = new Random();
  private static final String POSSIBLE_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
  private AuthorizationUrl authUrl;
  @Getter private String authenticationCode;
  @Getter private String accessToken;
  private String codeVerifier;
  @Getter private String codeChallenge;

  public SpotifyAuthorization() {
    authenticationCode = "";
  }

  public AuthorizationUrl generateAuthorizationUrl(String clientId) throws NoSuchAlgorithmException {
    String state = generateRandomString(16);
    // TODO: This function is doing too much! It stores the code challenge and creates an auth url
    this.codeVerifier = generateRandomString(128);
    this.codeChallenge = generateCodeChallenge(codeVerifier);
    authUrl = new AuthorizationUrl(clientId, state, codeChallenge);
    return authUrl;
  }

  public void hydrateWithAuthorizationResponse(AuthorizationResponse authResponse)
      throws IncorrectStateException {
    if(!authResponse.getState().equals(authUrl.getState())) { throw new IncorrectStateException(
        "Response state did not match the requested state"); }

    this.authenticationCode = authResponse.getAuthenticationCode();
  }

  public void requestAccessToken(HttpRequestAdapter http, String clientId) {
    String getAccessTokenEndpoint = "https://accounts.spotify.com/api/token";

    Map<String, String> bodyParams = new HashMap<>();
    bodyParams.put("grant_type", "authorization_code");
    bodyParams.put("code", authenticationCode);
    // TODO: This value needs to exactly match the one in the AuthorizaionUri class. It should only be stored in one place.
    bodyParams.put("redirect_uri", "http://localhost:8080");
    // TODO: Should we enter clientId into the constructor?
    bodyParams.put("client_id", clientId);
    bodyParams.put("code_verifier", codeVerifier);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/x-www-form-urlencoded");

    JSONObject response = http.makePostRequestNoAuth(getAccessTokenEndpoint, bodyParams, headers);

    this.accessToken = response.getString("token_type") + " " + response.getString("access_token");
    // TODO: Also getting a Refresh Token here, but I'll deal with that later.
  }

  // TODO: Do I want to catch the exception here? What is it doing for me?
  private static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
    byte[] data = codeVerifier.getBytes(StandardCharsets.US_ASCII);
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] digest = messageDigest.digest(data);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
  }

  private static String generateRandomString(int size) {
    return generateRandomStringRecursive("", size);
  }

  private static String generateRandomStringRecursive(String code, int counter) {
    if(counter == 0) { return code; }

    return generateRandomStringRecursive(code + POSSIBLE_VALUES.charAt(rng.nextInt(POSSIBLE_VALUES.length())), counter - 1);
  }
}
