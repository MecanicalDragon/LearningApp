package net.medrag.springexamples.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Getter
@ToString
@ConstructorBinding
@RequiredArgsConstructor
public final class Props {
    private final String value;
    private final String variable;
}
