package com.PD.idExtractorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.PD.model.spotifyIdExtractor.TrackIdFromAlbumExtractor;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

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
