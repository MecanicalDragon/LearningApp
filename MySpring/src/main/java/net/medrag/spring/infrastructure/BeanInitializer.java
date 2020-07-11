package net.medrag.spring.infrastructure;

import lombok.SneakyThrows;
import net.medrag.spring.infrastructure.annotations.ConfigurationOrder;
import net.medrag.spring.infrastructure.annotations.InitMethod;
import net.medrag.spring.infrastructure.api.BeanConfigurer;
import net.medrag.spring.infrastructure.api.ProxyConfigurer;
import net.medrag.spring.infrastructure.configurers.AutoInjectBeanConfigurer;
import net.medrag.spring.infrastructure.configurers.BroadcastProxyConfigurer;
import net.medrag.spring.infrastructure.configurers.PropertyValueBeanConfigurer;
import net.medrag.spring.infrastructure.configurers.SabotageProxyConfigurer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 02.07.2020
 */
public class BeanInitializer {

    private List<BeanConfigurer> beanConfigurers;
    private List<ProxyConfigurer> proxyConfigurers;


    public BeanInitializer() {
        beanConfigurers = new ArrayList<>();
        beanConfigurers.add(new AutoInjectBeanConfigurer());
        beanConfigurers.add(new PropertyValueBeanConfigurer());

        beanConfigurers.sort((a,b) -> {
            ConfigurationOrder annotation1 = a.getClass().getAnnotation(ConfigurationOrder.class);
            ConfigurationOrder annotation2 = b.getClass().getAnnotation(ConfigurationOrder.class);
            int order1 = annotation1 == null ? 10 : annotation1.order();
            int order2 = annotation2 == null ? 10 : annotation2.order();
            return order2 - order1;
        });

        proxyConfigurers = new ArrayList<>();
        proxyConfigurers.add(new BroadcastProxyConfigurer());
        proxyConfigurers.add(new SabotageProxyConfigurer());

        proxyConfigurers.sort((a,b) -> {
            ConfigurationOrder annotation1 = a.getClass().getAnnotation(ConfigurationOrder.class);
            ConfigurationOrder annotation2 = b.getClass().getAnnotation(ConfigurationOrder.class);
            int order1 = annotation1 == null ? 10 : annotation1.order();
            int order2 = annotation2 == null ? 10 : annotation2.order();
            return order2 - order1;
        });
    }

    @SneakyThrows
    public <T> T createBeanWithDefinition(Class<? extends T> definition) {
        return definition.getDeclaredConstructor().newInstance();
    }

    public Object configureBean(Object bean, ApplicationContext context) {

        for (BeanConfigurer beanConfigurer : beanConfigurers) {
            beanConfigurer.configure(bean, context);
        }

        invokeInitMethod(bean);

        Class beanClass = bean.getClass();
        for (ProxyConfigurer configurer : proxyConfigurers){
            bean = configurer.createProxy(bean, beanClass);
        }

        return bean;
    }

    private void invokeInitMethod(Object bean) {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    InitMethod annotation = method.getAnnotation(InitMethod.class);
                    if (annotation.mandatory()) {
                        System.err.println("ERROR: Mandatory init method could not been invoked. Application will be closed.");
                        e.printStackTrace();
                        System.exit(666);
                    } else {
                        System.out.println("WARNING: Init method failed, but application will proceed.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
