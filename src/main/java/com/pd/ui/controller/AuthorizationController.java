package com.pd.ui.controller;

import com.pd.authentication.Authentication;
import com.pd.uber_shuffle.spotifyApiHelper.SpotifyApiHelper;
import java.security.NoSuchAlgorithmException;

public class AuthorizationController {
  private SpotifyApiHelper spotifyApiHelper;

  public AuthorizationController(SpotifyApiHelper spotifyApiHelper) {
    this.spotifyApiHelper = spotifyApiHelper;
  }

  public String clickSendAuthorization(String clientId) throws NoSuchAlgorithmException {
    return new Authentication().requestUserAuthorization(spotifyApiHelper, clientId);
  }
}
