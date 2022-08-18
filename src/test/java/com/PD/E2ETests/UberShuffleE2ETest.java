package com.PD.E2ETests;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.SpotifyLibrary;
import com.PD.uberShuffle.SpotifyLibraryImpl;
import com.PD.uberShuffle.SpotifyPlaylistCreator;
import com.PD.uberShuffle.SpotifyPlaylistCreatorImpl;
import com.PD.uberShuffle.SpotifyUberShuffleImpl;
import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import com.PD.uberShuffle.httpAdapter.HumbleOkHttpCallerImpl;
import com.PD.uberShuffle.httpAdapter.OkHttpCaller;
import com.PD.uberShuffle.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.uberShuffle.model.UberShuffleRequest;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelper;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelperImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class UberShuffleE2ETest {
  @Test
  @Disabled
  public void endToEndTest() throws ShuffleException {
    final String accessToken = "Bearer Token";
    final String userId = "jazzFiend7";
    final int playlistSize = 50;

    OkHttpCaller caller = new OkHttpCaller(new HumbleOkHttpCallerImpl());
    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller, accessToken);
    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
    SpotifyLibrary library = new SpotifyLibraryImpl(spotifyAPIHelper);
    SpotifyPlaylistCreator shuffler = new SpotifyPlaylistCreatorImpl(spotifyAPIHelper);

    SpotifyUberShuffleImpl shuffle = new SpotifyUberShuffleImpl(library, shuffler);
    shuffle.beginUberShuffle(new UberShuffleRequest(accessToken, userId, playlistSize));
  }
}
