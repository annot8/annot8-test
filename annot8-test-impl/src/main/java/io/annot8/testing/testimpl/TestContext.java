/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import io.annot8.core.components.Resource;
import io.annot8.core.context.Context;
import io.annot8.core.data.ItemFactory;
import io.annot8.core.settings.Settings;

public class TestContext implements Context {

  private final ItemFactory itemFactory;
  private final Settings settings;
  private final Map<String, Resource> resources;

  public TestContext() {
    this(new TestItemFactory());
  }

  public TestContext(ItemFactory itemFactory) {
    this(itemFactory, Collections.emptyMap());
  }

  public TestContext(Settings settings) {
    this(new TestItemFactory(), settings, Collections.emptyMap());
  }

  public TestContext(Map<String, Resource> resources) {
    this(new TestItemFactory(), null, resources);
  }

  public TestContext(ItemFactory itemFactory, Settings settings) {
    this(itemFactory, settings, Collections.emptyMap());
  }

  public TestContext(ItemFactory itemFactory, Map<String, Resource> resources) {
    this(itemFactory, null, resources);
  }

  public TestContext(Settings settings, Map<String, Resource> resources) {
    this(new TestItemFactory(), settings, resources);
  }

  public TestContext(ItemFactory itemFactory, Settings settings, Map<String, Resource> resources) {
    this.itemFactory = itemFactory;
    this.settings = settings;
    this.resources = resources == null ? Collections.emptyMap() : resources;
  }

  @Override
  public Stream<Settings> getSettings() {
    return Stream.ofNullable(settings);
  }

  @Override
  public <T extends Resource> Optional<T> getResource(String key, Class<T> clazz) {
    return Optional.empty();
  }

  @Override
  public Stream<String> getResourceKeys() {
    return resources.keySet().stream();
  }

  @Override
  public <T extends Resource> Stream<T> getResources(Class<T> clazz) {
    return resources.entrySet().stream().filter(clazz::isInstance).map(clazz::cast);
  }

  @Override
  public ItemFactory getItemFactory() {
    return itemFactory;
  }
}
