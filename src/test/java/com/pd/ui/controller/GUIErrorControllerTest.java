package com.pd.ui.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GUIErrorControllerTest {
  @Test
  public void ParsableNumber() {
    assertTrue(GUIErrorController.isParsableNumber("347"));
  }

  @Test
  public void NonParsableNumber() {
    assertFalse(GUIErrorController.isParsableNumber("Test"));
  }
}
