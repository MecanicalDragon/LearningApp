package net.medrag.spring.infrastructure;

import lombok.SneakyThrows;
import net.medrag.spring.infrastructure.annotations.ConfigurationOrder;
import net.medrag.spring.infrastructure.annotations.InitMethod;
import net.medrag.spring.infrastructure.api.BeanConfigurer;
import net.medrag.spring.infrastructure.api.BeanDefinition;
import net.medrag.spring.infrastructure.api.ConfigurationReader;
import net.medrag.spring.infrastructure.api.ProxyConfigurer;
import net.medrag.spring.infrastructure.config.PackageReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 02.07.2020
 */
class BeanInitializer {

    private List<BeanConfigurer> beanConfigurers;
    private List<ProxyConfigurer> proxyConfigurers;

    @SneakyThrows
    BeanInitializer(PackageReader packageReader) {

        beanConfigurers = new ArrayList<>();
        proxyConfigurers = new ArrayList<>();

        for (Class<? extends BeanConfigurer> bpp : packageReader.getBeanConfigurers()) {
            beanConfigurers.add(bpp.getDeclaredConstructor().newInstance());
        }

        for (Class<? extends ProxyConfigurer> bpp : packageReader.getProxyConfigurers()) {
            proxyConfigurers.add(bpp.getDeclaredConstructor().newInstance());
        }

        sortBpp(beanConfigurers);
        sortBpp(proxyConfigurers);
    }

    @SneakyThrows
    BeanInitializer(ConfigurationReader configurationReader) {

        beanConfigurers = new ArrayList<>();
        proxyConfigurers = new ArrayList<>();

        Map<String, BeanDefinition> configuration = configurationReader.readConfiguration();
        for (String pbbName : configuration.keySet()) {
            try {
                Class<?> bpp = Class.forName(pbbName);
                Object instance = bpp.getDeclaredConstructor().newInstance();
                for (Class<?> anInterface : bpp.getInterfaces()) {
                    if (anInterface == BeanConfigurer.class) {
                        beanConfigurers.add((BeanConfigurer) instance);
                    } else if (anInterface == ProxyConfigurer.class) {
                        proxyConfigurers.add((ProxyConfigurer) instance);
                    }
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Hasn't been found class for name '" + e.getMessage() + "'.");
                System.err.println("Application failed to start.");
                System.exit(3);
            }
        }

        sortBpp(beanConfigurers);
        sortBpp(proxyConfigurers);
    }

    private<T> void sortBpp(List<T> bppList) {
        bppList.sort((a, b) -> {
            ConfigurationOrder annotation1 = a.getClass().getAnnotation(ConfigurationOrder.class);
            ConfigurationOrder annotation2 = b.getClass().getAnnotation(ConfigurationOrder.class);
            int order1 = annotation1 == null ? 10 : annotation1.order();
            int order2 = annotation2 == null ? 10 : annotation2.order();
            return order2 - order1;
        });
    }

    @SneakyThrows
    <T> T createBeanWithDefinition(Class<? extends T> definition) {
        return definition.getDeclaredConstructor().newInstance();
    }

    Object configureBean(Object bean, ApplicationContext context) {

        for (BeanConfigurer beanConfigurer : beanConfigurers) {
            beanConfigurer.configure(bean, context);
        }

        invokeInitMethod(bean);

        Class beanClass = bean.getClass();
        for (ProxyConfigurer configurer : proxyConfigurers) {
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
