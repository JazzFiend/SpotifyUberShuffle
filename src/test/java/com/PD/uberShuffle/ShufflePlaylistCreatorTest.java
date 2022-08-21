package com.PD.uberShuffle;

import static com.PD.uberShuffle.helpers.ModelTestHelpers.createDummyLibrary;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.PD.exceptions.ShuffleException;
import com.PD.mocks.SpotifyAPIHelperMock;
import com.PD.uberShuffle.spotifyPlaylistCreator.SpotifyPlaylistCreatorImpl;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShufflePlaylistCreatorTest {
  private SpotifyPlaylistCreatorImpl uberShuffle;

  @BeforeEach
  public void setup() {
    uberShuffle = new SpotifyPlaylistCreatorImpl(new SpotifyAPIHelperMock());
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
