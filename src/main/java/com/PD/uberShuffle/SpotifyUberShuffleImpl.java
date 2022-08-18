package com.PD.uberShuffle;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.model.UberShuffleRequest;
import java.util.Collection;

public class SpotifyUberShuffleImpl implements SpotifyUberShuffle {
  SpotifyLibrary library;
  SpotifyPlaylistCreator shuffler;

  public SpotifyUberShuffleImpl(SpotifyLibrary library, SpotifyPlaylistCreator shuffler) {
    this.library = library;
    this.shuffler = shuffler;
  }

  // TODO: This can be tested as an integration test. But I need a mock OkHttpClient before I can do that.
//  @Override
//  public void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException {
//    OkHttpCaller caller = new OkHttpCaller(new HumbleOkHttpCallerImpl());
//    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller, requestInfo.getAccessToken());
//    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
//    ShufflePlaylistCreator shuffler = new ShufflePlaylistCreator(spotifyAPIHelper);
//    SpotifyLibrary library = new SpotifyLibrary(spotifyAPIHelper);
//
//    Collection<String> trackLibrary = library.populateLibrary();
//    shuffler.createShufflePlaylist(requestInfo.getUserId(), requestInfo.getPlaylistSize(), trackLibrary);
//  }

  @Override
  public void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException {
    Collection<String> trackLibrary = library.populateLibrary();
    shuffler.createShufflePlaylist(requestInfo.getUserId(), requestInfo.getPlaylistSize(), trackLibrary);
  }
}
