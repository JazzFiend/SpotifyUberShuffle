package com.PD.model;

import static org.junit.Assert.assertEquals;

import com.PD.model.SpotifyUberShuffle;
import java.util.Collection;
import java.util.HashSet;

import com.PD.exceptions.ShuffleException;
import com.PD.mocks.SpotifyAPIHelperMock;

import org.junit.Test;

public class SpotifyUberShuffleTest {
    private SpotifyUberShuffle uberShuffle;

    @Test
    public void populateLibraryTest() {
        initializeUberShuffle();
        Collection<String> expectedLibrary = createDummyLibrary();

        uberShuffle.populateLibrary();
        assertEquals(expectedLibrary, uberShuffle.getTrackLibrary());
    }

    @Test(expected = ShuffleException.class)
    public void createShufflePlaylistEmptyLibraryTest() throws ShuffleException {
        SpotifyUberShuffle uberShuffle = new SpotifyUberShuffle(new SpotifyAPIHelperMock());
        uberShuffle.createShufflePlaylist("userID", 5);
    }

    @Test
    public void createShufflePlaylistTest() throws ShuffleException {
        initializeUberShuffle();

        uberShuffle.populateLibrary();
        assertEquals("Playlist ID", uberShuffle.createShufflePlaylist("userID", 3));
    }

    private void initializeUberShuffle() {
        uberShuffle = new SpotifyUberShuffle(new SpotifyAPIHelperMock());
    }

    private Collection<String> createDummyLibrary() {
        Collection<String> dummyLibrary = new HashSet<>();
        dummyLibrary.add("Track1");
        dummyLibrary.add("Track2");
        dummyLibrary.add("Track3");
        dummyLibrary.add("TrackA");
        dummyLibrary.add("TrackB");
        dummyLibrary.add("TrackC");

        return dummyLibrary;
    }
}
