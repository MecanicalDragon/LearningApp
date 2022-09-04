package net.medrag.reactivit.reactivitapp.customreadiness;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Getter
@ToString
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "ratelimiting")
public final class ConcurrentRequestsProperties {
    private final int upThreshold;
    private final int downThreshold;
}
