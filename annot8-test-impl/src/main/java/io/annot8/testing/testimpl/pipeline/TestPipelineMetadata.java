package io.annot8.testing.testimpl.pipeline;

import io.annot8.common.implementations.pipelines.Pipeline;
import io.annot8.common.implementations.pipelines.configuration.PipelineConfiguration;
import io.annot8.common.implementations.pipelines.management.PipelineMetadata;
import java.util.UUID;

public class TestPipelineMetadata implements PipelineMetadata {

  private String id;

  private PipelineConfiguration configuration;

  private final Pipeline pipeline;

  public TestPipelineMetadata() {
    this(null, null);
  }

  public TestPipelineMetadata(PipelineConfiguration configuration, Pipeline pipeline) {
    this.configuration = configuration;
    this.pipeline = pipeline;
    this.id = UUID.randomUUID().toString();
  }

  @Override
  public String getId() {
    return id;
  }


  @Override
  public PipelineConfiguration getPipelineConfiguration() {
    return configuration;
  }

  @Override
  public Pipeline getPipeline() {
    return pipeline;
  }
}
