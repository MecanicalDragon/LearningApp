package net.medrag.minigames.seabattle.player;

import lombok.SneakyThrows;
import net.medrag.minigames.seabattle.battle.DigitIndex;
import net.medrag.minigames.seabattle.battle.LetterIndex;
import net.medrag.minigames.seabattle.battle.Shot;

import java.util.*;


/**
 * @author Stanislav Tretyakov
 * 07.07.2020
 */
public class SimpleBot extends DummyBot {

    protected final Map<String, List<Integer>> foesSea = new HashMap<>();
    protected final List<String> letterIndexes = new ArrayList<>(10);

    private final static List<Integer> DIGIT_INDEXES = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private final static String BOT_NAME = "SimpleBot";

    protected String getBotName() {
        return name + "(" + BOT_NAME + ")";
    }

    public SimpleBot() {
        for (LetterIndex value : LetterIndex.values()) {
            letterIndexes.add(value.toString());
        }
        for (String letterIndex : letterIndexes) {
            foesSea.put(letterIndex, new ArrayList<>(DIGIT_INDEXES));
        }
    }

    @Override
    @SneakyThrows
    public Shot makeTheShot() {
        Thread.sleep(SLEEPING_TIME);
        Random random = new Random();
        String key = letterIndexes.get(random.nextInt(letterIndexes.size()));
        List<Integer> integers = foesSea.get(key);
        int d = integers.remove(random.nextInt(integers.size()));
        if (integers.isEmpty()) {
            foesSea.remove(key);
            letterIndexes.remove(key);
        }
        LetterIndex letterIndex = LetterIndex.valueOf(String.valueOf(key));
        DigitIndex digitIndex = DigitIndex.values()[d];
        Shot shot = new Shot(letterIndex, digitIndex);
        battleController.addMessage(getBotName() + " shoots at " + shot);
        return shot;
    }
}
