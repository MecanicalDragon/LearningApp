package net.medrag.devBuilder.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service provides jokes to the ARS-staff for relaxation from hard and monotonous work
 * <p>
 * {@author} Stanislav Tretyakov
 * 21.09.2018
 */
@Service
public class Joker {

    private static final List<String> HUMOR_ALMANAC = new ArrayList<>(92747);

    /**
     * Joker method
     *
     * @return - fresh random joke
     */
    public String getJoke() {
        int i = new Random().nextInt(92745);
        StringBuilder sb = new StringBuilder();
        while (HUMOR_ALMANAC.get(i++).trim().length() > 0) {
        }
        while (HUMOR_ALMANAC.get(i).trim().length() > 0) {
            sb.append("\n").append(HUMOR_ALMANAC.get(i++));
        }
        return sb.toString();
    }

    /**
     * method prepares jokes.
     */
    @PostConstruct
    private void prepareJokes() {

        try (InputStream is = getClass().getResourceAsStream("/static/imp.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is, "Windows-1251"))) {
            while (br.ready()) {
                HUMOR_ALMANAC.add(br.readLine());
            }
        } catch (IOException ignored) {
            HUMOR_ALMANAC.add("It's not a time for jokes - keep calm, work hard, don't be distracted!");
        }
    }
}
