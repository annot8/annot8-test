package io.annot8.testing.testimpl;

import io.annot8.common.implementations.factories.GroupBuilderFactory;
import io.annot8.core.annotations.Group;
import io.annot8.core.annotations.Group.Builder;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.stores.GroupStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TestGroupStore implements GroupStore {

  private final Map<String, Group> groups = new HashMap<>();
  private final GroupBuilderFactory<Group> groupBuilderFactory;
  private Item item;

  public TestGroupStore() {
    this(null);
  }

  public TestGroupStore(Item item, GroupBuilderFactory<Group> groupBuilderFactory) {
    this.item = item;
    this.groupBuilderFactory = groupBuilderFactory;
  }

  public TestGroupStore(Item item) {
    this(item, TestGroupBuilder.factory());
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Item getItem() {
    return item;
  }

  @Override
  public Builder getBuilder() {
    return groupBuilderFactory.create(item, this, this::save);
  }

  public Group save(Builder groupBuilder) throws IncompleteException {
    Group group = groupBuilder.save();
    return save(group);
  }

  public Group save(Group group) {
    groups.put(group.getId(), group);
    return group;
  }

  @Override
  public void delete(Group group) {
    groups.remove(group.getId());
  }

  @Override
  public Stream<Group> getAll() {
    return groups.values().stream();
  }

  @Override
  public Optional<Group> getById(String groupId) {
    return Optional.ofNullable(groups.get(groupId));
  }
}
