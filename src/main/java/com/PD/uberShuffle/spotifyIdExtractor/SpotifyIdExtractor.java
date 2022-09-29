package com.PD.uberShuffle.spotifyIdExtractor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract class SpotifyIdExtractor {
    // TODO: There's a lot of copy-paste between all of the extractors. Can I consolidate?
    public abstract List<String> extractIDs(JSONObject response) throws JSONException;
}
