package com.PD.E2ETests;

import com.PD.SpotifyUberShuffle;
import com.PD.exceptions.ShuffleException;
import com.PD.httpAdapter.HttpRequestAdapter;
import com.PD.httpAdapter.OkHttpCaller;
import com.PD.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.spotifyApiHelper.SpotifyApiHelper;
import com.PD.spotifyApiHelper.SpotifyApiHelperImpl;
import org.junit.Ignore;
import org.junit.Test;

public class SpotifyUberShuffleTest {

    @Test
    @Ignore
    public void endToEndTest() throws ShuffleException {
        final String accessToken = "Bearer ";
        final String USER_ID = "jazzFiend7";
        final int PLAYLIST_SIZE = 50;

        HttpRequestAdapter http = new OkHttpHttpRequestAdapter(new OkHttpCaller(), accessToken);
        SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
        SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
        shuffler.populateLibrary();
        shuffler.createShufflePlaylist(USER_ID, PLAYLIST_SIZE);
    }
}
