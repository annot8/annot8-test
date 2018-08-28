package io.annot8.test;

import io.annot8.core.settings.Settings;

public class TestSettings implements Settings {

  private boolean valid;

  public TestSettings() {
    this(true);
  }


  public TestSettings(boolean valid) {
    this.valid = valid;
  }

  @Override
  public boolean validate() {
    return valid;
  }
}
