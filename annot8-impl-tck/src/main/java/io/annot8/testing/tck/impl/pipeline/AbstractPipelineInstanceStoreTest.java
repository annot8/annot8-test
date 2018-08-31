//package io.annot8.testing.tck.impl.pipeline;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//import io.annot8.common.implementations.pipelines.PipelineStatus;
//import io.annot8.common.implementations.pipelines.configuration.ComponentConfiguration;
//import io.annot8.common.implementations.pipelines.configuration.PipelineConfiguration;
//import io.annot8.common.implementations.pipelines.management.PipelineInstanceStore;
//import io.annot8.common.implementations.pipelines.management.PipelineMetadata;
//import io.annot8.core.exceptions.IncompleteException;
//import java.util.Collection;
//import java.util.Collections;
//import org.junit.jupiter.api.Test;
//
//public abstract class AbstractPipelineInstanceStoreTest {
//
//  private static final String PROCESSOR_CLASS = "testProcessorClassName";
//  private static final String SOURCE_CLASS = "testSourceClassName";
//
//  protected abstract PipelineInstanceStore getPipelineService();
//
//  @Test
//  public void testCreatePipeline() throws IncompleteException {
//    PipelineInstance metadata = getPipelineService().createPipeline(getDefaultTestMetadata());
//    assertNotNull(metadata);
//    assertNotNull(metadata.getId());
//    assertEquals(PipelineStatus.INITIALISING, metadata.getStatus());
//
//    testDefaultTestConfiguration(metadata.getPipelineConfiguration());
//  }
//
//  @Test
//  public void testGetPipelineMetadata() throws IncompleteException {
//    String id = getPipelineService().createPipeline(getDefaultTestMetadata()).getId();
//    PipelineMetadata pipelineMetadata = getPipelineService().getPipeline(id);
//    testDefaultTestConfiguration(pipelineMetadata.getPipelineConfiguration());
//  }
//
//  @Test
//  public void testRunPipeline() throws IncompleteException {
//    PipelineInstanceStore pipelineService = getPipelineService();
//    PipelineInstance metadata = pipelineService.createPipeline(getDefaultTestMetadata());
//    metadata = pipelineService.runPipeline(metadata.getId());
//    assertEquals(PipelineStatus.COMPLETED, metadata.getStatus());
//  }
//
//
//  private void testDefaultTestConfiguration(PipelineConfiguration pipelineConfiguration) {
//    Collection<ComponentConfiguration> processors = pipelineConfiguration.getProcessors();
//    Collection<ComponentConfiguration> sources = pipelineConfiguration.getSources();
//
//    assertEquals(1, processors.size());
//    assertEquals(1, sources.size());
//
//    processors.forEach(p -> {
//      assertEquals(PROCESSOR_CLASS, p.getClassName());
//      assertNull(p.getSettings());
//    });
//
//    sources.forEach(s -> {
//      assertEquals(SOURCE_CLASS, s.getClassName());
//      assertNull(s.getSettings());
//    });
//  }
//
//  private PipelineMetadata getDefaultTestMetadata() {
//    return new TestPipelineMetadata(getDefaultTestConfiguration());
//  }
//
//  private PipelineConfiguration getDefaultTestConfiguration() {
//    TestPipelineComponentConfiguration processorConfig =
//        new TestPipelineComponentConfiguration(PROCESSOR_CLASS);
//    TestPipelineComponentConfiguration sourceConfig =
//        new TestPipelineComponentConfiguration(SOURCE_CLASS);
//    return new TestPipelineConfiguration(Collections.singletonList(processorConfig),
//        Collections.singletonList(sourceConfig));
//  }
//
//}
