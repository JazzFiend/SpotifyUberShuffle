package com.PD.mocks;

import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestAdapterMock implements HttpRequestAdapter {
    private String accessToken;

    @Override
    public JSONObject makeGetRequest(String url) {
        String getFavoriteTrackResponse1 = "{\"items\": [{\"track\": {\"id\": \"123\"}},{\"track\": {\"id\": \"abc\"}}],\"next\": \"sendFavoriteTrackResponse2\"}";
        String getFavoriteTrackResponse2 = "{\"items\": [{\"track\": {\"id\": \"123\"}}],\"next\": \"null\"}";
        String getFavoriteAlbumResponse1 = "{\"items\": [{\"album\": {\"id\": \"123\"}},{\"album\": {\"id\": \"abc\"}}],\"next\": \"sendFavoriteAlbumResponse2\"}";
        String getFavoriteAlbumResponse2 = "{\"items\": [{\"album\": {\"id\": \"123\"}}],\"next\": \"null\"}";
        String getAlbumTracksResponse1 = "{\"items\": [{\"id\": \"track1\"},{\"id\": \"track2\"}],\"next\": \"null\"}";
        String getAlbumTracksResponse2 = "{\"items\": [{\"id\": \"track1\"},{\"id\": \"track3\"}],\"next\": \"null\"}";

        try {
            switch (url) {
                case "https://api.spotify.com/v1/me/tracks?limit=50":
                    return new JSONObject(getFavoriteTrackResponse1);
                case "sendFavoriteTrackResponse2":
                    return new JSONObject(getFavoriteTrackResponse2);
                case "https://api.spotify.com/v1/me/albums?limit=50":
                    return new JSONObject(getFavoriteAlbumResponse1);
                case "sendFavoriteAlbumResponse2":
                    return new JSONObject(getFavoriteAlbumResponse2);
                case "https://api.spotify.com/v1/albums/123/tracks":
                    return new JSONObject(getAlbumTracksResponse1);
                case "https://api.spotify.com/v1/albums/abc/tracks":
                    return new JSONObject(getAlbumTracksResponse2);
                default:
                    throw new RuntimeException("Bad Test URL Seen");
            }
        } catch(JSONException e) {
            // Returning null here. The test data is correct and shouldn't change much, so we shouldn't throw a JSON Exception.
            // If we do, then the null will cause a null pointer exception.
            return null;
        }
    }

    @Override
    public JSONObject makeGetRequest(String url, Map<String, String> headers) {
        return null;
    }

    @Override
    public JSONObject makePostRequest(String url) {
        return makePostRequest(url, new HashMap<>());
    }

    @Override
    public JSONObject makePostRequest(String url, Map<String, String> body) {
        try {
            switch (url) {
                case "https://api.spotify.com/v1/users/UserID/playlists":
                    return new JSONObject("{\"id\": \"Playlist ID\"}");
                case "https://api.spotify.com/v1/playlists/PlaylistId/tracks?uris=spotify:track:Track1":
                    return new JSONObject("{\"snapshot_id\": \"PlaylistSnapshot\"}");
                case "https://api.spotify.com/v1/users/InterruptedException/playlists":
                default:
                    throw new RuntimeException("Bad Test URL Seen");
            }
        } catch(JSONException e) {
            // Returning null here. The test data is correct and shouldn't change much, so we shouldn't throw a JSON Exception.
            // If we do, then the null will cause a null pointer exception.
            return null;
        }
    }

    public String getToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public JSONObject makePostRequest(String url, Map<String, String> bodyParams,
        Map<String, String> headers) {
        return null;
    }

    @Override
    public JSONObject makePostRequestNoAuth(String url, Map<String, String> bodyParams,
        Map<String, String> headers) {
        return null;
    }
}
