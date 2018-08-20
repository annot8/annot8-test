package io.annot8.test.content;

import io.annot8.common.content.Text;
import io.annot8.core.data.Content;
import io.annot8.test.AbstractTestContent;

public class TestStringContent extends AbstractTestContent<String> {

  public TestStringContent() {
    super(String.class);
    setData("Test data");
  }

  @Override
  public Class<? extends Content<String>> getContentClass() {
    return Text.class;
  }
}
