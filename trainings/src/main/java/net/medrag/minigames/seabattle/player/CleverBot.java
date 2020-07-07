package net.medrag.minigames.seabattle.player;

import lombok.SneakyThrows;
import net.medrag.minigames.seabattle.battle.DigitIndex;
import net.medrag.minigames.seabattle.battle.LetterIndex;
import net.medrag.minigames.seabattle.battle.Shot;
import net.medrag.minigames.seabattle.battle.ShotResult;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 07.07.2020
 */
public class CleverBot extends SmartBot {

    private final static String BOT_NAME = "CleverBot";

    private final List<Shot> damagedDecks = new ArrayList<>();
    private final Deque<Shot> potentialVerticalHits = new ArrayDeque<>();
    private final Deque<Shot> potentialHorizontalHits = new ArrayDeque<>();

    protected String getBotName() {
        return name + "(" + BOT_NAME + ")";
    }

    @Override
    @SneakyThrows
    public Shot makeTheShot() {
        if (!potentialHorizontalHits.isEmpty() || !potentialVerticalHits.isEmpty()) {
            Thread.sleep(SLEEPING_TIME);
            Deque<Shot> deque = pickDirection();
            Shot shot = deque.element();
            String key = shot.getLetterIndex().toString();
            Integer digit = shot.getDigitIndex().getDigit();
            List<Integer> integers = foesSea.get(key);
            integers.remove(digit);
            if (integers.isEmpty()) {
                letterIndexes.remove(key);
                foesSea.remove(key);
            }
            battleController.addMessage(getBotName() + " shoots at " + shot);
            return shot;
        } else return super.makeTheShot();
    }

    private Deque<Shot> pickDirection() {
        if (potentialVerticalHits.isEmpty()) return potentialHorizontalHits;
        if (potentialHorizontalHits.isEmpty()) return potentialVerticalHits;
        if (potentialHorizontalHits.size() < potentialVerticalHits.size()) return potentialHorizontalHits;
        if (potentialVerticalHits.size() < potentialHorizontalHits.size()) return potentialVerticalHits;
        return (int) (Math.random() * 100) % 2 == 0 ? potentialVerticalHits : potentialHorizontalHits;
    }

    @Override
    protected void doConclusions(Shot shot, ShotResult shotResult) {
        if (shotResult == ShotResult.OBLITERATION) {
            super.doConclusions(shot, shotResult);
            for (Shot damagedDeck : damagedDecks) {
                super.doConclusions(damagedDeck, shotResult);
            }
            damagedDecks.clear();
            potentialVerticalHits.clear();
            potentialHorizontalHits.clear();
        } else if (shotResult == ShotResult.MISS) {
            potentialHorizontalHits.remove(shot);
            potentialVerticalHits.remove(shot);
        } else if (shotResult == ShotResult.HIT) {

            damagedDecks.add(shot);
            if (potentialHorizontalHits.remove(shot)) {
                potentialVerticalHits.clear();
                markAdjacentCells(shot, false, true);
            } else if (potentialVerticalHits.remove(shot)) {
                potentialHorizontalHits.clear();
                markAdjacentCells(shot, true, false);
            } else {
                markAdjacentCells(shot, true, true);
            }
        }
    }

    private void markAdjacentCells(Shot shot, boolean vertical, boolean horizontal) {
        char performedShotLetterIndex = shot.getLetterIndex().toString().charAt(0);
        int performedShotDigitIndex = shot.getDigitIndex().getDigit();

        if (vertical) {
            char c1 = (char) (performedShotLetterIndex - 1);
            if (c1 >= LetterIndex.CHAR_SHIFT) {
                List<Integer> integers = foesSea.get(String.valueOf(c1));
                if (integers != null && integers.contains(performedShotDigitIndex)) {
                    potentialVerticalHits.add(new Shot(LetterIndex.valueOf(String.valueOf(c1)),
                            DigitIndex.values()[performedShotDigitIndex]));
                }
            }
            char c2 = (char) (performedShotLetterIndex + 1);
            if (c2 <= LetterIndex.CHAR_SHIFT + SIZE) {
                List<Integer> integers = foesSea.get(String.valueOf(c2));
                if (integers != null && integers.contains(performedShotDigitIndex)) {
                    potentialVerticalHits.add(new Shot(LetterIndex.valueOf(String.valueOf(c2)),
                            DigitIndex.values()[performedShotDigitIndex]));
                }
            }
        }

        if (horizontal) {
            int d1 = performedShotDigitIndex - 1;
            int d2 = performedShotDigitIndex + 1;
            List<Integer> integers = foesSea.get(String.valueOf(performedShotLetterIndex));
            if (integers != null) {
                if (integers.contains(d1))
                    potentialHorizontalHits.add(new Shot(
                            LetterIndex.valueOf(String.valueOf(performedShotLetterIndex)), DigitIndex.values()[d1]));
                if (integers.contains(d2))
                    potentialHorizontalHits.add(new Shot(
                            LetterIndex.valueOf(String.valueOf(performedShotLetterIndex)), DigitIndex.values()[d2]));
            }
        }
    }
}
