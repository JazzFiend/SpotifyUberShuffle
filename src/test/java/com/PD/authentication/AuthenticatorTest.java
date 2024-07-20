package com.PD.authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AuthenticatorTest {
  @Test
  void noClientId() {
    assertThrows(RuntimeException.class, () -> {
      SpotifyAuthorization.computeAuthorizationCode("");
    });
  }
}
