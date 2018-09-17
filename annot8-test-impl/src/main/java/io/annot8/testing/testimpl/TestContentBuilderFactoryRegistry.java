package io.annot8.testing.testimpl;

import io.annot8.common.data.content.FileContent;
import io.annot8.common.data.content.Text;
import io.annot8.common.implementations.factories.ContentBuilderFactory;
import io.annot8.common.implementations.registries.SimpleContentBuilderFactoryRegistry;
import io.annot8.common.implementations.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Content.Builder;
import io.annot8.core.data.Item;
import io.annot8.core.properties.Properties;
import io.annot8.testing.testimpl.content.TestFileContent;
import io.annot8.testing.testimpl.content.TestStringContent;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContentBuilderFactoryRegistry extends SimpleContentBuilderFactoryRegistry {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestContentBuilderFactoryRegistry.class);


  public TestContentBuilderFactoryRegistry() {
    this(true);
  }

  public TestContentBuilderFactoryRegistry(boolean includeDefaultContentBuilders) {

    if(includeDefaultContentBuilders) {
      register(Text.class, new TestStringContent.BuilderFactory());
      register(FileContent.class, new TestFileContent.BuilderFactory());
    }
  }
  @Override
  public <D, C extends Content<D>> Optional<ContentBuilderFactory<D, C>> get(
      Class<C> contentClass) {
    // if you specifically ask for testcontent types we get create them (without needing registration)
    if (AbstractTestContent.class.isAssignableFrom(contentClass)) {
      try {
        return Optional.of(new TestContentBuilderFactory(contentClass));
      } catch (Exception e) {
        LOGGER.error("Unable to create test content", e);
      }
    }


    // Otherwise fallback to standard registry
    return super.get(contentClass);
  }

  public static class TestContentBuilderFactory<D, C extends AbstractTestContent<D>>
      implements ContentBuilderFactory<D, C> {

    private final C instance;
    private final Class<C> contentClass;


    public TestContentBuilderFactory(Class<C> contentClass) throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException, InstantiationException {
      this.contentClass = contentClass;
      instance = contentClass.getConstructor().newInstance();

    }

    @Override
    public Content.Builder<C, D> create(Item item, SaveCallback<C, C> saver) {
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

  public static class TestContentBuilder<D, C extends AbstractTestContent<D>>
      implements Content.Builder<C, D> {

    private final C instance;
    private final SaveCallback<C, C> saver;
    private final TestProperties builderProperties = new TestProperties();

    public TestContentBuilder(C instance, SaveCallback<C, C> saver) {
      this.instance = instance;
      this.saver = saver;
    }

    @Override
    public Builder<C, D> withId(String id) {
      this.instance.setId(id);
      return this;
    }

    @Override
    public Builder<C, D> withName(String name) {
      instance.setName(name);
      return this;
    }

    @Override
    public Builder<C, D> withData(Supplier<D> data) {
      instance.setData(data);
      return this;
    }

    @Override
    public Builder<C, D> from(C from) {
      withProperties(from.getProperties());
      return this;
    }

    @Override
    public Builder<C, D> withProperty(String key, Object value) {
      builderProperties.set(key, value);
      return this;
    }

    @Override
    public Builder<C, D> withoutProperty(String key, Object value) {
      Optional<Object> opt = builderProperties.get(key);
      if (opt.isPresent() && opt.get().equals(value)) {
        builderProperties.remove(key);
      }

      return this;
    }

    @Override
    public Builder<C, D> withoutProperty(String key) {
      builderProperties.remove(key);

      return this;
    }

    @Override
    public Builder<C, D> withProperties(Properties properties) {
      builderProperties.add(properties.getAll());
      return this;
    }

    @Override
    public C save() {
      instance.setProperties(builderProperties);
      return saver.save(instance);
    }

  }
}
