package com.pd.uber_shuffle.spotifyPlaylistCreator;

import com.pd.exceptions.ShuffleException;
import com.pd.uber_shuffle.spotifyApiHelper.SpotifyApiHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SpotifyPlaylistCreatorImpl implements SpotifyPlaylistCreator {
  private final SpotifyApiHelper spotifyAPIHelper;

  public SpotifyPlaylistCreatorImpl(SpotifyApiHelper spotifyAPIHelper) {
    this.spotifyAPIHelper = spotifyAPIHelper;
  }

  @Override
  public String createShufflePlaylist(String userId, int trackCount, Collection<String> trackLibrary) throws ShuffleException {
    List<String> shuffledSongIds = shuffle(trackCount, trackLibrary);
    String playlistId = spotifyAPIHelper.createPlaylist("UberShuffle", "UberShuffle", false, userId);
    spotifyAPIHelper.addToPlaylist(playlistId, shuffledSongIds);
    return playlistId;
  }

  private List<String> shuffle(int trackCount, Collection<String> trackLibrary) throws ShuffleException {
    if (trackLibrary.isEmpty()) { throw new ShuffleException("The library is empty"); }

    Random rng = new Random();
    final int librarySize = trackLibrary.size();
    List<String> trackLibraryList = new ArrayList<>(trackLibrary);
    List<String> shufflePlaylist = new ArrayList<>();

    while (moreTracksNeeded(trackCount, trackLibraryList, shufflePlaylist)) {
      int trackNumber = rng.nextInt(librarySize);
      addTrackWithoutDuplicates(shufflePlaylist, trackLibraryList.get(trackNumber));
    }
    return shufflePlaylist;
  }

  private void addTrackWithoutDuplicates(List<String> shufflePlaylist, String track) {
    if (!shufflePlaylist.contains(track)) {
      shufflePlaylist.add(track);
    }
  }

  private boolean moreTracksNeeded(int trackCount, List<String> trackLibraryList, List<String> shufflePlaylist) {
    return shufflePlaylist.size() < trackCount && shufflePlaylist.size() < trackLibraryList.size();
  }
}
