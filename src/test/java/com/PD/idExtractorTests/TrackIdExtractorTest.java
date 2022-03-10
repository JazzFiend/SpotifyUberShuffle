package com.PD.idExtractorTests;

import com.PD.model.spotifyIdExtractor.TrackIdExtractor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrackIdExtractorTest {
    @Test
    public void extractId() throws JSONException {
        String trackResponse = "{\"items\": [{\"track\": {\"id\": \"123\"}},{\"track\": {\"id\": \"abc\"}},{\"track\": {\"id\": \"987\"}}]}";
        JSONObject trackResponseJson = new JSONObject(trackResponse);
        List<String> extractedIds = new ArrayList<>();
        extractedIds.add("123");
        extractedIds.add("abc");
        extractedIds.add("987");

        TrackIdExtractor extractor = new TrackIdExtractor();
        List<String> underTest = extractor.extractIDs(trackResponseJson);

        assertEquals(underTest, extractedIds);
    }
}
