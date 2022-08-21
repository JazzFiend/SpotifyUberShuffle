package com.PD.uberShuffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.PD.mocks.SpotifyAPIHelperMock;
import com.PD.uberShuffle.helpers.ModelTestHelpers;
import com.PD.uberShuffle.spotifyLibrary.SpotifyLibraryImpl;
import java.util.Collection;
import org.junit.jupiter.api.Test;

class SpotifyLibraryTest {
  @Test
  public void populateLibraryTest() {
    SpotifyLibraryImpl library = new SpotifyLibraryImpl(new SpotifyAPIHelperMock());
    Collection<String> expectedLibrary = ModelTestHelpers.createDummyLibrary();

    library.populateLibrary();
    assertEquals(expectedLibrary, library.getTrackLibrary());
  }
}