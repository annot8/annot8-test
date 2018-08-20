package io.annot8.test;

import io.annot8.common.implementations.factories.AnnotationBuilderFactory;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Annotation.Builder;
import io.annot8.core.data.Content;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.stores.AnnotationStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TestAnnotationStore implements AnnotationStore {

  private final Map<String, Annotation> annotations = new HashMap<>();
  private String contentName;
  private final AnnotationBuilderFactory<Annotation> annotationBuilderFactory;

  public TestAnnotationStore() {
    this(TestConstants.CONTENT_NAME);
  }

  public TestAnnotationStore(String contentName) {
    this(contentName, TestAnnotationBuilder.factory());
  }

  public TestAnnotationStore(String contentName,
      AnnotationBuilderFactory<Annotation> annotationBuilderFactory) {
    this.contentName = contentName;
    this.annotationBuilderFactory = annotationBuilderFactory;
  }
  public TestAnnotationStore(Content<?> content) {
    this(content.getName());
  }

  @Override
  public Builder getBuilder() {
    return annotationBuilderFactory.create(contentName, this, this::save);
  }

  public void setContentName(String contentName) {
    this.contentName = contentName;
  }

  public Annotation save(Builder annotationBuilder) throws IncompleteException {
    Annotation annotation = annotationBuilder.save();
    return save(annotation);
  }

  public Annotation save(Annotation annotation) {
    annotations.put(annotation.getId(), annotation);
    return annotation;
  }

  @Override
  public void delete(Annotation annotation) {
    annotations.remove(annotation.getId());
  }

  @Override
  public Stream<Annotation> getAll() {
    return annotations.values().stream();
  }

  @Override
  public Optional<Annotation> getById(String annotationId) {
    return Optional.ofNullable(annotations.get(annotationId));
  }
}
