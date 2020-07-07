package net.medrag.minigames.seabattle.player;

import lombok.SneakyThrows;
import net.medrag.minigames.seabattle.battle.DigitIndex;
import net.medrag.minigames.seabattle.battle.LetterIndex;
import net.medrag.minigames.seabattle.battle.Shot;

import java.util.Random;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public class DummyBot extends Player {

    protected final static Long SLEEPING_TIME = 1000L;
    protected static final String[] NAMES = {"Boris", "Alfonse", "Antony", "Susanna", "George", "Lucy", "Konstantin",
            "Beatrisa", "Francois", "Napoleon", "Benny", "Alfred", "Michelle", "Emanuele", "Amelia", "Marty", "Luna",
            "Gregory", "Maxim", "Sally", "Sue", "Betty", "Bjorn", "Agnesse", "Victor", "Vladimir", "Einstein", "Nobel",
            "Romul", "Arseniy", "Florian", "Dante", "Vergil", "Arkham", "Azazel", "Zena", "Fangorn", "Aragorn", "Lary",
            "Frodo", "Gandalf", "Gimli", "Legolas", "Conan", "Feanor", "Rinswind", "Geralt", "Pinky", "Pedro", "Sting",
            "Bartolomeo", "Angus", "Sebastian", "Francesca", "Cratos", "Zeus", "Gargantua", "Sherlock", "Selestina"};

    private final static String BOT_NAME = "DummyBot";

    protected final String name = NAMES[(int) (Math.random() * NAMES.length)];

    protected String getBotName() {
        return name + "(" + BOT_NAME + ")";
    }

    @Override
    public void placeShips() {
        placeShipsRandomly();
    }

    @Override
    @SneakyThrows
    public Shot makeTheShot() {
        Thread.sleep(SLEEPING_TIME);
        Random random = new Random();
        LetterIndex letterIndex = LetterIndex.values()[random.nextInt(LetterIndex.values().length)];
        DigitIndex digitIndex = DigitIndex.values()[random.nextInt(DigitIndex.values().length)];
        Shot shot = new Shot(letterIndex, digitIndex);
        battleController.addMessage(getBotName() + " shoots at " + shot);
        return shot;
    }

    @Override
    protected void updateShipsDislocations() {
        for (int j = 0; j < fleetLocations.length; j++) {
            for (int i = 0; i < fleetLocations[j].length; i++) {
                if (fleetLocations[j][i] != '\u0000' && fleetLocations[j][i] != SHIP) {
                    sea[j] = new StringBuilder(sea[j]).replace(LOCATIONS[i], LOCATIONS[i] + 1,
                            String.valueOf(fleetLocations[j][i])).toString();
                }
            }
        }
    }
}
