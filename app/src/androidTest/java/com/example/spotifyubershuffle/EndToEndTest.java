package com.example.spotifyubershuffle;

import com.example.spotifyubershuffle.Exceptions.ShuffleException;
import com.example.spotifyubershuffle.HttpAdapter.HttpAdapter;
import com.example.spotifyubershuffle.HttpAdapter.HttpAdapterVolleyImpl;
import com.example.spotifyubershuffle.SpotifyAPIHelper.SpotifyAPIHelper;
import com.example.spotifyubershuffle.SpotifyAPIHelper.SpotifyAPIHelperImpl;

import org.junit.Test;

import static org.junit.Assert.fail;

public class EndToEndTest {
    @Test
    public void trackLookupTest() {
        try {
            //TODO: Need to login instead hardcoding token.
            final String ACCESS_TOKEN = "Bearer INSERT_ACCESS_TOKEN";
            final String USER_ID = "INSERT_USER_ID";
            HttpAdapter http = new HttpAdapterVolleyImpl(ACCESS_TOKEN);
            SpotifyAPIHelper spotifyAPIHelper = new SpotifyAPIHelperImpl(http);
            SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
            shuffler.populateLibrary();
            shuffler.createShufflePlaylist(USER_ID);
        } catch (ShuffleException e) {
            fail(e.getMessage());
        }
    }
}