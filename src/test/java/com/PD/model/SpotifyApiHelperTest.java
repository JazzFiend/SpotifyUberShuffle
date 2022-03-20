package com.PD.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.PD.mocks.HttpRequestAdapterMock;
import com.PD.model.httpAdapter.HttpRequestAdapter;
import com.PD.model.spotifyApiHelper.SpotifyApiHelper;
import com.PD.model.spotifyApiHelper.SpotifyApiHelperImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpotifyApiHelperTest {
        private SpotifyApiHelper apiHelper;

    @BeforeEach
    public void globalSetup() {
        HttpRequestAdapter httpTest = new HttpRequestAdapterMock();
        apiHelper = new SpotifyApiHelperImpl(httpTest);
    }

    @Test
    public void getFavoriteTrackIdsTest() {
        Collection<String> expectedResult = new HashSet<>();
        expectedResult.add("123");
        expectedResult.add("abc");

        Collection<String> favoriteTrackIds = apiHelper.getFavoriteTrackIds();
        assertEquals(expectedResult, favoriteTrackIds);
    }

    @Test
    public void getTrackIdsFromFavoriteAlbumsTest() {
        Collection<String> expectedResult = new HashSet<>();
        expectedResult.add("track1");
        expectedResult.add("track2");
        expectedResult.add("track3");

        Collection<String> favoriteAlbumTrackIds = apiHelper.getTrackIdsFromFavoriteAlbums();
        assertEquals(expectedResult, favoriteAlbumTrackIds);
    }

    @Test
    public void createPlaylistTest() {
        String playlistId = apiHelper.createPlaylist("Playlist Name", "Playlist Description", true, "UserID");
        assertEquals("Playlist ID", playlistId);
    }

    @Test
    public void createPlaylistExceptionTest() {
        assertThrows(RuntimeException.class, () -> apiHelper.createPlaylist("Playlist Name", "Playlist Description", true, "InterruptedException"));
    }

    @Test
    public void addToPlaylistTest() {
        List<String> songList = new ArrayList<>();
        songList.add("Track1");
        String playlistState = apiHelper.addToPlaylist("PlaylistId", songList);
        assertEquals("PlaylistSnapshot", playlistState);
    }

    @Test
    public void addToPlaylistExceptionTest() {
        List<String> songList = new ArrayList<>();
        songList.add("Exception");
        assertThrows(RuntimeException.class, () -> apiHelper.addToPlaylist("PlaylistId", songList));
    }
}
