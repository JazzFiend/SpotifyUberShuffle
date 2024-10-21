package com.pd.ui.view;

import com.pd.uber_shuffle.spotifyLibrary.SpotifyLibrary;
import com.pd.uber_shuffle.spotifyLibrary.SpotifyLibraryImpl;
import com.pd.uber_shuffle.spotifyPlaylistCreator.SpotifyPlaylistCreator;
import com.pd.uber_shuffle.spotifyPlaylistCreator.SpotifyPlaylistCreatorImpl;
import com.pd.uber_shuffle.spotifyUberShuffle.SpotifyUberShuffle;
import com.pd.uber_shuffle.spotifyUberShuffle.SpotifyUberShuffleImpl;
import com.pd.uber_shuffle.http_adapter.HttpRequestAdapter;
import com.pd.uber_shuffle.http_adapter.HumbleOkHttpCallerImpl;
import com.pd.uber_shuffle.http_adapter.OkHttpCaller;
import com.pd.uber_shuffle.http_adapter.OkHttpHttpRequestAdapter;
import com.pd.uber_shuffle.spotifyApiHelper.SpotifyApiHelper;
import com.pd.uber_shuffle.spotifyApiHelper.SpotifyApiHelperImpl;
import com.pd.ui.controller.AccessTokenController;
import com.pd.ui.controller.UberShuffleController;

public class UIMain {
  public static void main(String[] args) {
    OkHttpCaller caller = new OkHttpCaller(new HumbleOkHttpCallerImpl());
    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller);
    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);

    SpotifyPlaylistCreator shuffler = new SpotifyPlaylistCreatorImpl(spotifyAPIHelper);
    SpotifyLibrary library = new SpotifyLibraryImpl(spotifyAPIHelper);
    SpotifyUberShuffle shuffle = new SpotifyUberShuffleImpl(library, shuffler);

    UberShuffleController shuffleController = new UberShuffleController(shuffle);
    AccessTokenController tokenController = new AccessTokenController();
    tokenController.addTokenObserver(http);

    MainForm main = new MainForm(shuffleController, tokenController);
    main.startUi();
  }
}
