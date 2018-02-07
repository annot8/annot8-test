package io.annot8.test;

import io.annot8.core.data.Tags;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class TestTags implements Tags {

  private final Set<String> tags = new HashSet<>();

  @Override
  public Stream<String> get() {
    return tags.stream();
  }

  public void add(String tag) {
    tags.add(tag);
  }

  public void remove(String tag) {
    tags.remove(tag);
  }

  public void clear() {
    tags.clear();
  }
}
