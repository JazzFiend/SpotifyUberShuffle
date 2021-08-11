package com.PD.spotifyIdExtractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumIdExtractor extends SpotifyIdExtractor {
    @Override
    public List<String> extractIDs(JSONObject response) throws JSONException {
        List<String> albumIdList = new ArrayList<>();
        JSONArray itemArray = response.getJSONArray("items");
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            JSONObject album = item.getJSONObject("album");
            String id = album.getString("id");
            albumIdList.add(id);
        }
        return albumIdList;
    }
}
