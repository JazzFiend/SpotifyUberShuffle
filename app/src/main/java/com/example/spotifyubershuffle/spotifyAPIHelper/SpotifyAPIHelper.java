package com.example.spotifyubershuffle.spotifyAPIHelper;

import java.util.Collection;
import java.util.List;

public interface SpotifyAPIHelper {
    Collection<String> getFavoriteTrackIds();

    Collection<String> getUsersFavoriteAlbums();

    Collection<String> getTrackIdsFromFavoriteAlbums();

    Collection<String> getTrackIdsFromAlbum(String albumId);

    String createPlaylist(String playlistName, String playlistDescription, boolean isPublic, String userId);

    String addToPlaylist(String playlistId, List<String> songList);

}
