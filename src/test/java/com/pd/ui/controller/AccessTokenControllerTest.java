package com.pd.ui.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.pd.mocks.HttpRequestAdapterMock;
import com.pd.uber_shuffle.http_adapter.HttpRequestAdapter;
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
