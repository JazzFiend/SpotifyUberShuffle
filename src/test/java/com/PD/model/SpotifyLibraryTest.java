package com.PD.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.PD.mocks.SpotifyAPIHelperMock;
import com.PD.model.helpers.ModelTestHelpers;
import java.util.Collection;
import org.junit.jupiter.api.Test;

class SpotifyLibraryTest {
  @Test
  public void populateLibraryTest() {
    SpotifyLibrary library = new SpotifyLibrary(new SpotifyAPIHelperMock());
    Collection<String> expectedLibrary = ModelTestHelpers.createDummyLibrary();

    library.populateLibrary();
    assertEquals(expectedLibrary, library.getTrackLibrary());
  }
}