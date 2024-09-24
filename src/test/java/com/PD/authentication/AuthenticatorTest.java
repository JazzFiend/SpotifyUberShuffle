package com.PD.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AuthenticatorTest {
  @Test
  void generateCodeVerifier() {
    String possibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.-~";
    String codeVerifier = SpotifyAuthorization.computeAuthorizationCode();
    assertEquals(128, codeVerifier.length());
    assertTrue(codeVerifier.chars().allMatch(c -> possibleValues.indexOf(c) != -1));
  }
}
