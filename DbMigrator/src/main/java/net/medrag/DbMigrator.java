package net.medrag;

import org.flywaydb.core.Flyway;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * {@author} Stanislav Tretyakov
 * 26.09.2019
 */
public class DbMigrator {

    public static void main(String[] args) {
        try {
            File props = args.length > 0 ? new File(args[0]) : new File("application.properties");
            if (args.length > 0){
                System.out.println("Path to properties file have been found in app's arguments. We'll use it, even if it's incorrect.");
            } else {
                System.out.println("Properties file will be taken in application root directory. We'll use it, even if it's incorrect.");
            }
            FileInputStream fis = new FileInputStream(props);
            Properties property = new Properties();
            property.load(fis);

            String host = property.getProperty("spring.datasource.url");
            String login = property.getProperty("spring.datasource.username");
            String password = property.getProperty("spring.datasource.password");
            String clean = property.getProperty("migrator.clean.database");
            String dir = property.getProperty("migrator.sql.scripts.dir");

            if (host != null && login != null && password != null && dir != null) {
                migrate(host, login, password, clean, dir);
            } else {
                System.out.println("You need to specify properties in properties-file. Look the installation guide.");
            }

        } catch (IOException e) {
            System.err.println("You should have a application.properties file in the project root directory or convey a path to it in app's arguments!");
        }
    }

    private static void migrate(String url, String username, String password, String clean, String dir) {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("filesystem:" + dir);
        flyway.setDataSource(url, username, password);
        if ("true".equals(clean)) flyway.clean();
        flyway.migrate();
    }
}
