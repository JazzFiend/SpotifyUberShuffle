package com.pd.ui.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.pd.exceptions.ShuffleException;
import com.pd.mocks.SpotifyUberShuffleSpy;
import com.pd.uber_shuffle.model.UberShuffleRequest;
import org.junit.jupiter.api.Test;

class UberShuffleControllerTest {
  private void assertUberShuffleRequestsEqual(UberShuffleRequest actual, String expectedUserId, int expectedPlaylistSize) {
    assertEquals(expectedUserId, actual.getUserId());
    assertEquals(expectedPlaylistSize, actual.getPlaylistSize());
  }

  @Test
  public void beginsUberShuffle() throws ShuffleException {
    SpotifyUberShuffleSpy shuffleSpy = new SpotifyUberShuffleSpy();
    UberShuffleController controller = new UberShuffleController(shuffleSpy);
    String userId = "User";
    int playlistSize = 23;
    controller.clickUberShuffle(userId, playlistSize);
    assertEquals(shuffleSpy.beginUberShuffleCount(), 1);
    assertUberShuffleRequestsEqual(shuffleSpy.lastBeginUberShuffleArgs(), userId, playlistSize);
  }
}
