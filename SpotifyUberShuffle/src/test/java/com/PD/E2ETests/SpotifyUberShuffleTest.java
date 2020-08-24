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
        final String accessToken = "Bearer BQDVo4Irf0is_nXipdDDyAaR2of0MGtCTbT_IwrLhCSJ7HqanjV9CLAZhkwNsJxQHoEpNVwk15MvEGPqo2TAis_JIVg1w5cOCMbK5uk5oHkWbelHDo7rcCsQi3Nl0VnRbWzfIU1haT5NdI_XNRIYzHPs8oRgq1qobjhB1tXKzVxdlac-CtBFzVdyifSVtYJi-zayZQcp5mTaqlI6eHfz9dAstF1T9nsUPb6B12MSlhnA";
        final String USER_ID = "jazzFiend7";
        final int PLAYLIST_SIZE = 50;

        HttpRequestAdapter http = new OkHttpHttpRequestAdapter(new OkHttpCaller(), accessToken);
        SpotifyApiHelper spotifyAPIHelper = new SpotifyApiHelperImpl(http);
        SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
        shuffler.populateLibrary();
        shuffler.createShufflePlaylist(USER_ID, PLAYLIST_SIZE);
    }
}
