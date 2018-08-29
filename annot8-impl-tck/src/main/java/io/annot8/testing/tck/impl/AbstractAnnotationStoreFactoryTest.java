package io.annot8.testing.tck.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.annot8.common.implementations.stores.AnnotationStoreFactory;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.testing.testimpl.TestProperties;
import io.annot8.testing.testimpl.content.TestStringContent;
import org.junit.jupiter.api.Test;

public abstract class AbstractAnnotationStoreFactoryTest {

  protected abstract AnnotationStoreFactory getAnnotationStoreFactory();

  @Test
  public void testGetAnnotationStore() {
    TestStringContent content = new TestStringContent("testContentId", "name", new TestProperties(), () -> "test");
    AnnotationStore annotationStore = getAnnotationStoreFactory().create(content);
    assertNotNull(annotationStore);
  }

}