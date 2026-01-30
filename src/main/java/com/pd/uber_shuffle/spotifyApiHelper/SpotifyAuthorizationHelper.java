package com.pd.uber_shuffle.spotifyApiHelper;

public interface SpotifyAuthorizationHelper {
  String authorize(String state, String codeChallenge, String clientId);
}
