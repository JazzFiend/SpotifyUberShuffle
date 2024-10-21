package com.pd.uber_shuffle.spotifyLibrary;

import com.pd.uber_shuffle.spotifyApiHelper.SpotifyApiHelper;
import java.util.Collection;
import java.util.HashSet;

public class SpotifyLibraryImpl implements SpotifyLibrary {
    private final SpotifyApiHelper spotifyAPIHelper;
    private final Collection<String> trackLibrary = new HashSet<>();

    public SpotifyLibraryImpl(SpotifyApiHelper spotifyAPIHelper) {
      this.spotifyAPIHelper = spotifyAPIHelper;
    }

    @Override
    public void populateLibrary() {
        Collection<String> trackSet = spotifyAPIHelper.getFavoriteTrackIds();
        Collection<String> albumTrackSet = spotifyAPIHelper.getTrackIdsFromFavoriteAlbums();
        trackLibrary.addAll(trackSet);
        trackLibrary.addAll(albumTrackSet);
    }

    @Override
    public Collection<String> getTrackLibrary() {
        return new HashSet<String>(trackLibrary);
    }
}


