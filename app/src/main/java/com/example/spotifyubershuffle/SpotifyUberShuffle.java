package com.example.spotifyubershuffle;

import androidx.annotation.VisibleForTesting;

import com.example.spotifyubershuffle.Exceptions.ShuffleException;
import com.example.spotifyubershuffle.SpotifyAPIHelper.SpotifyAPIHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SpotifyUberShuffle {
    private final Collection<String> trackLibrary;
    private final SpotifyAPIHelper spotifyAPIHelper;

    public SpotifyUberShuffle(SpotifyAPIHelper spotifyAPIHelper) {
        trackLibrary = new HashSet<>();
        this.spotifyAPIHelper = spotifyAPIHelper;
    }

    public void populateLibrary() {
        Collection<String> trackSet = spotifyAPIHelper.getFavoriteTrackIds();
        Collection<String> albumTrackSet = spotifyAPIHelper.getTrackIdsFromFavoriteAlbums();
        trackLibrary.addAll(trackSet);
        trackLibrary.addAll(albumTrackSet);
    }

    public String createShufflePlaylist(String userId) throws ShuffleException {
        List<String> shuffledSongIds = shuffle();
        String playlistId = spotifyAPIHelper.createPlaylist("UberShuffle", "UberShuffle", false, userId);
        return spotifyAPIHelper.addToPlaylist(playlistId, shuffledSongIds);
    }

    @VisibleForTesting
    public Collection<String> getTrackLibrary() { return trackLibrary; }

    private List<String> shuffle() throws ShuffleException {
        if(trackLibrary.isEmpty()) { throw new ShuffleException("The library is empty"); }

        //TODO: Should probably make this an argument
        final int SHUFFLE_PLAYLIST_SIZE = 50;
        Random rng = new Random();
        final int librarySize = trackLibrary.size();
        List<String> trackLibraryList = new ArrayList<>(trackLibrary);
        List<String> shufflePlaylist = new ArrayList<>();

        while(shufflePlaylist.size() < SHUFFLE_PLAYLIST_SIZE && shufflePlaylist.size() < trackLibraryList.size()) {
            int trackNumber = rng.nextInt(librarySize);
            if(!shufflePlaylist.contains(trackLibraryList.get(trackNumber))) {
                shufflePlaylist.add(trackLibraryList.get(trackNumber));
            }
        }
        return shufflePlaylist;
    }
}
