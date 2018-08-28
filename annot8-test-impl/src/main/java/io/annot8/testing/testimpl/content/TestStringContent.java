package io.annot8.testing.testimpl.content;

import io.annot8.common.data.content.Text;
import io.annot8.core.data.Content;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.testing.testimpl.AbstractTestContent;

public class TestStringContent extends AbstractTestContent<String> implements Text {

  public TestStringContent() {
    super(String.class);
    setData("Test data");
  }

  public TestStringContent(String id, String name, ImmutableProperties properties, String data) {
    super(String.class, id, name, properties, data);

  }

  @Override
  public Class<? extends Content<String>> getContentClass() {
    return Text.class;
  }
}
