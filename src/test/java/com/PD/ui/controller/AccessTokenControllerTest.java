package com.PD.ui.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.PD.mocks.HttpRequestAdapterMock;
import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;
import org.junit.jupiter.api.Test;

class AccessTokenControllerTest {
  @Test
  public void modifyToken() {
    AccessTokenController underTest = new AccessTokenController();
    HttpRequestAdapter http = new HttpRequestAdapterMock();
    underTest.addTokenObserver(http);
    underTest.setNewAccessToken("New Token");
    assertEquals("New Token", ((HttpRequestAdapterMock)http).getToken());
  }
}
