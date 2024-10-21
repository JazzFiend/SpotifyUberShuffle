package com.pd.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class Authentication {
  private static final Random rng = new Random();

  private Authentication() {}

  public static String generateRandomString(int length) {
    StringBuilder result = new StringBuilder();
    String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (int i = 0; i < length; i++) {
      result.append(possible.charAt((rng.nextInt(possible.length()))));
    }
    return result.toString();
  }

  public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
    byte[] data = codeVerifier.getBytes(StandardCharsets.US_ASCII);
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] digest = messageDigest.digest(data);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
  }

  public static String generateAuthorizationCurl(String codeVerifier, String clientId) throws NoSuchAlgorithmException {
    StringBuilder result = new StringBuilder();
    String state = generateRandomString(16);
    String codeChallenge = generateCodeChallenge(codeVerifier);

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

  public static String generateAccessTokenCurl(String code, String clientId, String codeVerifier) {
    return
        "curl -X POST \"https://accounts.spotify.com/api/token\" -H \"Content-Type: application/x-www-form-urlencoded\" -d \""
            + "grant_type=authorization_code&"
            + "code=" + code + "&"
            + "redirect_uri=http://localhost:8080" + "&"
            + "client_id=" + clientId + "&"
            + "code_verifier=" + codeVerifier
            + "\"";
  }
}
