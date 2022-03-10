package com.PD.model.spotifyApiHelper;

import com.PD.model.httpAdapter.HttpRequestAdapter;
import com.PD.model.spotifyIdExtractor.AlbumIdExtractor;
import com.PD.model.spotifyIdExtractor.SpotifyIdExtractor;
import com.PD.model.spotifyIdExtractor.TrackIdExtractor;
import com.PD.model.spotifyIdExtractor.TrackIdFromAlbumExtractor;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class SpotifyApiHelperImpl implements SpotifyApiHelper {
    private final HttpRequestAdapter httpRequestAdapter;

    public SpotifyApiHelperImpl(HttpRequestAdapter httpRequestAdapter) {
        this.httpRequestAdapter = httpRequestAdapter;
    }

    @Override
    public Collection<String> getFavoriteTrackIds() {
        String url = "https://api.spotify.com/v1/me/tracks?limit=50";
        return getIDsFromURL(url, new TrackIdExtractor());
    }

    @Override
    public Collection<String> getUsersFavoriteAlbums() {
        String url = "https://api.spotify.com/v1/me/albums?limit=50";
        return getIDsFromURL(url, new AlbumIdExtractor());
    }

    @Override
    public Collection<String> getTrackIdsFromFavoriteAlbums() {
        Collection<String> albumIdSet = getUsersFavoriteAlbums();
        Collection<String> trackIdSet = new HashSet<>();

        for (String s : albumIdSet) {
            trackIdSet.addAll(getTrackIdsFromAlbum(s));
        }
        return trackIdSet;
    }

    @Override
    public Collection<String> getTrackIdsFromAlbum(String albumId) {
        String url = String.format("https://api.spotify.com/v1/albums/%s/tracks", albumId);
        return getIDsFromURL(url, new TrackIdFromAlbumExtractor());
    }

    @Override
    public String createPlaylist(String playlistName, String playlistDescription, boolean isPublic, String userId) {
        String url = String.format("https://api.spotify.com/v1/users/%s/playlists", userId);
        Map<String, String> bodyParams = constructBodyParams(playlistName, playlistDescription, isPublic);

        try {
            JSONObject response = httpRequestAdapter.makePostRequest(url, bodyParams);
            return response.getString("id");
        } catch(JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> constructBodyParams(String playlistName, String playlistDescription, boolean isPublic) {
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("name", playlistName);
        bodyParams.put("description", playlistDescription);
        bodyParams.put("public", (isPublic ? "true" : "false"));
        return bodyParams;
    }

    //TODO: Figure out how to send songs as body argument instead in query parameters.
    @Override
    public String addToPlaylist(String playlistId, List<String> songList) {
        String playListSnapshot = "";
        String url = String.format("https://api.spotify.com/v1/playlists/%s/tracks?uris=spotify:track:", playlistId);
        try {
            for(String songID : songList) {
                String urlWithTrackID = url + songID;
                JSONObject response = httpRequestAdapter.makePostRequest(urlWithTrackID);
                playListSnapshot = response.getString("snapshot_id");
            }
        } catch(JSONException e) {
            throw new RuntimeException(e);
        }
        return playListSnapshot;
    }

    //TODO: Refactor this
    private Collection<String> getIDsFromURL(String url, SpotifyIdExtractor idExtractor) {
        Collection<String> trackIdSet = new HashSet<>();
        String nextUrlRequest = url;
        while(!nextUrlRequest.equals("null")) {
            try {
                JSONObject response = httpRequestAdapter.makeGetRequest(nextUrlRequest);
                if(!response.isNull("next")) {
                    nextUrlRequest = response.getString("next");
                } else {
                    nextUrlRequest = "null";
                }
                List<String> trackIdList = idExtractor.extractIDs(response);
                trackIdSet.addAll(trackIdList);
            } catch(JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return trackIdSet;
    }
}
