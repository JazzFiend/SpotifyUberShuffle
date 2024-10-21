package com.pd.mocks;

import com.pd.exceptions.ShuffleException;
import com.pd.uber_shuffle.spotifyUberShuffle.SpotifyUberShuffle;
import com.pd.uber_shuffle.model.UberShuffleRequest;

public class SpotifyUberShuffleSpy implements SpotifyUberShuffle {
  private int calledBeginUberShuffle = 0;
  private UberShuffleRequest beginUberShuffleArguments;

  @Override
  public void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException {
    calledBeginUberShuffle += 1;
    beginUberShuffleArguments = requestInfo;
  }

  public int beginUberShuffleCount() {
    return calledBeginUberShuffle;
  }

  public UberShuffleRequest lastBeginUberShuffleArgs() {
    return beginUberShuffleArguments;
  }
}
