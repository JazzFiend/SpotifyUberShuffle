package com.PD.ui.view;

import com.PD.uberShuffle.SpotifyLibrary;
import com.PD.uberShuffle.SpotifyLibraryImpl;
import com.PD.uberShuffle.SpotifyPlaylistCreator;
import com.PD.uberShuffle.SpotifyPlaylistCreatorImpl;
import com.PD.uberShuffle.SpotifyUberShuffle;
import com.PD.uberShuffle.SpotifyUberShuffleImpl;
import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import com.PD.uberShuffle.httpAdapter.HumbleOkHttpCallerImpl;
import com.PD.uberShuffle.httpAdapter.OkHttpCaller;
import com.PD.uberShuffle.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelper;
import com.PD.uberShuffle.spotifyApiHelper.SpotifyApiHelperImpl;
import com.PD.ui.controller.UberShuffleController;

// TODO: Looks like there's a null pointer exception when starting the GUI. I'll need to fix that first before coming back here.

public class UIMain {
  public static void main(String[] args) {

    String token = "Bearer Token";


    OkHttpCaller caller = new OkHttpCaller(new HumbleOkHttpCallerImpl());
    // TODO: I need a version of this that has a settable token!!!!
    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(caller, token);
    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
    SpotifyPlaylistCreator shuffler = new SpotifyPlaylistCreatorImpl(spotifyAPIHelper);
    SpotifyLibrary library = new SpotifyLibraryImpl(spotifyAPIHelper);



    SpotifyUberShuffle shuffle = new SpotifyUberShuffleImpl(library, shuffler);
    UberShuffleController controller = new UberShuffleController(shuffle);

    MainForm main = new MainForm(controller);
    main.startUi();
  }
}
