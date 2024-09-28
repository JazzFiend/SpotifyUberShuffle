package com.PD.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class AuthenticatorTest {
  private static final String CLIENT_ID = "clientId";

  @Test
  void generateAuthorizationUrl() throws NoSuchAlgorithmException {
    String authorizationUrl = SpotifyAuthorization.generateAuthorizationUrl(CLIENT_ID);

    checkEndpoint(authorizationUrl);
    checkParams(authorizationUrl);
  }

  // Response URL: http://localhost:8080/?code=AQDDn6dvLsCBA_g9FBN-lgMf72UcGtrfhbbThNLbzRx8xF-xfD7pbNxo_lw6b2jrFFKtZnKcB6MG3_nfPlqwtyQMrn9adQP_R_ImH0xV737QhPmRPjNyndkqmNarN2sSHSFrxLcIZWFr0eoCSlvc0ZYgZ28dADykE--y3pNRdBAgsYA7seGeBvsSalp2X6OC0xzaEQnDs430aziUcpCZccaQLWOPEAUJdWyWjFfJRrWCHHQ9BinJq6pFYEiWdI9KNKpkgWms5vY8UyAluy7ZL0c1s88aJXjBLTX_62louxBOMsnPfFzSxcT0ZvPcrwBF&state=5LIC_AJoZhlAibch

  @Nested
  class EnterAuthorizationResponse {
    @Test
    void stateDoesNotMatch() {
      String responseBadState = "http://localhost:8080/?code=12345&state=bad";
      RuntimeException e = assertThrows(
        RuntimeException.class,
        () -> SpotifyAuthorization.enterAuthorizationResponse(responseBadState),
        "Mismatched state did not throw"
      );
      assertEquals("Response state did not match the requested state", e.getMessage());
    }

    // The next test is going to require me to save the state value after the authorizationURL is generated. I'll need to make the class not static as a result. Refactor that first.
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
