package net.medrag.tasks.charactercounter;

/**
 * @author Stanislav Tretyakov
 * 12.04.2021
 */
public class CharacterCounterTask {

    public static void main(final String[] args) {
        final String message = "Welcome to this interview!"
                + " What is the MOST frequent character in this String?"
                + " HINT: the return should be the character 'T' (guaranteed result).";

//        final CharacterCounter characterCounter = new MostFrequentCharacterCounter(message);

        // delete lines below
        CharacterCounter characterCounter = new CountingMostFrequentCharacterCounter(message);
        System.out.println(characterCounter.getCharacter());
        characterCounter = new MappingMostFrequentCharacterCounter(message);
        System.out.println(characterCounter.getCharacter());
    }
}
// Please write your solution below this line

