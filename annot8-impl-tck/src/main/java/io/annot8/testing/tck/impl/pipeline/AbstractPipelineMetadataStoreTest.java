package io.annot8.testing.tck.impl.pipeline;

import io.annot8.common.implementations.pipelines.configuration.PipelineConfiguration;
import io.annot8.common.implementations.pipelines.management.PipelineMetadata;
import io.annot8.common.implementations.pipelines.management.PipelineMetadataStore;
import io.annot8.testing.testimpl.pipeline.TestPipelineComponentConfiguration;
import io.annot8.testing.testimpl.pipeline.TestPipelineConfiguration;
import io.annot8.testing.testimpl.pipeline.TestPipelineMetadata;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractPipelineMetadataStoreTest {

  protected abstract PipelineMetadataStore getPipelineMetadataStoreService();

  @Test
  public void testSavePipelineMetadata() {
    PipelineMetadata pipelineMetadata = createPipelineMetadata();
    PipelineMetadataStore service = getPipelineMetadataStoreService();
    service.saveMetadata(pipelineMetadata);

    Optional<PipelineMetadata> pipeline = service.getMetadata(pipelineMetadata.getId());

    Assertions.assertTrue(pipeline.isPresent());
    Assertions.assertEquals(pipelineMetadata.getId(), pipeline.get().getId());
  }

  @Test
  public void testGetPiplineMetadata() {
    Optional<PipelineMetadata> pipeline =
        getPipelineMetadataStoreService().getMetadata("NO_METADATA_ID");

    Assertions.assertFalse(pipeline.isPresent());
  }

//  @Test
//  public void testCreatePipelineMetadata() {
//    PipelineConfiguration config = createPipelineConfiguration();
//    PipelineMetadataStore service = getPipelineMetadataStoreService();
//
//    PipelineMetadata pipelineMetadata = service.createMetadata(config);
//
//    Assertions.assertNotNull(pipelineMetadata);
//
//    String id = pipelineMetadata.getId();
//    Optional<PipelineMetadata> retrieved = service.getMetadata(id);
//    Assertions.assertTrue(retrieved.isPresent());
//    Assertions.assertEquals(id, retrieved.get().getId());
//  }

  private PipelineMetadata createPipelineMetadata() {
    return new TestPipelineMetadata(createPipelineConfiguration(), null);
  }

  private PipelineConfiguration createPipelineConfiguration() {
    return new TestPipelineConfiguration(
        Collections.singleton(new TestPipelineComponentConfiguration("com.example.b", Collections.emptySet())),
        Collections.singleton(new TestPipelineComponentConfiguration("com.example.a", Collections.emptySet())),
        null);
  }

}
