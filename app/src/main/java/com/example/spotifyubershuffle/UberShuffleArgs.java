package com.example.spotifyubershuffle;

public class UberShuffleArgs {
    private final String accessToken;
    private final String username;
    private final int playlistSize;

    public UberShuffleArgs(String accessToken, String username, int playlistSize) {
        this.accessToken = accessToken;
        this.username = username;
        this.playlistSize = playlistSize;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public int getPlaylistSize() {
        return playlistSize;
    }
}
