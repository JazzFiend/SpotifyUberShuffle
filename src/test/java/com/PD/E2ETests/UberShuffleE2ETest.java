package com.PD.E2ETests;

import com.PD.exceptions.ShuffleException;
import com.PD.uberShuffle.SpotifyUberShuffleImpl;
import com.PD.uberShuffle.model.UberShuffleRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class UberShuffleE2ETest {

  @Test
  @Disabled
  public void endToEndTest() throws ShuffleException {
    final String accessToken = "Bearer Token";
    final String USER_ID = "jazzFiend7";
    final int PLAYLIST_SIZE = 50;

    SpotifyUberShuffleImpl shuffle = new SpotifyUberShuffleImpl();
    shuffle.beginUberShuffle(new UberShuffleRequest(accessToken, USER_ID, PLAYLIST_SIZE));
  }
}
