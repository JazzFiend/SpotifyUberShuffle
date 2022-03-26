package com.PD.model;

import com.PD.model.spotifyApiHelper.SpotifyApiHelper;
import java.util.Collection;
import java.util.HashSet;

public class SpotifyLibrary {
    private final SpotifyApiHelper spotifyAPIHelper;
    private final Collection<String> trackLibrary = new HashSet<>();

    public SpotifyLibrary(SpotifyApiHelper spotifyAPIHelper) {
      this.spotifyAPIHelper = spotifyAPIHelper;
    }

    public Collection<String> populateLibrary() {
        Collection<String> trackSet = spotifyAPIHelper.getFavoriteTrackIds();
        Collection<String> albumTrackSet = spotifyAPIHelper.getTrackIdsFromFavoriteAlbums();
        trackLibrary.addAll(trackSet);
        trackLibrary.addAll(albumTrackSet);
        return trackLibrary;
    }

    public Collection<String> getTrackLibrary() { return trackLibrary; }
}


