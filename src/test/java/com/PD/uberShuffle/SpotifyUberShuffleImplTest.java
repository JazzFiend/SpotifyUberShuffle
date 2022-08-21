package com.PD.uberShuffle;

import static org.junit.jupiter.api.Assertions.*;

import com.PD.exceptions.ShuffleException;
import com.PD.mocks.SpotifyLibrarySpy;
import com.PD.mocks.SpotifyPlaylistCreatorSpy;
import com.PD.uberShuffle.model.UberShuffleRequest;
import com.PD.uberShuffle.spotifyUberShuffle.SpotifyUberShuffleImpl;
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