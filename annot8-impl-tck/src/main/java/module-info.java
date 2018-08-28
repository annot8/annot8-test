module io.annot8.testing.tcl.impl {
  requires transitive io.annot8.testing.testimpl;
  requires io.annot8.common.implementations;

  requires org.junit.jupiter.api;
  requires org.mockito;

  exports io.annot8.testing.tck.impl;

}