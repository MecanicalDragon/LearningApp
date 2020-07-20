package net.medrag.spring.infrastructure.config;

import net.medrag.spring.infrastructure.annotations.Bean;
import net.medrag.spring.infrastructure.api.BeanConfigurer;
import net.medrag.spring.infrastructure.api.ConfigurationReader;
import net.medrag.spring.infrastructure.api.ProxyConfigurer;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Stanislav Tretyakov
 * 11.07.2020
 */
public class PackageReader implements ConfigurationReader {

    private Reflections packageScanner;

    public PackageReader(String packageToScan) {
        packageScanner = new Reflections(packageToScan);
    }

    @Override
    public Map<String, String> readConfiguration() {
        Set<Class<?>> beans = packageScanner.getTypesAnnotatedWith(Bean.class);
        Map<String, String> beanDefinitions = new HashMap<>();
        for (Class<?> bean : beans) {
            Bean annotation = bean.getAnnotation(Bean.class);
            if (annotation.id().isBlank()) {
                if (annotation.registerByInterface()) {
                    Class<?>[] interfaces = bean.getInterfaces();
                    if (interfaces.length == 1) {
                        beanDefinitions.put(interfaces[0].getName(), bean.getName());
                    } else {
                        System.err.println("Bean interface determination is ambiguous: " + bean.getName());
                        System.err.println("Application context failed to start!");
                        System.exit(3);
                    }
                } else {
                    beanDefinitions.put(bean.getName(), bean.getName());
                }
            } else {
                beanDefinitions.put(annotation.id(), bean.getName());
            }
        }
        return beanDefinitions;
    }

    public Set<Class<? extends ProxyConfigurer>> getProxyConfigurers() {
        return packageScanner.getSubTypesOf(ProxyConfigurer.class);
    }

    public Set<Class<? extends BeanConfigurer>> getBeanConfigurers() {
        return packageScanner.getSubTypesOf(BeanConfigurer.class);
    }

    public <T> Class getBean(Class<T> aClass) {
        Set<Class<? extends T>> beanCandidates = packageScanner.getSubTypesOf(aClass);
        if (beanCandidates.isEmpty()) {
            System.err.println("No beans have been found for class " + aClass);
            System.exit(3);
        } else if (beanCandidates.size() > 1) {
            System.err.println("Two or more beans have been found for class " + aClass);
            System.exit(3);
        } else return beanCandidates.iterator().next();
        return null;
    }
}
