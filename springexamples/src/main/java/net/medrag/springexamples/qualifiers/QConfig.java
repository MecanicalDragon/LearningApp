package net.medrag.springexamples.qualifiers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stanislav Tretyakov
 * 13.06.2022
 */
@Configuration
public class QConfig {
    @Bean
    public QInterface qin1(){
        return new Q1();
    }

    @Bean
    @Qualifier("two")
    public QInterface qin2(){
        return new Q2();
    }
}
