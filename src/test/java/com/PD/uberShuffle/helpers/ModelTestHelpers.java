package com.PD.uberShuffle.helpers;

import java.util.Collection;
import java.util.HashSet;

public class ModelTestHelpers {
  public static Collection<String> createDummyLibrary() {
    Collection<String> dummyLibrary = new HashSet<>();
    dummyLibrary.add("Track1");
    dummyLibrary.add("Track2");
    dummyLibrary.add("Track3");
    dummyLibrary.add("TrackA");
    dummyLibrary.add("TrackB");
    dummyLibrary.add("TrackC");

    return dummyLibrary;
  }
}
