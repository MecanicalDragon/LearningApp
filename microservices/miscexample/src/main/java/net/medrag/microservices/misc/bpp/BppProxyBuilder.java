package net.medrag.microservices.misc.bpp;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Stanislav Tretyakov
 * 21.10.2021
 */
@Component
public class BppProxyBuilder implements BeanPostProcessor {

    private Map<String, ImmutablePair<Object, Set<InterceptionData>>> beans = new HashMap<>();

    private record InterceptionData(String methodName, String printBefore, String printAfter) {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final Set<InterceptionData> interceptionData = Stream.of(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(BppIntercepted.class)).map(
                        method -> new InterceptionData(
                                method.getName(),
                                method.getAnnotation(BppIntercepted.class).printBefore(),
                                method.getAnnotation(BppIntercepted.class).printAfter()
                        )).collect(Collectors.toSet());
        if (!interceptionData.isEmpty()) {
            beans.put(beanName, new ImmutablePair<>(bean, interceptionData));
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final ImmutablePair<Object, Set<InterceptionData>> pair = beans.get(beanName);
        if (pair != null) {
            if (Math.random() > 0.5) {
                return buildProxy(bean, beanName, pair);
            } else {
                return buildEnhancer(bean, beanName, pair);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * More useful CGLIB methods here: https://www.baeldung.com/cglib
     */
    private Object buildEnhancer(Object bean, String beanName, ImmutablePair<Object, Set<InterceptionData>> pair) {

        System.out.println("Using CGLIB...");
        Object storedBean = pair.left;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(storedBean.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            final var data = pair.right;
            final Optional<InterceptionData> first = data.stream().filter(x -> x.methodName().equals(method.getName())).findFirst();
            if (first.isPresent()) {
                System.out.println(first.get().printBefore);
                final Object invoke = method.invoke(bean, args);
                System.out.println(first.get().printAfter);
                return invoke;
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });
        return BeanPostProcessor.super.postProcessAfterInitialization(enhancer.create(), beanName);
    }

    private Object buildProxy(Object bean, String beanName, ImmutablePair<Object, Set<InterceptionData>> pair) {

        System.out.println("Using proxy...");
        Object storedBean = pair.left;

        final Object proxiedBean = Proxy.newProxyInstance(
                storedBean.getClass().getClassLoader(),
                storedBean.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    final var data = pair.right;
                    final Optional<InterceptionData> first = data.stream().filter(x -> x.methodName().equals(method.getName())).findFirst();
                    if (first.isPresent()) {
                        System.out.println(first.get().printBefore);
                        final Object invoke = method.invoke(bean, args);
                        System.out.println(first.get().printAfter);
                        return invoke;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
        );
        return BeanPostProcessor.super.postProcessAfterInitialization(proxiedBean, beanName);
    }
}
