package io.annot8.test;

import java.util.UUID;
import io.annot8.core.data.Content;
import io.annot8.core.properties.ImmutableProperties;
import io.annot8.core.stores.AnnotationStore;

public abstract class AbstractTestContent<D> implements Content<D> {

  private final String id;
  private final Class<D> dataClass;
  private String name;
  private ImmutableProperties properties;
  private AnnotationStore annotations;

  private D data;

  public AbstractTestContent(Class<D> dataClass) {
    this(dataClass, TestConstants.CONTENT_NAME);
  }

  public AbstractTestContent(Class<D> dataClass, String name) {
    this.id = UUID.randomUUID().toString();
    this.dataClass = dataClass;
    this.name = name;
    this.annotations = new TestAnnotationStore();
    this.properties = new TestProperties();
  }

  @Override
  public String getId() {
    return id;
  }

  public Class<D> getDataClass() {
    return dataClass;
  }

  @Override
  public D getData() {
    return data;
  }

  public void setData(D data) {
    this.data = data;
  }

  @Override
  public AnnotationStore getAnnotations() {
    return annotations;
  }

  public void setAnnotations(AnnotationStore annotations) {
    this.annotations = annotations;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  public void setProperties(ImmutableProperties properties) {
    this.properties = properties;
  }

}