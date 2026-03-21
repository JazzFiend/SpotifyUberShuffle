package com.pd.ui.controller;

import com.pd.authentication.Authentication;
import com.pd.uber_shuffle.spotifyApiHelper.SpotifyAuthorizationHelper;
import java.security.NoSuchAlgorithmException;

public class AuthorizationController {
  private SpotifyAuthorizationHelper auth;

  public AuthorizationController(SpotifyAuthorizationHelper auth) {
    this.auth = auth;
  }

  public String clickSendAuthorization(String clientId) throws NoSuchAlgorithmException {
    return new Authentication().requestUserAuthorization(auth, clientId);
  }
}
