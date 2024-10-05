package com.PD.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AuthenticationTest {
  public static final String CLIENT_ID = "Replace with client id";

  @ParameterizedTest
  @ValueSource(ints = {1, 5, 43, 128})
  void randomStringsBySize(int sequenceSize) {
    String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    String randomString = Authentication.generateRandomString(sequenceSize);
    assertEquals(sequenceSize, randomString.length());
    for(int i = 0; i < randomString.length(); i++) {
      CharSequence character = randomString.subSequence(i, i + 1);
      assertTrue(possible.contains(character));
    }
  }

  @Test
  void authorizationCurl() throws NoSuchAlgorithmException {
    String codeVerifier = Authentication.generateRandomString(128);
    String authorizationCurl = Authentication.generateAuthorizationCurl(codeVerifier, CLIENT_ID);
    System.out.println(codeVerifier);
    System.out.println(authorizationCurl);
    assertNotNull(authorizationCurl);
  }
  @Test
  void accessTokenCurl() {
    String code = "Replace with response from previous test";
    String codeVerifier = "Replace with code verifier generated in previous test";
    String accessTokenCurl = Authentication.generateAccessTokenCurl(code, CLIENT_ID, codeVerifier);
    System.out.println(accessTokenCurl);
  }
}

// Bearer BQBox-Me5Uck9z90wVujmKRKIi1kE_bVNKQ6iAtujYmtcwz9uWxI7YOXAr54rdLGqkk7NsBqbpZKesMvIMRtEwkeTSv5pOflyc8QdzeeUPSTQ3PouaRhbfP1kucSC6Rk0vhT3KewinJU99Qclfs8TAGEaSQw27XyKGMn8EINJG-3_sWWudyxsuSHwuCbghurI6zjxOBIMnHG4BYtBJ6AtaYmUIDbuxbv6ZQg2TNbE7wrjpf6JCBCG88exKZPi6WneQ