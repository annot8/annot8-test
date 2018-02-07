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

  private final MutableProperties properties;
  private final GroupStore groupStore;
  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;
  private final Map<String, Content<?>> content = new HashMap<>();

  public TestItem() {
    this(new TestGroupStore());
  }

  public TestItem(GroupStore groupStore) {
    this(groupStore, new TestContentBuilderFactoryRegistry());
  }

  public TestItem(GroupStore groupStore,
      ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.properties = new TestProperties();
    this.groupStore = groupStore;
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

  private <D, C extends Content<D>> C save(C c) {
    content.put(c.getName(), c);
    return c;
  }

  @Override
  public void removeContent(String name) {
    content.remove(name);
  }

  @Override
  public GroupStore getGroups() {
    return groupStore;
  }

  @Override
  public MutableProperties getProperties() {
    return properties;
  }

}
