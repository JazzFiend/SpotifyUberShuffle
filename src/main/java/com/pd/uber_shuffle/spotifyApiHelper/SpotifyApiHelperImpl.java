package com.pd.uber_shuffle.spotifyApiHelper;

import com.pd.uber_shuffle.http_adapter.HttpRequestAdapter;
import com.pd.uber_shuffle.spotifyIdExtractor.AlbumIdExtractor;
import com.pd.uber_shuffle.spotifyIdExtractor.SpotifyIdExtractor;
import com.pd.uber_shuffle.spotifyIdExtractor.TrackIdExtractor;
import com.pd.uber_shuffle.spotifyIdExtractor.TrackIdFromAlbumExtractor;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

// TODO: This class depends on all of the Extractor classes. There's probably a way to do this better.
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

  @Override
  public void authorize(String state, String codeChallenge, String clientId) {
    StringBuilder result = new StringBuilder();

    result.append("https://accounts.spotify.com/authorize?");
    result.append("client_id=").append(clientId).append("&");
    result.append("response_type=code").append("&");
    result.append("redirect_uri=http://localhost:8080").append("&");
    result.append("state=").append(state).append("&");
    result.append("scope=playlist-modify-private%20playlist-modify-public%20user-library-read").append("&");
    result.append("code_challenge_method=S256").append("&");
    result.append("code_challenge=").append(codeChallenge);

    JSONObject response = httpRequestAdapter.makeGetRequest(result.toString());
    System.out.print(response.toString());
  }

  //TODO: Refactor this. Also, is turning the JSON Exception into a Runtime Exception the best
  // option here?
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
