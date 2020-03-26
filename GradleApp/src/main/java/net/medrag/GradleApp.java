package net.medrag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Stanislav Tretyakov
 * 26.03.2020
 */
public class GradleApp {

    private static final Logger log = LoggerFactory.getLogger(GradleApp.class);

    public static void main(String[] args) {
        log.info("We've just built a simple gradle application with logging on java.");
    }
}