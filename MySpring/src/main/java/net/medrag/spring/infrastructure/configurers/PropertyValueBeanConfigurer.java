package net.medrag.spring.infrastructure.configurers;

import lombok.SneakyThrows;
import lombok.val;
import net.medrag.spring.infrastructure.ApplicationContext;
import net.medrag.spring.infrastructure.annotations.PropertyValue;
import net.medrag.spring.infrastructure.api.BeanConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Stanislav Tretyakov
 * 02.07.2020
 */
public class PropertyValueBeanConfigurer implements BeanConfigurer {

    private Map<String, String> props;

    @SneakyThrows
    public PropertyValueBeanConfigurer(String properties) {
        props = new HashMap<>();
        val path = ClassLoader.getSystemClassLoader().getResource(properties).getPath();
        Properties p = new Properties();
        p.load(new FileInputStream(new File(path)));
        p.forEach((key, value) -> props.put(key.toString(), value.toString()));
    }

    public PropertyValueBeanConfigurer() {
        this("application.properties");
    }

    @Override
    @SneakyThrows
    public void configure(Object bean, ApplicationContext context) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            PropertyValue annotation = declaredField.getAnnotation(PropertyValue.class);
            if (annotation != null) {
                String prop = annotation.value();
                declaredField.setAccessible(true);
                Class type = declaredField.getType();
                Object value;
                if (type == Integer.class) {
                    value = Integer.valueOf(props.get(prop));
                } else if (type == Boolean.class) {
                    value = Boolean.valueOf(props.get(prop));
                } else value = props.get(prop);
                declaredField.set(bean, value);
            }
        }
    }
}
