package com.PD.controller;

import com.PD.model.SpotifyUberShuffle;
import com.PD.exceptions.ShuffleException;
import com.PD.model.httpAdapter.HttpRequestAdapter;
import com.PD.model.httpAdapter.OkHttpCaller;
import com.PD.model.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.model.spotifyApiHelper.SpotifyApiHelper;
import com.PD.model.spotifyApiHelper.SpotifyApiHelperImpl;

public class UberShuffleController {
  public static void clickUberShuffle(String accessToken, String userId, int playlistSize) throws ShuffleException {
    HttpRequestAdapter http = new OkHttpHttpRequestAdapter(new OkHttpCaller(), accessToken);
    SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
    SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
    shuffler.populateLibrary();
    shuffler.createShufflePlaylist(userId, playlistSize);
  }
}
