/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.core.data.Item;
import io.annot8.core.data.ItemFactory;

public class TestItemFactory implements ItemFactory {

  @Override
  public Item create() {
    return new TestItem();
  }

  @Override
  public Item create(Item parent) {
    return new TestItem(parent.getId());
  }
}
