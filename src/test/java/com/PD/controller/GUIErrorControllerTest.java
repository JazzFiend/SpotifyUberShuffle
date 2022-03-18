package com.PD.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
