package com.pd.uber_shuffle;

import static org.junit.jupiter.api.Assertions.*;

import com.pd.exceptions.ShuffleException;
import com.pd.mocks.SpotifyLibrarySpy;
import com.pd.mocks.SpotifyPlaylistCreatorSpy;
import com.pd.uber_shuffle.model.UberShuffleRequest;
import com.pd.uber_shuffle.spotifyUberShuffle.SpotifyUberShuffleImpl;
import org.junit.jupiter.api.Test;

class SpotifyUberShuffleImplTest {
  @Test
  public void uberShuffleStarted() throws ShuffleException {
    SpotifyLibrarySpy librarySpy = new SpotifyLibrarySpy();
    SpotifyPlaylistCreatorSpy playlistCreatorSpy = new SpotifyPlaylistCreatorSpy();

    SpotifyUberShuffleImpl uberShuffle = new SpotifyUberShuffleImpl(librarySpy, playlistCreatorSpy);
    uberShuffle.beginUberShuffle(new UberShuffleRequest("UserId", 10));
    assertEquals(librarySpy.getPopulateCalls(), 1);
    assertEquals(playlistCreatorSpy.getCreateCalls(), 1);
  }
}