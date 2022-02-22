package net.medrag.tasks.charactercounter;

/**
 * @author Stanislav Tretyakov
 * 07.02.2022
 */
public class CountingMostFrequentCharacterCounter extends MostFrequentCharacterCounter{
    public CountingMostFrequentCharacterCounter(String string) {
        super(string);
    }

    char mostFrequentImpl() {
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
}
