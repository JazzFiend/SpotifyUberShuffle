package com.pd.authentication;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pd.uber_shuffle.spotifyApiHelper.SpotifyAuthorizationHelperImpl;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AuthenticationTest {
  @Test
  void requestUserAuthorization() throws NoSuchAlgorithmException {
    var auth = new Authentication();
    String clientId = "ClientId";
    List<String> urlParts = List.of("https://accounts.spotify.com/authorize?",
        "client_id=ClientId",
        "response_type=code",
        "redirect_uri=http%3A%2F%2F127.0.0.1%3A8080",
        "state=",
        "scope=playlist-modify-private%20playlist-modify-public%20user-library-read",
        "code_challenge_method=S256",
        "code_challenge=");
    String authUrl = auth.requestUserAuthorization(new SpotifyAuthorizationHelperImpl(), clientId);


    assertAll("authUrl parts",
        urlParts.stream().map(part ->
            (Executable) () -> assertTrue(authUrl.contains(part),
                "Missing: " + part + "\nActual URL: " + authUrl)
        )
    );


  }
}
