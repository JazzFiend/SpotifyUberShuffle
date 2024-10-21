package com.pd.uber_shuffle;

import static com.pd.uber_shuffle.helpers.ModelTestHelpers.createDummyLibrary;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pd.exceptions.ShuffleException;
import com.pd.mocks.SpotifyAPIHelperMock;
import com.pd.uber_shuffle.spotifyPlaylistCreator.SpotifyPlaylistCreatorImpl;
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
