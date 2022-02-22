package net.medrag.tasks.charactercounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Stanislav Tretyakov
 * 07.02.2022
 */
public class MappingMostFrequentCharacterCounter extends MostFrequentCharacterCounter {
    public MappingMostFrequentCharacterCounter(String string) {
        super(string);
    }

    char mostFrequentImpl() {
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
