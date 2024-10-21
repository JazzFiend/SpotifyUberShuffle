package com.pd.ui.controller;

import com.pd.exceptions.ShuffleException;
import com.pd.uber_shuffle.spotifyUberShuffle.SpotifyUberShuffle;
import com.pd.uber_shuffle.model.UberShuffleRequest;

public class UberShuffleController {
  private final SpotifyUberShuffle shuffle;

  public UberShuffleController(SpotifyUberShuffle shuffle) {
    this.shuffle = shuffle;
  }

  public void clickUberShuffle(String userId, int playlistSize) throws ShuffleException {
    shuffle.beginUberShuffle(new UberShuffleRequest(userId, playlistSize));
  }
}
