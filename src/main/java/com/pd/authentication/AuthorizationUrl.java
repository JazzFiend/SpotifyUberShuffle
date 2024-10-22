package com.pd.authentication;

import lombok.Getter;

public class AuthorizationUrl {
  private final String clientId;
  @Getter private final String state;
  @Getter private final String codeChallenge;
  private final String redirectUri;

  public AuthorizationUrl(String clientId, String state, String codeChallenge, String redirectUri) {
    this.clientId = clientId;
    this.state = state;
    this.codeChallenge = codeChallenge;
    this.redirectUri = redirectUri;
  }

  public String toString() {
    return "https://accounts.spotify.com/authorize?"
        + "client_id=" + clientId + "&"
        + "response_type=code" + "&"
        + "redirect_uri="+ redirectUri + "&"
        + "state=" + state + "&"
        + "scope=playlist-modify-private%20playlist-modify-public%20user-library-read" + "&"
        + "code_challenge_method=S256" + "&"
        + "code_challenge=" + codeChallenge;
  }
}
