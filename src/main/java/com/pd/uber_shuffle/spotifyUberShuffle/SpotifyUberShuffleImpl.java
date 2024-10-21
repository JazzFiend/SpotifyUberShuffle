package com.pd.uber_shuffle.spotifyUberShuffle;

import com.pd.exceptions.ShuffleException;
import com.pd.uber_shuffle.model.UberShuffleRequest;
import com.pd.uber_shuffle.spotifyLibrary.SpotifyLibrary;
import com.pd.uber_shuffle.spotifyPlaylistCreator.SpotifyPlaylistCreator;
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
