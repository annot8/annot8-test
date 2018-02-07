package io.annot8.test;

import io.annot8.common.factories.ContentBuilderFactory;
import io.annot8.common.registries.ContentBuilderFactoryRegistry;
import io.annot8.common.utils.StreamUtils;
import io.annot8.core.data.Content;
import io.annot8.core.data.Content.Builder;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.AlreadyExistsException;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.exceptions.UnsupportedContentException;
import io.annot8.core.properties.MutableProperties;
import io.annot8.core.stores.GroupStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TestItem implements Item {

  private MutableProperties properties;
  private GroupStore groups;
  private ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;
  private Map<String, Content<?>> content = new HashMap<>();

  public TestItem() {
    this(new TestGroupStore());
  }

  public TestItem(GroupStore groupStore) {
    this(groupStore, new TestContentBuilderFactoryRegistry());
  }

  public TestItem(GroupStore groupStore,
      ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.properties = new TestProperties();
    this.groups = groupStore;
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
  }

  @Override
  public Stream<String> listContents() {
    return content.keySet().stream();
  }

  @Override
  public Optional<Content<?>> getContent(String name) {
    return Optional.ofNullable(content.get(name));
  }

  @Override
  public Stream<Content<?>> getContents() {
    return content.values().stream();
  }

  @Override
  public <T extends Content<?>> Stream<T> getContents(Class<T> clazz) {
    return StreamUtils.cast(getContents(), clazz);
  }

  @Override
  public <C extends Content<D>, D> Builder<C, D> create(Class<C> clazz)
      throws UnsupportedContentException {
    Optional<ContentBuilderFactory<D, C>> optional = contentBuilderFactoryRegistry
        .get(clazz);

    if (!optional.isPresent()) {
      throw new UnsupportedContentException("No content builder factory " + clazz.getSimpleName());
    }

    ContentBuilderFactory<D, C> factory = optional.get();

    return factory.create(this, this::save);
  }

  public <C extends Content<D>, D> C save(Builder<C, D> builder) throws AlreadyExistsException {
    C c;
    try {
      c = builder.save();
    } catch (IncompleteException e) {
      throw new AssertionError(e.getMessage());
    }

    if (content.get(c.getName()) != null) {
      throw new AlreadyExistsException("Already exists " + c.getName());
    }

    return save(c);
  }

  public <D, C extends Content<D>> C save(C c) {
    content.put(c.getName(), c);
    return c;
  }

  @Override
  public void removeContent(String name) {
    content.remove(name);
  }

  @Override
  public GroupStore getGroups() {
    return groups;
  }

  @Override
  public MutableProperties getProperties() {
    return properties;
  }

  public void setGroups(GroupStore groups) {
    this.groups = groups;
  }

  public void setProperties(MutableProperties properties) {
    this.properties = properties;
  }

  public ContentBuilderFactoryRegistry getContentBuilderFactoryRegistry() {
    return contentBuilderFactoryRegistry;
  }

  public void setContentBuilderFactoryRegistry(
      ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
  }

  public Map<String, Content<?>> getContent() {
    return content;
  }

  public void setContent(Map<String, Content<?>> content) {
    this.content = content;
  }
}
