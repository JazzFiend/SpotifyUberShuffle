package com.PD.uberShuffle.model;

public class UberShuffleRequest {
  private final String userId;
  private final int playlistSize;

  public UberShuffleRequest(String userId, int playlistSize) {
    this.userId = userId;
    this.playlistSize = playlistSize;
  }

  public String getUserId() {
    return userId;
  }

  public int getPlaylistSize() {
    return playlistSize;
  }
}
