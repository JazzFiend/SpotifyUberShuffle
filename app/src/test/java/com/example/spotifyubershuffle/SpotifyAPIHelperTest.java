package com.example.spotifyubershuffle;

import com.example.spotifyubershuffle.httpAdapter.HttpRequestAdapter;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelper;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelperImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import mocks.HttpRequestAdapterMock;

import static org.junit.Assert.*;

public class SpotifyAPIHelperTest {
    private SpotifyAPIHelper apiHelper;

    @Before
    public void globalSetup() {
        HttpRequestAdapter httpTest = new HttpRequestAdapterMock();
        apiHelper = new SpotifyAPIHelperImpl(httpTest);
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

    @Test(expected = RuntimeException.class)
    public void createPlaylistExceptionTest() {
        apiHelper.createPlaylist("Playlist Name", "Playlist Description", true, "InterruptedException");
    }

    @Test
    public void addToPlaylistTest() {
        List<String> songList = new ArrayList<>();
        songList.add("Track1");
        String playlistState = apiHelper.addToPlaylist("PlaylistId", songList);
        assertEquals("PlaylistSnapshot", playlistState);
    }

    @Test(expected = RuntimeException.class)
    public void addToPlaylistExceptionTest() {
        List<String> songList = new ArrayList<>();
        songList.add("Exception");
        apiHelper.addToPlaylist("PlaylistId", songList);
    }
}