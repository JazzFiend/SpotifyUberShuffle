package com.PD.uberShuffle.idExtractorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.PD.uberShuffle.spotifyIdExtractor.AlbumIdExtractor;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class TrackIdFromAlbumExtractorTest {
    @Test
    public void extractId() throws JSONException {
        String albumResponse = "{\"items\": [{\"album\": {\"id\": \"123\"}},{\"album\": {\"id\": \"abc\"}},{\"album\": {\"id\": \"987\"}}]}";
        JSONObject albumResponseJson = new JSONObject(albumResponse);
        List<String> extractedIds = new ArrayList<>();
        extractedIds.add("123");
        extractedIds.add("abc");
        extractedIds.add("987");

        AlbumIdExtractor extractor = new AlbumIdExtractor();
        List<String> underTest = extractor.extractIDs(albumResponseJson);

        assertEquals(underTest, extractedIds);
    }
}
