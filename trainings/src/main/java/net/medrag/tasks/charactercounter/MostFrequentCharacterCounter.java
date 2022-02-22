package net.medrag.tasks.charactercounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Stanislav Tretyakov
 * 07.02.2022
 */
abstract class MostFrequentCharacterCounter implements CharacterCounter {
    protected final String string;
    protected Character mostFrequent;

    protected final int[] abc = new int[26];

    public MostFrequentCharacterCounter(String string) {
        this.string = string;
    }

    @Override
    public char getCharacter() {
        if (mostFrequent != null) {
            return mostFrequent;
        } else {
            mostFrequent = mostFrequentImpl();
        }
        return mostFrequent;
    }

    abstract char mostFrequentImpl();
}
