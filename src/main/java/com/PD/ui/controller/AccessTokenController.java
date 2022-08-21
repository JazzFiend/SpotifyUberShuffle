package com.PD.ui.controller;

import com.PD.uberShuffle.httpAdapter.HttpRequestAdapter;

public class AccessTokenController {
  HttpRequestAdapter observer;

  public void setNewAccessToken(String token) {
    observer.setAccessToken(token);
  }

  public void addTokenObserver(HttpRequestAdapter http) {
    observer = http;
  }
}
