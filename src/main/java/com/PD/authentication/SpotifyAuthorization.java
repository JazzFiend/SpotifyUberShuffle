package com.PD.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class SpotifyAuthorization {
  private static final Random rng = new Random();
  private static final String POSSIBLE_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.-~";

  private SpotifyAuthorization() {}

  public static String generateCodeVerifier() {
    return buildCode("", 128);
  }

  public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
    byte[] data = codeVerifier.getBytes(StandardCharsets.US_ASCII);
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] digest = messageDigest.digest(data);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
  }

  private static String buildCode(String code, int counter) {
    if(counter == 0) { return code; }

    return buildCode(code + POSSIBLE_VALUES.charAt(rng.nextInt(POSSIBLE_VALUES.length())), counter - 1);
  }
}
