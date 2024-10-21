package com.pd.uber_shuffle.spotifyPlaylistCreator;

import com.pd.exceptions.ShuffleException;
import java.util.Collection;

public interface SpotifyPlaylistCreator {
  public String createShufflePlaylist(String userId, int trackCount, Collection<String> trackLibrary) throws ShuffleException;
}
