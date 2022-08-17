package com.PD.uberShuffle.model;

public class UberShuffleRequest {
  private final String accessToken;
  private final String userId;
  private final int playlistSize;

  public UberShuffleRequest(String accessToken, String userId, int playlistSize) {
    this.accessToken = accessToken;
    this.userId = userId;
    this.playlistSize = playlistSize;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getUserId() {
    return userId;
  }

  public int getPlaylistSize() {
    return playlistSize;
  }
}
