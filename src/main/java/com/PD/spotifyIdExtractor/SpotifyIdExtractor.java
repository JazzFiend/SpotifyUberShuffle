package com.PD.spotifyIdExtractor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class SpotifyIdExtractor {
    public abstract List<String> extractIDs(JSONObject response) throws JSONException;
}
