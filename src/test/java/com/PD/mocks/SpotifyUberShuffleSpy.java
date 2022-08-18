package com.PD.mocks;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.SpotifyUberShuffle;
import com.PD.uberShuffle.model.UberShuffleRequest;

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
