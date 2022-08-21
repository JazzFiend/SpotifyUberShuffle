package com.PD.ui.view;

import com.PD.uberShuffle.spotifyLibrary.SpotifyLibrary;
import com.PD.uberShuffle.spotifyLibrary.SpotifyLibraryImpl;
import com.PD.uberShuffle.spotifyPlaylistCreator.SpotifyPlaylistCreator;
import com.PD.uberShuffle.spotifyPlaylistCreator.SpotifyPlaylistCreatorImpl;
import com.PD.uberShuffle.spotifyUberShuffle.SpotifyUberShuffle;
import com.PD.uberShuffle.spotifyUberShuffle.SpotifyUberShuffleImpl;
import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import com.PD.uberShuffle.httpAdapter.HumbleOkHttpCallerImpl;
import com.PD.uberShuffle.httpAdapter.OkHttpCaller;
import com.PD.uberShuffle.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelper;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelperImpl;
import com.PD.ui.controller.AccessTokenController;
import com.PD.ui.controller.UberShuffleController;

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
