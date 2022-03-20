package com.PD.E2ETests;

import com.PD.model.SpotifyUberShuffle;
import com.PD.exceptions.ShuffleException;
import com.PD.model.httpAdapter.HttpRequestAdapter;
import com.PD.model.httpAdapter.OkHttpCaller;
import com.PD.model.httpAdapter.OkHttpHttpRequestAdapter;
import com.PD.model.spotifyApiHelper.SpotifyApiHelper;
import com.PD.model.spotifyApiHelper.SpotifyApiHelperImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SpotifyUberShuffleTest {

    @Test
    @Disabled
    public void endToEndTest() throws ShuffleException {
        final String accessToken = "Bearer BQBDGB9ig-pDkMsp_laI0_A5hHniD3V53MPwAZNaPR9HoGhX9hcOLanWxfZ2_yjtrpV-Hv117mPNsWyqug2LZ1qLjvmHnGFSlZts34njutN47FMKMnxKsxzjE9xgInmH8x6g5mUI3aObeBNjyhD94q0HdnflQpZwsXL_GFqI-FT92VLUDIEqGa6jL7KClb-ofky8Dh0xwZkB_wNJjJ23jdAvXyOHj2gEJECWUEv3AhbV6Bg";
        final String USER_ID = "jazzFiend7";
        final int PLAYLIST_SIZE = 50;

        HttpRequestAdapter http = new OkHttpHttpRequestAdapter(new OkHttpCaller(), accessToken);
        SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
        SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
        shuffler.populateLibrary();
        shuffler.createShufflePlaylist(USER_ID, PLAYLIST_SIZE);
    }
}
