package net.medrag.spring.infrastructure;

import lombok.Data;

import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 11.07.2020
 */
@Data
public class BeanDefinition {
    private String beanId;
    private Class beanClass;
    private Map<String, Object> beanProperties;
}
