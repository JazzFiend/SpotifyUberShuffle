package com.PD.model;

import static com.PD.model.helpers.ModelTestHelpers.createDummyLibrary;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.PD.exceptions.ShuffleException;
import com.PD.mocks.SpotifyAPIHelperMock;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShufflePlaylistCreatorTest {
  private ShufflePlaylistCreator uberShuffle;

  @BeforeEach
  public void setup() {
    uberShuffle = new ShufflePlaylistCreator(new SpotifyAPIHelperMock());
  }

  @Test
  public void createShufflePlaylistEmptyLibraryTest() {
    assertThrows(ShuffleException.class, () ->
      uberShuffle.createShufflePlaylist("userID", 5, new HashSet<>()));
  }

  @Test
  public void createShufflePlaylistTest() throws ShuffleException {
    assertEquals("Playlist ID", uberShuffle.createShufflePlaylist("userID", 3, createDummyLibrary()));
  }
}
