package io.annot8.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import io.annot8.common.factories.ContentBuilderFactory;
import io.annot8.common.registries.ContentBuilderFactoryRegistry;
import io.annot8.common.stores.SaveCallback;
import io.annot8.core.data.Content;
import io.annot8.core.data.Content.Builder;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.properties.Properties;

public class TestContentBuilderFactoryRegistry implements ContentBuilderFactoryRegistry {

  @Override
  public <D, C extends Content<D>> Optional<ContentBuilderFactory<D, C>> get(
      Class<C> contentClass) {
    if (AbstractTestContent.class.isAssignableFrom(contentClass)) {
      try {
        return Optional.of(new TestContentBuilderFactory(contentClass));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return Optional.empty();
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
    private String id;
    private final TestProperties builderProperties = new TestProperties();

    public TestContentBuilder(C instance, SaveCallback<C, C> saver) {
      this.instance = instance;
      this.saver = saver;
    }

    @Override
    public Builder<C, D> withId(String id) {
      this.id = id;
      return this;
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
      if (opt.isPresent() && opt.get().equals(value))
        builderProperties.remove(key);

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
    public C save() throws IncompleteException {
      instance.setProperties(builderProperties);
      return saver.save(instance);
    }

  }
}