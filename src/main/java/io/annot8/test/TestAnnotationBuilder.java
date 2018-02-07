package io.annot8.test;

import io.annot8.common.factories.AnnotationBuilderFactory;
import io.annot8.common.stores.SaveFromBuilder;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Annotation.Builder;
import io.annot8.core.bounds.Bounds;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.properties.Properties;
import java.util.UUID;

public class TestAnnotationBuilder implements Annotation.Builder {

  private final MutableProperties properties = new TestProperties();
  private final SaveFromBuilder<Annotation, Annotation> saver;
  private String contentName;
  private Bounds bounds;
  private String id;
  private String type;

  public TestAnnotationBuilder(String content, SaveFromBuilder<Annotation, Annotation> saver) {
    this.contentName = content;
    this.saver = saver;
  }

  public static AnnotationBuilderFactory<Annotation> factory() {
    return (content, store, saver) -> new TestAnnotationBuilder(content, saver);
  }

  @Override
  public Builder withBounds(Bounds bounds) {
    this.bounds = bounds;
    return this;
  }

  @Override
  public Annotation save() throws IncompleteException {
    String assignedId = this.id == null ? UUID.randomUUID().toString() : this.id;

    TestAnnotation annotation = new TestAnnotation();
    annotation.setBounds(bounds);
    annotation.setContentName(contentName);
    annotation.setId(assignedId);
    annotation.setType(type);
    TestProperties testProperties = new TestProperties();
    testProperties.add(properties.getAll());
    annotation.setProperties(testProperties);

    return this.saver.save(annotation);
  }

  @Override
  public Builder from(Annotation from) {
    contentName = from.getContentName();
    bounds = from.getBounds();
    id = from.getId();
    type = from.getType();
    properties.add(from.getProperties().getAll());
    return this;
  }

  @Override
  public Builder newId() {
    this.id = null;
    return this;
  }

  @Override
  public Builder withProperty(String key, Object value) {
    this.properties.set(key, value);
    return this;
  }

  @Override
  public Builder withProperties(Properties properties) {
    this.properties.add(properties.getAll());
    return this;
  }

  @Override
  public Builder withType(String type) {
    this.type = type;
    return this;
  }
}
