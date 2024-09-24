package com.PD.authentication;

import java.util.Random;

public class SpotifyAuthorization {
  private static final Random rng = new Random();
  private static final String POSSIBLE_VALUES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.-~";

  public static String computeAuthorizationCode() {
    return buildCode("", 128);
  }

  private static String buildCode(String code, int counter) {
    if(counter == 0) { return code; }

    return buildCode(code + POSSIBLE_VALUES.charAt(rng.nextInt(POSSIBLE_VALUES.length())), counter - 1);
  }
}
