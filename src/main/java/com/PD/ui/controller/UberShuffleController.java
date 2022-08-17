package com.PD.ui.controller;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.SpotifyUberShuffle;
import com.PD.uberShuffle.model.UberShuffleRequest;

public class UberShuffleController {
  private final SpotifyUberShuffle shuffle;

  public UberShuffleController(SpotifyUberShuffle shuffle) {
    this.shuffle = shuffle;
  }

  public void clickUberShuffle(String accessToken, String userId, int playlistSize) throws ShuffleException {
    shuffle.beginUberShuffle(new UberShuffleRequest(accessToken, userId, playlistSize));
  }
}
