package com.PD.model;

import com.PD.exceptions.ShuffleException;
import com.PD.model.httpAdapter.HttpRequestAdapter;
import com.PD.model.httpAdapter.HumbleOkHttpCallerImpl;
import com.PD.model.httpAdapter.OkHttpCaller;
import com.PD.model.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.model.spotifyApiHelper.SpotifyApiHelper;
import com.PD.model.spotifyApiHelper.SpotifyApiHelperImpl;
import java.util.Collection;

public class SpotifyUberShuffle {
  // TODO: This can be tested as an integration test. But I need a mock OkHttpClient before I can do that.
  public static void beginUberShuffle(String accessToken, String userId, int playlistSize) throws ShuffleException {
    OkHttpCaller caller = new OkHttpCaller(new HumbleOkHttpCallerImpl());
    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller, accessToken);
    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
    ShufflePlaylistCreator shuffler = new ShufflePlaylistCreator(spotifyAPIHelper);
    SpotifyLibrary library = new SpotifyLibrary(spotifyAPIHelper);

    Collection<String> trackLibrary = library.populateLibrary();
    shuffler.createShufflePlaylist(userId, playlistSize, trackLibrary);
  }
}
