package net.medrag.reactivit.reactivitapp.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.nio.file.Files;

@SpringBootTest
class LogParser {

    public static final String REACTOR_THREAD = "reactor-http-nio-";

    @Value("file:C:/myrepos/reactivit/logs/reactivit.log")
    private Resource logs;

    @Test
    @DisplayName("parse logs and test that MdcUtils work properly")
    @SneakyThrows
    void testLogs() {
        if (!logs.exists()) {
            throw new RuntimeException("There're no logs in log directory!");
        }
        try (var log = Files.lines(logs.getFile().toPath())) {
            log.forEach(it -> {
                if (!it.startsWith(REACTOR_THREAD)) {
                    return;
                }
                var mdc = new StringBuilder();
                var logged = new StringBuilder();
                int state = 0;
                for (char aChar : it.toCharArray()) {
                    if (aChar == '[' || aChar == ']') {
                        state++;
                        continue;
                    }
                    if (state == 1) {
                        mdc.append(aChar);
                    } else if (state == 3) {
                        logged.append(aChar);
                    }
                }

                final var loggedValue = logged.toString();
                final var split = mdc.toString().split(", ");
                for (String splitValue : split) {
                    final var substring = splitValue.substring(splitValue.indexOf('=') + 1);
                    if (!substring.equals(loggedValue)) {
                        throw new RuntimeException(substring);
                    }
                }
            });
            System.out.println("All variables match!");
        }
    }
}