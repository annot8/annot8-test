package io.annot8.testing.testimpl.content;

import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.content.AbstractContentBuilder;
import io.annot8.common.implementations.content.AbstractContentBuilderFactory;
import io.annot8.common.implementations.stores.AnnotationStoreFactory;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;
import io.annot8.testing.testimpl.AbstractTestContent;
import io.annot8.testing.testimpl.TestAnnotationStoreFactory;
import java.util.function.Supplier;

public class TestStringContent extends AbstractTestContent<String> implements Text {

  public TestStringContent() {
    super(String.class);
    setData("Test data");
  }

  public TestStringContent(String id, String name, ImmutableProperties properties, Supplier<String> data) {
    super(String.class, id, name, properties, data);
  }

  public TestStringContent(AnnotationStore annotations, String id, String name, ImmutableProperties properties, Supplier<String> data) {
    super(String.class, c -> annotations, id, name, properties, data);
  }

  public TestStringContent(AnnotationStoreFactory annotationStore, String id, String name, ImmutableProperties properties, Supplier<String> data) {
    super(String.class, annotationStore, id, name, properties, data);
  }

  @Override
  public Class<? extends Content<String>> getContentClass() {
    return Text.class;
  }


  public static class Builder extends AbstractContentBuilder<String, TestStringContent> {

    public Builder(
        AnnotationStoreFactory annotationStoreFactory,
        SaveCallback<TestStringContent, TestStringContent> saver) {
      super(annotationStoreFactory, saver);
    }

    @Override
    protected TestStringContent create(String id, String name,
        ImmutableProperties properties, Supplier<String> data) throws IncompleteException {
      return new TestStringContent(getAnnotationStoreFactory(), id, name, properties, data);
    }

  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<String, TestStringContent> {

    public BuilderFactory() {
      this(TestAnnotationStoreFactory.getInstance());
    }

    public BuilderFactory(AnnotationStoreFactory annotationStoreFactory) {
      super(String.class, TestStringContent.class, annotationStoreFactory);
    }

    @Override
    public Builder create(Item item,
        SaveCallback<TestStringContent, TestStringContent> saver) {
      return new Builder(getAnnotationStoreFactory(), saver);
    }
  }
}
