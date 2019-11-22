package net.medrag;

import net.medrag.process.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbImporter implements CommandLineRunner {

    @Autowired
    private Processor processor;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DbImporter.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Override
    public void run(String... args) {
        processor.migrate();
    }
}
