package com.PD.ui.controller;

public class GUIErrorController {
  public static boolean isParsableNumber(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch(NumberFormatException ex) {
      return false;
    }
  }
}
