package io.annot8.testing.testimpl;

import io.annot8.core.bounds.Bounds;
import io.annot8.core.data.Content;
import java.util.Optional;

public class TestBounds implements Bounds {

  // TODO: Add mocked responses for this perhaps (eg map of requriedClass to value for getData)

  @Override
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {
    // Can't return anything
    return Optional.empty();
  }

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {
    // true for everything
    return true;
  }
}
