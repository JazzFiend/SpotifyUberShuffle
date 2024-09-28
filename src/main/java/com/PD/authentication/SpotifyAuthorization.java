package com.PD.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class SpotifyAuthorization {
  private static final Random rng = new Random();
  private static final String POSSIBLE_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";

  public String generateAuthorizationUrl(String clientId) throws NoSuchAlgorithmException {
    StringBuilder result = new StringBuilder();
    String state = generateRandomString(16);
    String codeChallenge = generateCodeChallenge(generateRandomString(128));

    result.append("https://accounts.spotify.com/authorize?");
    result.append("client_id=").append(clientId).append("&");
    result.append("response_type=code").append("&");
    result.append("redirect_uri=http://localhost:8080").append("&");
    result.append("state=").append(state).append("&");
    result.append("scope=playlist-modify-private%20playlist-modify-public%20user-library-read").append("&");
    result.append("code_challenge_method=S256").append("&");
    result.append("code_challenge=").append(codeChallenge);

    return result.toString();
  }

  public void enterAuthorizationResponse(String authorizationResponse) {
    throw new RuntimeException("Response state did not match the requested state");
  }

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
