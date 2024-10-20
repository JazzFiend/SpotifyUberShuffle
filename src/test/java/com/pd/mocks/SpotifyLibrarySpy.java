package com.pd.mocks;

import com.pd.uber_shuffle.spotifyLibrary.SpotifyLibrary;
import java.util.Collection;
import java.util.HashSet;

public class SpotifyLibrarySpy implements SpotifyLibrary {
  private int populateLibraryCalls = 0;

  @Override
  public void populateLibrary() {
    populateLibraryCalls += 1;
  }

  @Override
  public Collection<String> getTrackLibrary() {
    return new HashSet<String>();
  }

  public int getPopulateCalls() {
    return populateLibraryCalls;
  }
}
