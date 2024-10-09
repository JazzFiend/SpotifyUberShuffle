package com.PD.authentication;

import com.PD.uberShuffle.httpAdapter.HttpCaller;
import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import com.PD.uberShuffle.httpAdapter.HumbleOkHttpCallerImpl;
import com.PD.uberShuffle.httpAdapter.OkHttpCaller;
import com.PD.uberShuffle.httpAdapter.OkHttpHttpRequestAdapter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import lombok.Getter;

public class SpotifyAuthorization {
  private static final Random rng = new Random();
  private static final String POSSIBLE_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
  private AuthorizationUrl authUrl;
  @Getter private String authenticationCode;
  @Getter private String accessToken;

  public SpotifyAuthorization() {
    authenticationCode = "";
  }

  public AuthorizationUrl generateAuthorizationUrl(String clientId) throws NoSuchAlgorithmException {
    String state = generateRandomString(16);
    String codeChallenge = generateCodeChallenge(generateRandomString(128));
    authUrl = new AuthorizationUrl(clientId, state, codeChallenge);
    return authUrl;
  }

  public void hydrateWithAuthorizationResponse(AuthorizationResponse authResponse)
      throws IncorrectStateException {
    if(!authResponse.getState().equals(authUrl.getState())) { throw new IncorrectStateException(
        "Response state did not match the requested state"); }

    this.authenticationCode = authResponse.getAuthenticationCode();
  }

//  public void requestAccessToken() {
//    HumbleOkHttpCallerImpl callerImpl = new HumbleOkHttpCallerImpl();
//    HttpCaller caller = new OkHttpCaller(callerImpl);
//    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller);
//
//    String getAccessTokenEndpoint = "https://accounts.spotify.com/api/token";
//
//    http.makePostRequest()
//
//  }

  // Do I want to catch the exception here? What is it doing for me?
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
