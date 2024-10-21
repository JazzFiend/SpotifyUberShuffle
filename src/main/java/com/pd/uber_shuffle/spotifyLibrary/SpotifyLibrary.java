package com.pd.uber_shuffle.spotifyLibrary;

import java.util.Collection;

public interface SpotifyLibrary {
    public void populateLibrary();

    public Collection<String> getTrackLibrary();
}
