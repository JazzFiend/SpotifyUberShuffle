package com.PD.authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import com.PD.uberShuffle.httpAdapter.HumbleOkHttpCallerImpl;
import com.PD.uberShuffle.httpAdapter.OkHttpCaller;
import com.PD.uberShuffle.httpAdapter.OkHttpHttpRequestAdapter;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

  @Test
  void requestAccessToken() {
    HttpRequestAdapter http = mock();
    String getAccessTokenEndpoint = "https://accounts.spotify.com/api/token";

    JSONObject expectedResponse = new JSONObject();
    expectedResponse.put("access_token", "Token");
    expectedResponse.put("token_type", "Bearer");
    expectedResponse.put("scope", "Scopes");
    expectedResponse.put("expires_in", "10000");
    expectedResponse.put("refresh_token", "Refresh");

    Map<String, String> expectedBodyParams = new HashMap<>();
    expectedBodyParams.put("grant_type", "authorization_code");
    expectedBodyParams.put("code", auth.getAuthenticationCode());
    expectedBodyParams.put("redirect_uri", "http://localhost:8080");
    expectedBodyParams.put("client_id", CLIENT_ID);
    expectedBodyParams.put("code_verifier", auth.getCodeChallenge());

    Map<String, String> expectedHeaders = new HashMap<>();
    expectedHeaders.put("Content-Type", "application/x-www-form-urlencoded");

    when(http.makePostRequestNoAuth(getAccessTokenEndpoint, expectedBodyParams, expectedHeaders)).thenReturn(expectedResponse);

    auth.requestAccessToken(http, CLIENT_ID);
    assertThat(auth.getAccessToken(), is("Bearer Token"));
  }

  // Once I have a proper acceptance test, get rid of this.
  @Test
  @Disabled
  void acceptanceTest() throws NoSuchAlgorithmException, IncorrectStateException {
    String clientId = "INSERT CLIENT ID HERE";
    var acceptanceAuth = new SpotifyAuthorization();
    var authUrl = acceptanceAuth.generateAuthorizationUrl(clientId);
    String toBrowser = authUrl.toString();

    String fromBrowser = "";
    var authResponse = new AuthorizationResponse(fromBrowser);
    acceptanceAuth.hydrateWithAuthorizationResponse(authResponse);


    var okHttp = new OkHttpHttpRequestAdapter(new OkHttpCaller(new HumbleOkHttpCallerImpl()));

    acceptanceAuth.requestAccessToken(okHttp, clientId);
    assertThat(acceptanceAuth.getAccessToken(), isNotNull());
  }

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
