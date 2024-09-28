package com.PD.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class AuthenticatorTest {
  private static final String CLIENT_ID = "CLIENT_ID";

  @Test
  void generateAuthorizationUrl() throws NoSuchAlgorithmException {
    String authorizationUrl = SpotifyAuthorization.generateAuthorizationUrl(CLIENT_ID);

    checkEndpoint(authorizationUrl);
    checkParams(authorizationUrl);
  }

  private static void checkEndpoint(String authorizationUrl) {
    String[] endpointAndParams = authorizationUrl.split("\\?");
    assertEquals("https://accounts.spotify.com/authorize", endpointAndParams[0]);
  }

  private static void checkParams(String authorizationUrl) {
    List<String> params = Arrays.stream(authorizationUrl.split("\\?")[1].split("&")).toList();
    assertTrue(params.containsAll(List.of(
        "client_id=" + CLIENT_ID,
        "response_type=code",
        "redirect_uri=http://localhost:8080",
        "scope=playlist-modify-private%20playlist-modify-public%20user-library-read",
        "code_challenge_method=S256"
    )));
    checkAllowedValues(params, "state");
    checkAllowedValues(params, "code_challenge");
  }

  private static void checkAllowedValues(List<String> params, String paramName) {
    String possibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
    List<String[]> singleParam = params.stream()
        .map(p -> p.split("="))
        .filter(p -> p[0].equals(paramName))
        .toList();
    assertEquals(1, singleParam.size());
    assertTrue(singleParam.get(0)[1].chars().allMatch(c -> possibleValues.indexOf(c) != -1));
  }
}
