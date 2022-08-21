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

  // TODO: This can be tested as an integration test. But I need a mock OkHttpClient before I can do that.
  @Override
  public void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException {
    Collection<String> trackLibrary = library.populateLibrary();
    shuffler.createShufflePlaylist(requestInfo.getUserId(), requestInfo.getPlaylistSize(), trackLibrary);
  }
}
