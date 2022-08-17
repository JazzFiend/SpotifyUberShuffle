package com.PD.uberShuffle;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.model.UberShuffleRequest;

public interface SpotifyUberShuffle {
  void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException;
}
