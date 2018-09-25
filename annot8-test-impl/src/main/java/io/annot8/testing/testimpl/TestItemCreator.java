package io.annot8.testing.testimpl;

import io.annot8.common.implementations.factories.ItemCreator;
import io.annot8.core.data.Item;
import io.annot8.core.data.ItemFactory;

public class TestItemCreator implements ItemCreator {

  @Override
  public Item create(ItemFactory factory) {
    return new TestItem(factory);
  }

  @Override
  public Item create(ItemFactory factory, Item parent) {
    return new TestItem(factory, parent.getId());
  }

}
