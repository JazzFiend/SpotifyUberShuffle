package com.PD.idExtractorTests;

import com.PD.model.spotifyIdExtractor.TrackIdFromAlbumExtractor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlbumIdExtractorTest {
    @Test
    public void extractId() throws JSONException {
        String trackResponse = "{\"items\": [{\"id\": \"123\"},{\"id\": \"abc\"},{\"id\": \"987\"}]}";
        JSONObject trackResponseJson = new JSONObject(trackResponse);
        List<String> extractedIds = new ArrayList<>();
        extractedIds.add("123");
        extractedIds.add("abc");
        extractedIds.add("987");

        TrackIdFromAlbumExtractor extractor = new TrackIdFromAlbumExtractor();
        List<String> underTest = extractor.extractIDs(trackResponseJson);

        assertEquals(underTest, extractedIds);
    }
}
