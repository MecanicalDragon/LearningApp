package net.medrag.spring.infrastructure;

import lombok.SneakyThrows;
import net.medrag.spring.infrastructure.api.BeanConfigurer;
import net.medrag.spring.infrastructure.api.Configuration;
import net.medrag.spring.infrastructure.configurers.AutoInjectBeanConfigurer;
import net.medrag.spring.infrastructure.configurers.PropertyValueBeanConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 02.07.2020
 */
public class BeanInitializer {

    private List<BeanConfigurer> beanConfigurers;


    public BeanInitializer() {
        beanConfigurers = new ArrayList<>();
        beanConfigurers.add(new AutoInjectBeanConfigurer());
        beanConfigurers.add(new PropertyValueBeanConfigurer());
    }

    @SneakyThrows
    public <T> T createBeanWithDefinition(Class<T> definition) {
        return definition.getDeclaredConstructor().newInstance();
    }

    public void configureBean(Object bean, ApplicationContext context){
        for (BeanConfigurer beanConfigurer : beanConfigurers) {
            beanConfigurer.configure(bean, context);
        }
    }
}
