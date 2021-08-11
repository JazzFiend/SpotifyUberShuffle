package com.PD.mocks;

import com.PD.spotifyApiHelper.SpotifyApiHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class SpotifyAPIHelperMock implements SpotifyApiHelper {
      public SpotifyAPIHelperMock() {}

    @Override
    public Collection<String> getFavoriteTrackIds() {
        Collection<String> favoriteTrackIds = new HashSet<>();
        favoriteTrackIds.add("Track1");
        favoriteTrackIds.add("Track2");
        favoriteTrackIds.add("Track3");

        return favoriteTrackIds;
    }

    @Override
    public Collection<String> getUsersFavoriteAlbums() {
        return null;
    }

    @Override
    public Collection<String> getTrackIdsFromFavoriteAlbums() {
        Collection<String> favoriteAlbumTrackIds = new HashSet<>();
        favoriteAlbumTrackIds.add("TrackA");
        favoriteAlbumTrackIds.add("TrackB");
        favoriteAlbumTrackIds.add("TrackC");

        return favoriteAlbumTrackIds;
    }

    @Override
    public Collection<String> getTrackIdsFromAlbum(String albumId) {
        return null;
    }

    @Override
    public String createPlaylist(String playlistName, String playlistDescription, boolean isPublic, String userId) {
        if(!userId.equals("userID")) {
            throw new RuntimeException(String.format("User Id is not as expected. Expected \"userId\", Recieved %s", userId));
        }
        return "Playlist ID";
    }

    @SuppressWarnings("checkstyle:LineLength")
    @Override
    public String addToPlaylist(String playlistId, List<String> songList) {
        if(!playlistId.equals("Playlist ID")) {
            throw new RuntimeException(String.format("Playlist ID is not as expected. Expected \"Playlist ID\", Recieved %s", playlistId));
        }
        if(songList.isEmpty()) {
            throw new RuntimeException("Song list must have at least one entry.");
        }

        return "PlaylistSnapshot";
    }
}
