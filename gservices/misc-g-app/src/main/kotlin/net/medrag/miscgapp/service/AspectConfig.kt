package net.medrag.miscgapp.service

import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import io.micrometer.core.aop.CountedAspect
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * To make this configuration work dependency `spring-boot-starter-aop` required.
 * @author Stanislav Tretyakov
 * 24.12.2021
 */
@Configuration
class AspectConfig {

    /**
     * TimedAspect bean is required to use [Timed].
     */
    @Bean
    fun timedAspect(registry: MeterRegistry): TimedAspect = TimedAspect(registry)

    /**
     * CountedAspect bean is required to use [Counted].
     */
    @Bean
    fun countedAspect(registry: MeterRegistry): CountedAspect = CountedAspect(registry)
}
