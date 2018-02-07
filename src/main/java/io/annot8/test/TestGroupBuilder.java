package io.annot8.test;

import io.annot8.common.factories.GroupBuilderFactory;
import io.annot8.common.stores.SaveFromBuilder;
import io.annot8.core.annotations.Annotation;
import io.annot8.core.annotations.Group;
import io.annot8.core.annotations.Group.Builder;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.Properties;
import io.annot8.core.references.AnnotationReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestGroupBuilder implements Group.Builder {

  private final SaveFromBuilder<Group, Group> saver;

  private final Map<AnnotationReference, String> annotations = new HashMap<>();
  private final TestProperties properties = new TestProperties();
  private String id = UUID.randomUUID().toString();
  private String type = TestConstants.GROUP_TYPE;


  public TestGroupBuilder(SaveFromBuilder<Group, Group> saver) {
    this.saver = saver;
  }

  public static GroupBuilderFactory<Group> factory() {
    return (item, store, saver) -> new TestGroupBuilder(saver);
  }

  @Override
  public Builder withAnnotation(String role, Annotation annotation) {
    annotations.put(new TestAnnotationReference(annotation), role);
    return this;
  }

  @Override
  public Group save() throws IncompleteException {
    TestGroup group = new TestGroup(id, type);
    group.setProperties(properties);
    group.setAnnotations(annotations);
    return saver.save(group);
  }

  @Override
  public Builder from(Group from) {
    id = from.getId();
    type = from.getType();
    properties.add(from.getProperties().getAll());
    from.getAnnotations().forEach(
        (key, value) -> value.forEach(a -> annotations.put(new TestAnnotationReference(a), key)));
    return this;
  }

  @Override
  public Builder newId() {
    id = null;
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
