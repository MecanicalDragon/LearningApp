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
                + " What is the MOST frequent character in this String?"
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

    private final int[] abc = new int[26];

    public MostFrequentCharacterCounter(String string) {
        this.string = string;
    }

    @Override
    public char getCharacter() {
        if (mostFrequent != null) {
            return mostFrequent;
        } else {
            mostFrequent = countImpl();
        }
        return mostFrequent;
    }

    private char countImpl() {
        int a = 'a';
        for (char c : string.toLowerCase().toCharArray()) {
            if (Character.isAlphabetic(c)) {
                abc[c - a]++;
            }
        }
        int m = 0;
        for (int i = 0; i < abc.length; i++) {
            if (abc[i] > m) m = i;
        }
        return (char) (m + a);
    }

    private char mapImpl() {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : string.toLowerCase().toCharArray()) {
            if (Character.isAlphabetic(c)) {
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

