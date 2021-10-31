package net.medrag.something;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Stanislav Tretyakov
 * 12.04.2021
 */
public class CodingExercise {

    public static void main(final String[] args) {
        final String message = "Welcome to this interview!"
                + " What is the MOST frequent non-whitespace character in this String?"
                + " HINT: the return should be the character 'T' (guaranteed result).";

        final CharacterCounter characterCounter = new MostFrequentCharacterCounter(message);

        System.out.println(characterCounter.getCharacter());
    }
}
// Please write your solution below this line

interface CharacterCounter {
    char getCharacter();
}

class MostFrequentCharacterCounter implements CharacterCounter {
    private final String string;
    Character mostFrequent;

    public MostFrequentCharacterCounter(String string) {
        this.string = string;
    }

    @Override
    public char getCharacter() {
        if (mostFrequent != null) {
            return mostFrequent;
        }
        return mapImpl();
    }

    private char mapImpl() {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : string.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                map.compute(c, (k, v) -> {
                    int i = Optional.ofNullable(v).orElse(0);
                    return ++i;
                });
            }
        }
        return map.entrySet().stream()
                .reduce((e1, e2) -> e1.getValue() > e2.getValue() ? e1 : e2)
                .map(Map.Entry::getKey).orElse((char) 0);
    }
}

