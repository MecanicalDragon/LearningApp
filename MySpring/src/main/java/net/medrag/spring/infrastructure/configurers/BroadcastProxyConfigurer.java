package net.medrag.spring.infrastructure.configurers;

import lombok.SneakyThrows;
import net.medrag.spring.infrastructure.annotations.Broadcast;
import net.medrag.spring.infrastructure.annotations.ConfigurationOrder;
import net.medrag.spring.infrastructure.api.ProxyConfigurer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Stanislav Tretyakov
 * 10.07.2020
 */
@ConfigurationOrder(order = 5)
public class BroadcastProxyConfigurer implements ProxyConfigurer {

    @Override
    public <T> T createProxy(T bean, Class<? extends T> beanClass) {
        for (Method m : beanClass.getMethods()) {
            if (m.isAnnotationPresent(Broadcast.class)) {
                if (beanClass.getInterfaces().length == 0) {
                    return (T) Enhancer.create(beanClass,
                            (InvocationHandler) (proxy, method, args) -> proxyLogic(method, bean, beanClass, args));
                } else {
                    return (T) Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                            (proxy1, method, args) -> proxyLogic(method, bean, beanClass, args));
                }
            }
        }
        return bean;
    }

    @SneakyThrows
    private <T> Object proxyLogic(Method method, T bean, Class<? extends T> beanClass, Object[] args) {
        Method originalMethod = beanClass.getMethod(method.getName(), method.getParameterTypes());
        if (originalMethod.isAnnotationPresent(Broadcast.class)) {
            System.out.println("HOT NEWS!!!");
            Object invoke = method.invoke(bean, args);
            System.out.println("::HERE COULD BE YOUR ADVERTISE::");
            return invoke;
        } else return method.invoke(bean, args);
    }
}
