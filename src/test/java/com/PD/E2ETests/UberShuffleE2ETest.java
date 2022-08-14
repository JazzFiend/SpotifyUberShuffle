package com.PD.E2ETests;

import com.PD.exceptions.ShuffleException;
import com.PD.model.SpotifyUberShuffle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class UberShuffleE2ETest {

  @Test
  @Disabled
  public void endToEndTest() throws ShuffleException {
    final String accessToken = "Bearer Token";
    final String USER_ID = "jazzFiend7";
    final int PLAYLIST_SIZE = 50;

    SpotifyUberShuffle.beginUberShuffle(accessToken, USER_ID, PLAYLIST_SIZE);
  }
}
