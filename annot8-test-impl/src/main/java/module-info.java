open module io.annot8.test {
  requires transitive io.annot8.core;
  requires io.annot8.common.implementations;
  requires io.annot8.common.utils;
  requires io.annot8.common.data;

  exports io.annot8.test;
  exports io.annot8.test.content;
}
