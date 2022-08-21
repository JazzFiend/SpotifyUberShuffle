package com.PD.mocks;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.spotifyPlaylistCreator.SpotifyPlaylistCreator;
import java.util.Collection;

public class SpotifyPlaylistCreatorSpy implements SpotifyPlaylistCreator {
  private int createShufflePlaylistCalls = 0;
  @Override
  public String createShufflePlaylist(String userId, int trackCount, Collection<String> trackLibrary) throws ShuffleException {
    createShufflePlaylistCalls += 1;
    return "PlaylistId";
  }

  public int getCreateCalls() {
    return createShufflePlaylistCalls;
  }
}
