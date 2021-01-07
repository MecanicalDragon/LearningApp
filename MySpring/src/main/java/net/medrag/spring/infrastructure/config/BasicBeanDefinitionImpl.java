package net.medrag.spring.infrastructure.config;

import net.medrag.spring.infrastructure.api.BeanDefinition;

/**
 * @author Stanislav Tretyakov
 * 08.01.2021
 */
public class BasicBeanDefinitionImpl implements BeanDefinition {

    private final String name;
    private final int precedence;

    public BasicBeanDefinitionImpl(String name, int precedence) {
        this.name = name;
        this.precedence = precedence;
    }

    public BasicBeanDefinitionImpl(String name) {
        this(name, 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrecedence() {
        return precedence;
    }
}
