package net.medrag.microservices.misc.rest.config;

import net.medrag.microservices.misc.rest.model.Seniority;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@Component
public class SeniorityConverter implements Converter<String, Seniority> {
    @Override
    public Seniority convert(String source) {
        return Seniority.from(source);
    }
}
