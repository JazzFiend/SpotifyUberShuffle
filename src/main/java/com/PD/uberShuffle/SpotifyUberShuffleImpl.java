package com.PD.uberShuffle;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import com.PD.uberShuffle.httpAdapter.HumbleOkHttpCallerImpl;
import com.PD.uberShuffle.httpAdapter.OkHttpCaller;
import com.PD.uberShuffle.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.uberShuffle.model.UberShuffleRequest;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelper;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelperImpl;
import java.util.Collection;

public class SpotifyUberShuffleImpl implements SpotifyUberShuffle {
  // TODO: This can be tested as an integration test. But I need a mock OkHttpClient before I can do that.
  @Override
  public void beginUberShuffle(UberShuffleRequest requestInfo) throws ShuffleException {
    OkHttpCaller caller = new OkHttpCaller(new HumbleOkHttpCallerImpl());
    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller, requestInfo.getAccessToken());
    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
    ShufflePlaylistCreator shuffler = new ShufflePlaylistCreator(spotifyAPIHelper);
    SpotifyLibrary library = new SpotifyLibrary(spotifyAPIHelper);

    Collection<String> trackLibrary = library.populateLibrary();
    shuffler.createShufflePlaylist(requestInfo.getUserId(), requestInfo.getPlaylistSize(), trackLibrary);
  }
}
