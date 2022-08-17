package com.PD.ui.view;

import com.PD.uberShuffle.SpotifyUberShuffle;
import com.PD.uberShuffle.SpotifyUberShuffleImpl;
import com.PD.ui.controller.UberShuffleController;

public class UIMain {
  public static void main(String[] args) {
    SpotifyUberShuffle shuffle = new SpotifyUberShuffleImpl();
    UberShuffleController controller = new UberShuffleController(shuffle);

    MainForm main = new MainForm(controller);
    main.startUi();
  }
}
