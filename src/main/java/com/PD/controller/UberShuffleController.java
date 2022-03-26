package com.PD.controller;

import com.PD.exceptions.ShuffleException;
import com.PD.model.SpotifyUberShuffle;

public class UberShuffleController {
  public static void clickUberShuffle(String accessToken, String userId, int playlistSize) throws ShuffleException {
    SpotifyUberShuffle.beginUberShuffle(accessToken, userId, playlistSize);
  }
}
