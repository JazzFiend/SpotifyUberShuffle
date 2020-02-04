package com.example.spotifyubershuffle;

import com.example.spotifyubershuffle.exceptions.ShuffleException;
import com.example.spotifyubershuffle.httpAdapter.HttpAdapter;
import com.example.spotifyubershuffle.httpAdapter.HttpAdapterVolleyImpl;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelper;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelperImpl;

import org.junit.Test;

import static org.junit.Assert.fail;

public class EndToEndTest {
    @Test
    public void uberShuffleTest() {
        try {
            //TODO: Need to login instead hardcoding token.
            final String ACCESS_TOKEN = "Bearer ACCESS_TOKEN";
            final String USER_ID = "USERNAME";
            final int PLAYLIST_SIZE = 50;
            HttpAdapter http = new HttpAdapterVolleyImpl(ACCESS_TOKEN);
            SpotifyAPIHelper spotifyAPIHelper = new SpotifyAPIHelperImpl(http);
            SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
            shuffler.populateLibrary();
            shuffler.createShufflePlaylist(USER_ID, PLAYLIST_SIZE);
        } catch (ShuffleException e) {
            fail(e.getMessage());
        }
    }
}