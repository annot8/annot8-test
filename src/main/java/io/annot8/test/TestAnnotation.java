package io.annot8.test;

import io.annot8.core.annotations.Annotation;
import io.annot8.core.bounds.Bounds;
import io.annot8.core.properties.ImmutableProperties;
import java.util.Objects;
import java.util.UUID;

/**
 * An annotation for use (only) in unit tests.
 *
 * DO NOT USE THIS OUTSIDE A UNIT TEST.
 *
 * This does not have the necessary correctness of behaviour, for example is it mutable.
 */
public class TestAnnotation implements Annotation {

  private Bounds bounds;
  private String content;
  private String id;
  private ImmutableProperties properties;
  private String type;

  public TestAnnotation() {
    this(UUID.randomUUID().toString(), TestConstants.TEST_CONTENT);
  }

  public TestAnnotation(String id, String content) {
    this(id, content, TestConstants.ANNOTATION_TYPE);
  }

  public TestAnnotation(String content, String id, String type) {
    this(id, content, type, new TestBounds());
  }

  public TestAnnotation(String content, String id, String type, Bounds bounds) {
    this.id = id;
    this.content = content;
    this.type = type;
    this.bounds = bounds;
    this.properties = new TestProperties();
  }

  @Override
  public Bounds getBounds() {
    return bounds;
  }

  public void setBounds(Bounds bounds) {
    this.bounds = bounds;
  }

  @Override
  public String getContentName() {
    return content;
  }

  public void setContentName(String content) {
    this.content = content;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  public void setProperties(ImmutableProperties properties) {
    this.properties = properties;
  }

  @Override
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestAnnotation that = (TestAnnotation) o;
    return Objects.equals(bounds, that.bounds) &&
        Objects.equals(content, that.content) &&
        Objects.equals(id, that.id) &&
        Objects.equals(properties, that.properties) &&
        Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bounds, content, id, properties, type);
  }
}
