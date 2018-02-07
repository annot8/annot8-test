package io.annot8.test.content;

import io.annot8.test.AbstractTestContent;

public class TestStringContent extends AbstractTestContent<String> {

  public TestStringContent() {
    super(String.class);
    setData("Test data");
  }
}
