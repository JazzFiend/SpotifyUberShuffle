package com.pd.E2ETests;

import com.pd.exceptions.ShuffleException;
import com.pd.uber_shuffle.spotifyLibrary.SpotifyLibrary;
import com.pd.uber_shuffle.spotifyLibrary.SpotifyLibraryImpl;
import com.pd.uber_shuffle.spotifyPlaylistCreator.SpotifyPlaylistCreator;
import com.pd.uber_shuffle.spotifyPlaylistCreator.SpotifyPlaylistCreatorImpl;
import com.pd.uber_shuffle.spotifyUberShuffle.SpotifyUberShuffleImpl;
import com.pd.uber_shuffle.http_adapter.HttpRequestAdapter;
import com.pd.uber_shuffle.http_adapter.HumbleOkHttpCallerImpl;
import com.pd.uber_shuffle.http_adapter.OkHttpCaller;
import com.pd.uber_shuffle.http_adapter.OkHttpHttpRequestAdapter;
import com.pd.uber_shuffle.model.UberShuffleRequest;
import com.pd.uber_shuffle.spotifyApiHelper.SpotifyApiHelper;
import com.pd.uber_shuffle.spotifyApiHelper.SpotifyApiHelperImpl;
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
    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller);
    http.setAccessToken(accessToken);
    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
    SpotifyLibrary library = new SpotifyLibraryImpl(spotifyAPIHelper);
    SpotifyPlaylistCreator shuffler = new SpotifyPlaylistCreatorImpl(spotifyAPIHelper);

    SpotifyUberShuffleImpl shuffle = new SpotifyUberShuffleImpl(library, shuffler);
    shuffle.beginUberShuffle(new UberShuffleRequest(userId, playlistSize));
  }
}
