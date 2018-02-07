package io.annot8.test;

import io.annot8.common.references.GroupReference;
import io.annot8.core.annotations.Group;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO: ...Before you use this you need to understand that group change, so this is not going to
 * provide you with the most upto date group. Nor will it work if your group is deleted from the
 * store.
 */
public class TestGroupReference implements GroupReference {

  private Group group;

  public TestGroupReference(Group group) {
    assert group != null;
    this.group = group;
  }

  @Override
  public Optional<Group> toGroup() {
    return Optional.of(group);
  }


  public void setGroup(Group group) {
    assert group != null;
    this.group = group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestGroupReference that = (TestGroupReference) o;

    return Objects.equals(group.getId(), that.group.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(group.getId());
  }
}
