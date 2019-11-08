package net.medrag.devBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DevBuilderApplication extends SpringBootServletInitializer {

	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		ctx = SpringApplication.run(DevBuilderApplication.class, args);
	}

	public static void shutDown(){
		ctx.close();
	}

}
