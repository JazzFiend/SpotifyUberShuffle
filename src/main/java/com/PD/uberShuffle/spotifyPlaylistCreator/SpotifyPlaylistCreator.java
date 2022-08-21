package com.PD.uberShuffle.spotifyPlaylistCreator;

import com.PD.exceptions.ShuffleException;
import java.util.Collection;

public interface SpotifyPlaylistCreator {
  public String createShufflePlaylist(String userId, int trackCount, Collection<String> trackLibrary) throws ShuffleException;
}
