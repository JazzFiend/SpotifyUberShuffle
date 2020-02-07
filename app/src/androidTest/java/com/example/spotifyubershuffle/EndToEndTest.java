package com.example.spotifyubershuffle;

import com.example.spotifyubershuffle.exceptions.ShuffleException;
import com.example.spotifyubershuffle.httpAdapter.HttpRequestAdapter;
import com.example.spotifyubershuffle.httpAdapter.HttpRequestAdapterImpl;
import com.example.spotifyubershuffle.httpAdapter.HttpVolleyRequestSender;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelper;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelperImpl;

import org.junit.Test;

import static org.junit.Assert.fail;

public class EndToEndTest {
    @Test
    public void uberShuffleTest() {
        try {
            //TODO: Need to login instead hardcoding token.
            final String ACCESS_TOKEN = "Bearer BQBIZlDU7-p8szr7406SGSnNrFtnazvvkkYAZOxlINs7LefMdx6UPQzg4tTB61ib8VXfU48qiKZcmwzxzdvmI7Eefsf3luIMbAvYZHr5OzhkMuRXNmYIVv8Ou4ktA6vSwJR5K2fpmsbtJkiCE-fulff_NvFjnJZHtpMxKtkVuzBuSjlnu-lYmKnwg_WbAVpYe3UgVBn2dsjAmyqq-6HYbtsZJIApTYt-wmmUL0qpG72W";
            final String USER_ID = "jazzfiend7";
            final int PLAYLIST_SIZE = 11;
            HttpRequestAdapter http = new HttpRequestAdapterImpl(new HttpVolleyRequestSender(ACCESS_TOKEN));
            SpotifyAPIHelper spotifyAPIHelper = new SpotifyAPIHelperImpl(http);
            SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
            shuffler.populateLibrary();
            shuffler.createShufflePlaylist(USER_ID, PLAYLIST_SIZE);
        } catch (ShuffleException e) {
            fail(e.getMessage());
        }
    }
}