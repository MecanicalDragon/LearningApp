package net.medrag.springexamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringExamplesApplication {

    // SOME_ARBITRARY_PROPERTY=yyy Precedence for Spring App Context: 2
    private static final String ENV_VAR = "SOME_ARBITRARY_PROPERTY";
    // -Dsome.arbitrary.property=xxx Precedence for Spring App Context: 1
    private static final String PROPERTY = "some.arbitrary.property";

	public static void main(String[] args) {

        System.out.println("----");
        System.out.println(System.getenv(ENV_VAR));
//        System.out.println(System.getenv());
        System.out.println(System.getProperty(PROPERTY));
//        System.out.println(System.getProperties());
        System.out.println("----");
        final var context = SpringApplication.run(SpringExamplesApplication.class, args);
        final var property = context.getEnvironment().getProperty(PROPERTY);
        System.out.println("----");
        System.out.println(property);
        System.out.println("----");
    }

}
