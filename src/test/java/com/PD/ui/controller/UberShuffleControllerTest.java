package com.PD.ui.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.PD.exceptions.ShuffleException;
import com.PD.mocks.SpotifyUberShuffleSpy;
import com.PD.uberShuffle.model.UberShuffleRequest;
import org.junit.jupiter.api.Test;

class UberShuffleControllerTest {
  private void assertUberShuffleRequestsEqual(UberShuffleRequest actual, String expectedAccessToken, String expectedUserId, int expectedPlaylistSize) {
    assertEquals(expectedAccessToken, actual.getAccessToken());
    assertEquals(expectedUserId, actual.getUserId());
    assertEquals(expectedPlaylistSize, actual.getPlaylistSize());
  }

  @Test
  public void beginsUberShuffle() throws ShuffleException {
    SpotifyUberShuffleSpy shuffleSpy = new SpotifyUberShuffleSpy();
    UberShuffleController controller = new UberShuffleController(shuffleSpy);
    String accessToken = "AccessToken";
    String userId = "User";
    int playlistSize = 23;
    controller.clickUberShuffle(accessToken, userId, playlistSize);
    assertEquals(shuffleSpy.beginUberShuffleCount(), 1);
    assertUberShuffleRequestsEqual(shuffleSpy.lastBeginUberShuffleArgs(), accessToken, userId, playlistSize);
  }
}