package com.PD.spotifyIdExtractor;

import com.PD.spotifyIdExtractor.SpotifyIdExtractor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackIdFromAlbumExtractor extends SpotifyIdExtractor {
    @Override
    public List<String> extractIDs(JSONObject response) throws JSONException {
        List<String> trackIdList = new ArrayList<>();
        JSONArray itemArray = response.getJSONArray("items");
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            String id = item.getString("id");
            trackIdList.add(id);
        }
        return trackIdList;
    }
}
