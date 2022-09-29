package com.PD.uberShuffle.spotifyUberShuffle;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.model.UberShuffleRequest;
import com.PD.uberShuffle.spotifyLibrary.SpotifyLibrary;
import com.PD.uberShuffle.spotifyPlaylistCreator.SpotifyPlaylistCreator;
import java.util.Collection;

public class SpotifyUberShuffleImpl implements SpotifyUberShuffle {
  SpotifyLibrary library;
  SpotifyPlaylistCreator shuffler;

  public SpotifyUberShuffleImpl(SpotifyLibrary library, SpotifyPlaylistCreator shuffler) {
    this.library = library;
    this.shuffler = shuffler;
  }

  @Override
  public void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException {
    library.populateLibrary();
    Collection<String> trackLibrary = library.getTrackLibrary();
    shuffler.createShufflePlaylist(requestInfo.getUserId(), requestInfo.getPlaylistSize(), trackLibrary);
  }
}
