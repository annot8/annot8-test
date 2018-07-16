package io.annot8.test.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestStringContentTest {

  @Test
  public void testAbstractTestContent() {
    TestStringContent stringContent = new TestStringContent();
    assertEquals("Test data", stringContent.getData());
  }

}
