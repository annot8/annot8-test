package io.annot8.test;

import io.annot8.common.factories.ContentBuilderFactory;
import io.annot8.common.registries.ContentBuilderFactoryRegistry;
import io.annot8.common.stores.SaveFromBuilder;
import io.annot8.core.data.Content;
import io.annot8.core.data.Content.Builder;
import io.annot8.core.data.Item;
import io.annot8.core.data.Tags;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.Properties;
import java.util.Optional;

public class TestContentBuilderFactoryRegistry implements ContentBuilderFactoryRegistry {

  @Override
  public <D, C extends Content<D>> Optional<ContentBuilderFactory<D, C>> get(
      Class<C> contentClass) {
    if (AbstractTestContent.class.isAssignableFrom(contentClass)) {
      try {
        return Optional.of(new TestContentBuilderFactory(contentClass));
      } catch (Exception e) {
        // TODO: log
        e.printStackTrace();
      }
    }

    return Optional.empty();
  }

  public static class TestContentBuilderFactory<D, C extends AbstractTestContent<D>> implements
      ContentBuilderFactory<D, C> {

    private final C instance;
    private final Class<C> contentClass;


    public TestContentBuilderFactory(Class<C> contentClass) throws Exception {
      this.contentClass = contentClass;
      instance = contentClass.getConstructor().newInstance();

    }

    @Override
    public Content.Builder<C, D> create(Item item, SaveFromBuilder<C, C> saver) {
      return new TestContentBuilder(instance, saver);
    }


    @Override
    public Class getDataClass() {
      return instance.getDataClass();
    }

    @Override
    public Class<C> getContentClass() {
      return contentClass;
    }
  }

  public static class TestContentBuilder<D, C extends AbstractTestContent<D>> implements
      Content.Builder<C, D> {

    private final C instance;
    private final SaveFromBuilder<C, C> saver;
    private final TestProperties builderProperties = new TestProperties();
    private final TestTags tags = new TestTags();

    public TestContentBuilder(C instance, SaveFromBuilder<C, C> saver) {
      this.instance = instance;
      this.saver = saver;
    }

    @Override
    public Builder<C, D> withName(String name) {
      instance.setName(name);
      return this;
    }

    @Override
    public Builder<C, D> withData(D data) {
      instance.setData(data);
      return this;
    }

    @Override
    public Builder<C, D> from(C from) {
      withTags(from.getTags());
      return this;
    }

    @Override
    public Builder<C, D> withProperty(String key, Object value) {
      builderProperties.set(key, value);
      return this;
    }

    @Override
    public Builder<C, D> withProperties(Properties properties) {
      builderProperties.add(properties.getAll());
      return this;
    }

    @Override
    public C save() throws IncompleteException {
      instance.setProperties(builderProperties);
      instance.setTags(tags);
      return saver.save(instance);
    }

    @Override
    public Builder<C, D> withTag(String tag) {
      this.tags.add(tag);
      return this;
    }

    @Override
    public Builder<C, D> withTags(Tags tags) {
      tags.get().forEach(this.tags::add);
      return this;
    }
  }
}
