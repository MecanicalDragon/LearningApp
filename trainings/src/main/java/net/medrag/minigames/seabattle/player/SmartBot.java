package net.medrag.minigames.seabattle.player;

import net.medrag.minigames.seabattle.battle.Shot;
import net.medrag.minigames.seabattle.battle.ShotResult;
import net.medrag.minigames.seabattle.battle.LetterIndex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Stanislav Tretyakov
 * 07.07.2020
 */
public class SmartBot extends SimpleBot {

    private final static String BOT_NAME = "SmartBot";

    protected String getBotName() {
        return name + "(" + BOT_NAME + ")";
    }

    @Override
    protected void doConclusions(Shot shot, ShotResult shotResult) {
        if (shotResult == ShotResult.OBLITERATION) {
            char c = shot.getLetterIndex().toString().charAt(0);
            Set<Character> charSet = new HashSet<>();
            charSet.add(c);
            charSet.add((char) Math.max(c - 1, LetterIndex.CHAR_SHIFT));
            charSet.add((char) Math.min(c + 1, LetterIndex.CHAR_SHIFT + SIZE));
            int digit = shot.getDigitIndex().getDigit();
            Set<Integer> digitSet = new HashSet<>();
            digitSet.add(digit);
            digitSet.add(Math.max(digit - 1, 0));
            digitSet.add(Math.min(digit + 1, 9));
            for (Character character : charSet) {
                List<Integer> integers = foesSea.get(character.toString());
                if (integers != null) {
                    for (Integer integer : digitSet) {
                        integers.remove(integer);
                    }
                    if (integers.isEmpty()) {
                        foesSea.remove(character.toString());
                        letterIndexes.remove(character.toString());
                    }
                }
            }
        }
    }
}
