package io.annot8.testing.testimpl.serialisation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.annot8.common.serialisation.jackson.AbstractAnnot8Deserializer;
import io.annot8.testing.testimpl.TestBounds;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBoundsDeserializer extends AbstractAnnot8Deserializer<TestBounds> {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestBoundsDeserializer.class);

  public static final String KEY = "id";

  public TestBoundsDeserializer() {
    super(TestBounds.class);
  }

  @Override
  public TestBounds deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode n = p.getCodec().readTree(p);
    String id = n.get(KEY).asText();
    return new TestBounds(id);
  }


}
