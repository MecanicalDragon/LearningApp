package net.medrag.SwaggerTests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan("org.openapitools.configuration")
@ComponentScan("net.medrag.SwaggerTests")
//@ComponentScans([@ComponentScan("org.openapitools.configuration"), @ComponentScan("net.medrag.SwaggerTests")])
public class SwaggerTestsApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SwaggerTestsApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(SwaggerTestsApplication.class, args);
	}

}
