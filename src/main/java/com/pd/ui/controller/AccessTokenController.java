package com.pd.ui.controller;

import com.pd.uber_shuffle.http_adapter.HttpRequestAdapter;

public class AccessTokenController {
  HttpRequestAdapter observer;

  public void setNewAccessToken(String token) {
    observer.setAccessToken(token);
  }

  public void addTokenObserver(HttpRequestAdapter http) {
    observer = http;
  }
}
