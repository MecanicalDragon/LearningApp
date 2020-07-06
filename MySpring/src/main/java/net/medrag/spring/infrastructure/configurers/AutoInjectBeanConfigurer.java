package net.medrag.spring.infrastructure.configurers;

import lombok.SneakyThrows;
import net.medrag.spring.infrastructure.ApplicationContext;
import net.medrag.spring.infrastructure.annotations.AutoInject;
import net.medrag.spring.infrastructure.api.BeanConfigurer;

import java.lang.reflect.Field;

/**
 * @author Stanislav Tretyakov
 * 02.07.2020
 */
public class AutoInjectBeanConfigurer implements BeanConfigurer {
    @Override
    @SneakyThrows
    public void configure(Object bean, ApplicationContext context) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            AutoInject annotation = declaredField.getAnnotation(AutoInject.class);
            if (annotation != null) {
                String beanId = annotation.beanId().isEmpty() ? declaredField.getType().getName() : annotation.beanId();
                Object bean1 = context.getBean(beanId);
                if (bean1 == null) {
                    System.err.println("No such bean has been found among definitions: '" + beanId + "'.");
                    System.exit(3);
                } else {
                    declaredField.setAccessible(true);
                    declaredField.set(bean, bean1);
                }
            }
        }
    }
}
