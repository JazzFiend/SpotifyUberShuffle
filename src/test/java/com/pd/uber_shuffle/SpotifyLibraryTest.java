package com.pd.uber_shuffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pd.mocks.SpotifyAPIHelperMock;
import com.pd.uber_shuffle.helpers.ModelTestHelpers;
import com.pd.uber_shuffle.spotifyLibrary.SpotifyLibraryImpl;
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