package io.annot8.testing.testimpl;

import io.annot8.core.data.Item;
import io.annot8.core.data.ItemFactory;

public class TestItemFactory implements ItemFactory {

  @Override
  public Item create() {
    return new TestItem(this);
  }

  @Override
  public Item create(Item parent) {
    return new TestItem(this);
  }
}
