package io.annot8.test;

import io.annot8.common.references.AnnotationReference;
import io.annot8.core.annotations.Annotation;
import java.util.Objects;
import java.util.Optional;

public class TestAnnotationReference implements AnnotationReference {

  private Annotation annotation;

  public TestAnnotationReference(Annotation annotation) {
    assert annotation != null;
    this.annotation = annotation;
  }

  @Override
  public Optional<Annotation> toAnnotation() {
    return Optional.of(annotation);
  }

  public void setAnnotation(Annotation annotation) {
    assert annotation != null;
    this.annotation = annotation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestAnnotationReference that = (TestAnnotationReference) o;
    return Objects.equals(annotation.getId(), that.annotation.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotation.getId());
  }
}
