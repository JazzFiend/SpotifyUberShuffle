package com.pd.uber_shuffle.spotifyUberShuffle;

import com.pd.exceptions.ShuffleException;
import com.pd.uber_shuffle.model.UberShuffleRequest;

public interface SpotifyUberShuffle {
  void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException;
}
