package com.PD.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AuthenticatorTest {
  private static final String CLIENT_ID = "clientId";
  SpotifyAuthorization auth;
  @BeforeEach
  void setup() {
    auth = new SpotifyAuthorization();
  }

  @Test
  void generateAuthorizationUrl() throws NoSuchAlgorithmException {
    AuthorizationUrl authUrl = auth.generateAuthorizationUrl(CLIENT_ID);
    checkEndpoint(authUrl.toString());
    checkParams(authUrl);
  }

  // Sample Response URL: http://localhost:8080/?code=AQDDn6dvLsCBA_g9FBN-lgMf72UcGtrfhbbThNLbzRx8xF-xfD7pbNxo_lw6b2jrFFKtZnKcB6MG3_nfPlqwtyQMrn9adQP_R_ImH0xV737QhPmRPjNyndkqmNarN2sSHSFrxLcIZWFr0eoCSlvc0ZYgZ28dADykE--y3pNRdBAgsYA7seGeBvsSalp2X6OC0xzaEQnDs430aziUcpCZccaQLWOPEAUJdWyWjFfJRrWCHHQ9BinJq6pFYEiWdI9KNKpkgWms5vY8UyAluy7ZL0c1s88aJXjBLTX_62louxBOMsnPfFzSxcT0ZvPcrwBF&state=5LIC_AJoZhlAibch

  @Nested
  class EnterAuthorizationResponse {
    @Test
    void stateDoesNotMatch() throws NoSuchAlgorithmException {
      AuthorizationResponse responseBadState = new AuthorizationResponse("http://localhost:8080/?code=12345&state=bad");
      auth.generateAuthorizationUrl(CLIENT_ID);
      IncorrectStateException e = assertThrows(
        IncorrectStateException.class,
        () -> auth.hydrateWithAuthorizationResponse(responseBadState),
        "Mismatched state did not throw"
      );
      assertThat(e.getMessage(), is("Response state did not match the requested state"));
    }

    @Test
    void goodAuthorizationResponse() throws NoSuchAlgorithmException, IncorrectStateException {
      AuthorizationUrl authUrl = auth.generateAuthorizationUrl(CLIENT_ID);
      String authCode = "12345";
      AuthorizationResponse authResponse = new AuthorizationResponse("http://localhost:8080/?code="+ authCode + "&state=" + authUrl.getState());
      auth.hydrateWithAuthorizationResponse(authResponse);
      assertThat(auth.getAuthenticationCode(), is("12345"));
    }
  }


  // Right now, my HTTP protocol doesn't support specifying headers. I'm going to open a new PR that allows that.
//  @Test
//  void requestAccessToken() {
//    auth.requestAccessToken();
//    assertThat(auth.getAccessToken(), is("Bearer Token"));
//  }

  private static void checkEndpoint(String authorizationUrl) {
    String[] endpointAndParams = authorizationUrl.split("\\?");
    assertThat(endpointAndParams[0], is("https://accounts.spotify.com/authorize"));
  }

  private static void checkParams(AuthorizationUrl authorizationUrl) {
    List<String> params = Arrays.stream(authorizationUrl.toString().split("\\?")[1].split("&")).toList();
    assertThat(params, hasItems(
        "client_id=" + CLIENT_ID,
        "response_type=code",
        "redirect_uri=http://localhost:8080",
        "scope=playlist-modify-private%20playlist-modify-public%20user-library-read",
        "code_challenge_method=S256"
    ));
    assertTrue(params.stream().anyMatch(p->p.contains("state=")));
    assertTrue(params.stream().anyMatch(p->p.contains("code_challenge=")));
    checkAllowedValues(authorizationUrl.getState());
    checkAllowedValues(authorizationUrl.getCodeChallenge());
  }

  private static void checkAllowedValues(String param) {
    String possibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
    assertTrue(param.chars().allMatch(c -> possibleValues.indexOf(c) != -1));
  }
}
