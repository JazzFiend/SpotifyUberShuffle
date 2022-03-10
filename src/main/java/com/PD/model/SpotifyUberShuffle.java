package com.PD.model;

import com.PD.exceptions.ShuffleException;
import com.PD.model.spotifyApiHelper.SpotifyApiHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SpotifyUberShuffle {
    private final Collection<String> trackLibrary;
    private final SpotifyApiHelper spotifyAPIHelper;

    public SpotifyUberShuffle(SpotifyApiHelper spotifyAPIHelper) {
        trackLibrary = new HashSet<>();
        this.spotifyAPIHelper = spotifyAPIHelper;
    }

    public void populateLibrary() {
        Collection<String> trackSet = spotifyAPIHelper.getFavoriteTrackIds();
        Collection<String> albumTrackSet = spotifyAPIHelper.getTrackIdsFromFavoriteAlbums();
        trackLibrary.addAll(trackSet);
        trackLibrary.addAll(albumTrackSet);
    }

    public String createShufflePlaylist(String userId, int trackCount) throws ShuffleException {
        List<String> shuffledSongIds = shuffle(trackCount);
        String playlistId = spotifyAPIHelper.createPlaylist("UberShuffle", "UberShuffle", false, userId);
        spotifyAPIHelper.addToPlaylist(playlistId, shuffledSongIds);
        return playlistId;
    }

    public Collection<String> getTrackLibrary() { return trackLibrary; }

    private List<String> shuffle(int trackCount) throws ShuffleException {
        if (trackLibrary.isEmpty()) { throw new ShuffleException("The library is empty"); }

        Random rng = new Random();
        final int librarySize = trackLibrary.size();
        List<String> trackLibraryList = new ArrayList<>(trackLibrary);
        List<String> shufflePlaylist = new ArrayList<>();

        while (shufflePlaylist.size() < trackCount && shufflePlaylist.size() < trackLibraryList.size()) {
            int trackNumber = rng.nextInt(librarySize);
            if (!shufflePlaylist.contains(trackLibraryList.get(trackNumber))) {
                shufflePlaylist.add(trackLibraryList.get(trackNumber));
            }
        }
        return shufflePlaylist;
    }

}
