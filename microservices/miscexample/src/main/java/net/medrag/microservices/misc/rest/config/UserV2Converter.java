package net.medrag.microservices.misc.rest.config;

import net.medrag.microservices.misc.rest.model.Seniority;
import net.medrag.microservices.misc.rest.model.UserV2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@Component
public class UserV2Converter implements Converter<String, UserV2> {

    @Override
    public UserV2 convert(String source) {
        final String[] split = source.split(",");
        final String[] split1 = split[0].split(":");
        final String[] split2 = split[1].split(":");
        final String[] split3 = split[2].split(":");
        final String[] split4 = split[3].split(":");
        return new UserV2(split1[1], split2[1], split3[1], Seniority.from(split4[1]));
    }
}
