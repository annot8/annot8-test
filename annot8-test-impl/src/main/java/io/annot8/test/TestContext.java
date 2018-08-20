package io.annot8.test;

import io.annot8.core.components.Resource;
import io.annot8.core.context.Context;
import io.annot8.core.settings.Settings;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TestContext implements Context {

  private  Settings settings = null;
  private Map<String, Resource> resources = Collections.emptyMap();

  public TestContext() {
    this(null, Collections.emptyMap());
  }

  public TestContext(Settings settings) {
    this(settings, Collections.emptyMap());
  }

  public TestContext(Map<String, Resource> resources) {
    this(null, resources);
  }

  public TestContext(Settings settings, Map<String, Resource> resources) {
    this.settings = settings;
    this.resources = resources;
  }

  @Override
  public Optional<Settings> getSettings() {
    return Optional.ofNullable(settings);
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
    return resources.entrySet()
          .stream()
          .filter(r -> clazz.isInstance(r))
          .map(clazz::cast);
  }
}
