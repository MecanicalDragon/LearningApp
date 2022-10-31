package net.medrag.reactivit.reactivitapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class ReactivitApplication {
    private static final String DEBUG_MODE_ENV_VAR_NAME = "REACTIVIT_DEBUG_MODE";
    private static final String DEBUG_MODE_PROPERTY_NAME = "reactivit.debug.mode";

    public static void main(String[] args) {

        if (isDebugOn()) {
            // -Djdk.attach.allowAttachSelf=true if can't self-attach to jvm
            BlockHound.install(builder ->
                builder
                    .nonBlockingThreadPredicate(current -> current.or(thread -> thread.getName().startsWith("reactor")))
                    .install()
            );
            ReactorDebugAgent.init();
        }

        SpringApplication.run(ReactivitApplication.class, args);
    }

    private static boolean isDebugOn() {
        String envValue = System.getenv(DEBUG_MODE_ENV_VAR_NAME);
        String propsValue = System.getProperty(DEBUG_MODE_PROPERTY_NAME);
        return propsValue == null ? Boolean.parseBoolean(envValue) : Boolean.parseBoolean(propsValue);
    }

}
