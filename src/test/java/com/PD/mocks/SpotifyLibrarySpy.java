package com.PD.mocks;

import com.PD.uberShuffle.SpotifyLibrary;
import java.util.Collection;
import java.util.HashSet;

public class SpotifyLibrarySpy implements SpotifyLibrary {
  private int populateLibraryCalls = 0;

  @Override
  public Collection<String> populateLibrary() {
    populateLibraryCalls += 1;
    return new HashSet<String>();
  }

  @Override
  public Collection<String> getTrackLibrary() {
    return new HashSet<String>();
  }

  public int getPopulateCalls() {
    return populateLibraryCalls;
  }
}
