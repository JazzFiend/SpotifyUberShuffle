package com.PD.uberShuffle.idExtractorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.PD.uberShuffle.spotifyIdExtractor.TrackIdExtractor;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

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
