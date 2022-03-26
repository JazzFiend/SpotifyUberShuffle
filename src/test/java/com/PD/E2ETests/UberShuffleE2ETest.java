package com.PD.E2ETests;

import com.PD.exceptions.ShuffleException;
import com.PD.model.SpotifyUberShuffle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class UberShuffleE2ETest {

  @Test
  @Disabled
  public void endToEndTest() throws ShuffleException {
    final String accessToken = "Bearer BQAXH5kFAHeXlxph_eox946rQaOYkFnlwO0wBJ-p5m-Mol4iH10pbS6kaFSe5Tc7KXCMK7Cw45UulzpsmzjGIHR9CiQRokRYYwxsg9FV_UfpPuvzJvDRqgG670rCOdTbeHEOvCa2kq_9gbyIoaMaRnykMB-Ey0DnSfajdpBuoc-zrlJ3VDVpQr5xsj6zp4y887xUzBBizMiXzF_TeyKRn9JRN74ao0rz66F3QEoOEKQNq30";
    final String USER_ID = "jazzFiend7";
    final int PLAYLIST_SIZE = 50;

    SpotifyUberShuffle.beginUberShuffle(accessToken, USER_ID, PLAYLIST_SIZE);
  }
}
