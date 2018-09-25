open module io.annot8.testing.testimpl {
  requires transitive io.annot8.core;
  requires io.annot8.common.implementations;
  requires io.annot8.common.utils;
  requires io.annot8.common.data;
  requires slf4j.api;
  requires io.annot8.common.jackson.serialisation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;

  exports io.annot8.testing.testimpl.content;
  exports io.annot8.testing.testimpl;
  exports io.annot8.testing.testimpl.serialisation;
  exports io.annot8.testing.testimpl.pipeline;
}
