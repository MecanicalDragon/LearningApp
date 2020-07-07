package net.medrag.minigames.seabattle.player;

import lombok.Setter;
import net.medrag.minigames.seabattle.SeaBattle;
import net.medrag.minigames.seabattle.ShipDislocationException;
import net.medrag.minigames.seabattle.battle.Deck;
import net.medrag.minigames.seabattle.battle.Shot;
import net.medrag.minigames.seabattle.battle.ShotResult;
import net.medrag.minigames.seabattle.placers.ShipPlacer;
import net.medrag.minigames.seabattle.battle.LetterIndex;

import java.util.*;

import static net.medrag.minigames.seabattle.placers.HorizontalShipPlacer.HORIZONTAL_PLACER;
import static net.medrag.minigames.seabattle.placers.SingleDeckShipPlacer.SINGLE_DECK_PLACER;
import static net.medrag.minigames.seabattle.placers.VerticalShipPlacer.VERTICAL_PLACER;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public abstract class Player {

    public final static char SHIP = '#';
    protected final static char MISS = '*';
    protected final static char DECK = 'X';
    protected final static int SIZE = 10;
    protected final static int[] LOCATIONS = {5, 9, 13, 17, 21, 25, 29, 33, 37, 41};

    protected final static Map<Integer, Integer> DEFAULT_FLEET = Map.of(4, 1, 3, 2, 2, 3, 1, 4);
    protected final char[][] fleetLocations = new char[10][10];
    protected final String[] sea = new String[10];

    @Setter
    protected SeaBattle battleController;

    private final static String ROW = " / |   |   |   |   |   |   |   |   |   |   |";
    private final static char LETTER = '/';
    private final Map<Integer, Integer> fleet = new HashMap<>(DEFAULT_FLEET);

    Player() {
        Arrays.fill(sea, ROW);
        initFleetRows(sea);
    }

    public String getSeaRow(int i) {
        return sea[i];
    }

    public boolean isDefeated() {
        return fleet.isEmpty();
    }

    public String printFleet() {
        return "Ships left: " + fleet.toString();
    }

    private static void initFleetRows(String[] fleet) {
        for (int i = 0; i < fleet.length; i++) {
            fleet[i] = fleet[i].replace(LETTER, (char) (i + LetterIndex.CHAR_SHIFT));
        }
    }

    void placeShipsRandomly() {
        Random random = new Random();
        for (int i = 4, j = 1; i > 0; i--, j++) {
            for (int k = 0; k < j; k++) {
                boolean placed = false;
                while (!placed) {
                    int x = random.nextInt(11 - i);
                    int y = random.nextInt(11 - i);
                    ShipPlacer placer = i == 1 ? SINGLE_DECK_PLACER :
                            random.nextInt(10) % 2 == 0 ? HORIZONTAL_PLACER : VERTICAL_PLACER;
                    try {
                        placer.placeShip(x, y, i, fleetLocations);
                        placed = true;
                    } catch (ShipDislocationException ignored) {
                    }
                }
            }
        }
    }

    public ShotResult makeMove(Player target) {
        Shot shot = makeTheShot();
        ShotResult shotResult = target.acceptHit(shot);
        doConclusions(shot, shotResult);
        return shotResult;
    }

    protected void doConclusions(Shot shot, ShotResult shotResult) {
    }

    protected ShotResult acceptHit(Shot shot) {
        try {
            int letterIndex = shot.getLetterIndex().ordinal();
            int digitIndex = shot.getDigitIndex().getDigit();
            if (fleetLocations[letterIndex][digitIndex] == SHIP) {
                battleController.addMessage("HIT!");
                fleetLocations[letterIndex][digitIndex] = DECK;
                return checkForObliteration(letterIndex, digitIndex);
            } else if (fleetLocations[letterIndex][digitIndex] == DECK) {
                battleController.addMessage("But you've shoot this area already...");
                return ShotResult.MISS;
            } else {
                battleController.addMessage("MISS!");
                fleetLocations[letterIndex][digitIndex] = MISS;
                return ShotResult.MISS;
            }
        } finally {
            updateShipsDislocations();
        }
    }

    private ShotResult checkForObliteration(int letterIndex, int digitIndex) {
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        List<Deck> ship = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            int l1 = letterIndex - i, l2 = letterIndex + i, d1 = digitIndex - i, d2 = digitIndex + i;
            if (l1 >= 0 && !up) {
                char c = fleetLocations[l1][digitIndex];
                if (c == SHIP) return ShotResult.HIT;
                if (c == DECK) ship.add(new Deck(l1, digitIndex));
                if (c == '\u0000' || c == MISS) up = true;
            }
            if (l2 < SIZE && !down) {
                char c = fleetLocations[l2][digitIndex];
                if (c == SHIP) return ShotResult.HIT;
                if (c == DECK) ship.add(new Deck(l2, digitIndex));
                if (c == '\u0000' || c == MISS) down = true;
            }
            if (d1 >= 0 && !left) {
                char c = fleetLocations[letterIndex][d1];
                if (c == SHIP) return ShotResult.HIT;
                if (c == DECK) ship.add(new Deck(letterIndex, d1));
                if (c == '\u0000' || c == MISS) left = true;
            }
            if (d2 < SIZE && !right) {
                char c = fleetLocations[letterIndex][d2];
                if (c == SHIP) return ShotResult.HIT;
                if (c == DECK) ship.add(new Deck(letterIndex, d2));
                if (c == '\u0000' || c == MISS) right = true;
            }
        }
        ship.add(new Deck(letterIndex, digitIndex));
        destroyShip(ship.size());
        markDeadArea(ship);
        return ShotResult.OBLITERATION;
    }

    private void markDeadArea(List<Deck> ship) {
        for (Deck deck : ship) {
            for (int i = Math.max(deck.getLetterIndex() - 1, 0); i <= Math.min(deck.getLetterIndex() + 1, 9); i++) {
                for (int j = Math.max(deck.getDigitIndex() - 1, 0); j <= Math.min(deck.getDigitIndex() + 1, 9); j++) {
                    if (fleetLocations[i][j] != DECK) fleetLocations[i][j] = MISS;
                }
            }
        }
    }

    private void destroyShip(int deckCount) {
        Integer e = fleet.get(deckCount) - 1;
        if (e <= 0) {
            fleet.remove(deckCount);
        } else {
            fleet.put(deckCount, e);
        }
        battleController.addMessage(deckCount + "-DECK SHIP DESTROYED!");
    }

    public abstract void placeShips();

    protected abstract Shot makeTheShot();

    protected abstract void updateShipsDislocations();
}
