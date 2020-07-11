package net.medrag.spring.infrastructure.configurers;

import lombok.SneakyThrows;
import net.medrag.spring.infrastructure.annotations.ConfigurationOrder;
import net.medrag.spring.infrastructure.annotations.SabotageTarget;
import net.medrag.spring.infrastructure.api.ProxyConfigurer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Stanislav Tretyakov
 * 10.07.2020
 */
@ConfigurationOrder(order = 6)
public class SabotageProxyConfigurer implements ProxyConfigurer {

    @Override
    public <T> T createProxy(T bean, Class<? extends T> beanClass) {
        for (Method m : beanClass.getMethods()) {
            if (m.isAnnotationPresent(SabotageTarget.class)) {
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
        if (originalMethod.isAnnotationPresent(SabotageTarget.class)) {
            double chance = originalMethod.getAnnotation(SabotageTarget.class).chance();
            if (Math.random() * 100 < chance) {
                System.err.println("SABOTAGE SUCCEED!!!");
                return null;
            } else {
                return method.invoke(bean, args);
            }
        } else return method.invoke(bean, args);
    }
}
