package com.PD.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

class AuthenticatorTest {
  @Test
  void generateCodeVerifier() {
    String possibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_.-~";
    String codeVerifier = SpotifyAuthorization.generateCodeVerifier();
    assertEquals(128, codeVerifier.length());
    assertTrue(codeVerifier.chars().allMatch(c -> possibleValues.indexOf(c) != -1));
  }

  @Test
  void generateCodeChallenge() throws NoSuchAlgorithmException {
    String expected = "WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U"; // Computed by code that I know works.
    assertEquals(expected, SpotifyAuthorization.generateCodeChallenge("12345"));
  }
}
