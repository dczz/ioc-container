package context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

public class Application {

  Map<String, Class<?>> classContainer;

  static final Map<String, Object> beanContainer = new HashMap<>();

  public Application (Map<String,Class<?>> packageClassMap) {
    classContainer = packageClassMap;
    classContainer.forEach((s, clzz) -> {
      try {
        everyone(s, clzz);
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    });
  }

  private boolean needInject (Annotation annotation) {
    return annotation instanceof Resource;
  }

  private void everyone (String s, Class<?> clzz) throws InstantiationException, IllegalAccessException {
    Object instance = beanContainer.get(s);
    if(Objects.nonNull(instance)){
      return;
    }
    instance = clzz.newInstance();
    beanContainer.put(s, instance);
    for (Field instanceField : clzz.getDeclaredFields()) {
      for (Annotation annotation : instanceField.getAnnotations()) {
        if (!needInject(annotation)) {
          continue;
        }
        Object resource = beanContainer.get(instanceField.getName());
        if (Objects.nonNull(resource)) {
          setResource(instance, instanceField, resource);
        } else {
          final Class<?> resourceClass = classContainer.get(instanceField.getName());
          everyone(instanceField.getName(), resourceClass);
          final Object resourceCache = beanContainer.get(instanceField.getName());
          if (Objects.nonNull(resourceCache)) {
            setResource(instance, instanceField, resourceCache);
          }
        }
      }
    }
  }

  private void setResource (Object instance, Field instanceField, Object resourceCache) throws IllegalAccessException {
    instanceField.setAccessible(true);
    instanceField.set(instance, resourceCache);
  }

  public <T> T getBean (String beanName, Class<T> clzz) {
    final char[] nameChars = beanName.toCharArray();
    nameChars[0] -= 32;
    final Object o = beanContainer.get(beanName);
    return clzz.cast(o);
  }
}
