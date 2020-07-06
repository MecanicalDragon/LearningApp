package minigames.seaBattle;

import lombok.SneakyThrows;
import minigames.seaBattle.placers.HorizontalShipPlacer;
import minigames.seaBattle.placers.ShipPlacer;
import minigames.seaBattle.placers.SingleDeckShipPlacer;
import minigames.seaBattle.placers.VerticalShipPlacer;

import java.util.*;

import static minigames.seaBattle.Sea.*;

/**
 * @author Stanislav Tretyakov
 * 04.07.2020
 */
public class SeaBattle {

    public final static char SHIP = 'E';
    private final static int START_LETTER = 'A';

    private final static String SHIP_REGEX = "^[A-J][0-9]-[A-J][0-9]$";
    private final static String SHOT_REGEX = "^[A-J][0-9]$";

    private final static Scanner SCANNER = new Scanner(System.in);
    public final static Map<Integer, Integer> DEFAULT_FLEET = Map.of(4, 1, 3, 2, 2, 3, 1, 4);

    public final static ShipPlacer SINGLE_DECK_PLACER = new SingleDeckShipPlacer();
    public final static ShipPlacer VERTICAL_PLACER = new VerticalShipPlacer();
    public final static ShipPlacer HORIZONTAL_PLACER = new HorizontalShipPlacer();

    private final Map<Integer, Integer> fleet = new HashMap<>(DEFAULT_FLEET);
    private final Map<Integer, Integer> yourFleet = new HashMap<>(DEFAULT_FLEET);
    private final Map<Integer, Integer> foesFleet = new HashMap<>(DEFAULT_FLEET);

    private final char[][] yourFleetLocations = new char[10][10];
    private final char[][] foesFleetLocations = new char[10][10];

    private final String[] yourSea = new String[10];
    private final String[] foesSea = new String[10];

    private boolean randomMode = false;

    private SeaBattle() {
        Arrays.fill(yourSea, ROW);
        Arrays.fill(foesSea, ROW);
        initFleetRows(yourSea);
        initFleetRows(foesSea);
    }

    private static void initFleetRows(String[] fleet) {
        for (int i = 0; i < fleet.length; i++) {
            fleet[i] = fleet[i].replace(LETTER, (char) (i + START_LETTER));
        }
    }

    public static void main(String[] args) throws Exception {
        SeaBattle seaBattle = new SeaBattle();
        seaBattle.drawTheGrid();
        seaBattle.chooseDislocationMode();
        seaBattle.placeShips();
        seaBattle.fight();
    }

    private void fight() {
        System.out.println("LET THE BATTLE BEGIN!");
        while (!yourFleet.isEmpty() && !foesFleet.isEmpty()) {

        }
    }

    private void chooseDislocationMode() {
        System.out.println("Do you want to place your ships randomly?");
        System.out.println("(Input anything, if you do)");
        if (!SCANNER.nextLine().isEmpty()) randomMode = true;
    }

    private void placeShipsRandomly(char[][] fleetLoc) {
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
                        placer.placeShip(x, y, i, fleetLoc);
                        placed = true;
                    } catch (ShipDislocationException ignored) {
                    }
                }
            }
        }
    }

    private void updateShipsDislocations() {
        for (int j = 0; j < yourFleetLocations.length; j++) {
            for (int i = 0; i < yourFleetLocations[j].length; i++) {
                if (yourFleetLocations[j][i] != '\u0000') {
                    yourSea[j] = new StringBuilder(yourSea[j]).replace(LOCATIONS[i], LOCATIONS[i] + 1, String.valueOf(SHIP)).toString();
                }
            }
        }
    }

    private void placeShips() {
        placeShipsRandomly(foesFleetLocations);
        if (randomMode) {
            placeShipsRandomly(yourFleetLocations);
            updateShipsDislocations();
            drawTheGrid();
            return;
        }
        while (!fleet.isEmpty()) {
            System.out.println("");
            System.out.println("PLACE YOUR SHIPS:");
            System.out.println(fleet);
            String ship = SCANNER.nextLine().toUpperCase();
            try {
                if (!ship.matches(SHIP_REGEX) && !ship.matches(SHOT_REGEX)) {
                    throw new Exception();
                }
                boolean oneDeckShip = ship.length() == 2;
                char a = ship.charAt(0);
                char b = oneDeckShip ? ship.charAt(0) : ship.charAt(3);
                int x = Integer.parseInt(String.valueOf(ship.charAt(1)));
                int y = Integer.parseInt(String.valueOf(oneDeckShip ? ship.charAt(1) : ship.charAt(4)));

                ShipPlacer placer = oneDeckShip ? SINGLE_DECK_PLACER : a == b ? HORIZONTAL_PLACER : VERTICAL_PLACER;

                int letterIndex = Math.min(a, b) - START_LETTER;
                int digitIndex = Math.min(x, y);
                int size = a == b ? Math.abs(x - y) + 1 : Math.abs(a - b) + 1;

                Integer e = fleet.get(size);

                if (e != null && e > 0) {
                    placer.placeShip(letterIndex, digitIndex, size, yourFleetLocations);
                    reduceAvailableShips(size);
                    updateShipsDislocations();
                    drawTheGrid();
                } else System.out.println("There're no more ships of this type in yur stock.");

            } catch (ShipDislocationException e) {
                System.out.println("You can't place your ship this way!");
            } catch (Exception e) {
                System.out.println("Input ship locations according the following template: 'A0-D0', 'B2-B4'. Input ships one by one.");
//                e.printStackTrace();
            }
        }
    }

    private void reduceAvailableShips(int size) {
        Integer e = fleet.get(size) - 1;
        if (e <= 0) {
            fleet.remove(size);
        } else {
            fleet.put(size, e);
        }
    }

    @SneakyThrows
    private void drawTheGrid() {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println();
        System.out.println(HEAD1 + LAND + HEAD2);
        System.out.println(SEP + LAND + SEP);

        for (int i = 0; i < SIZE; i++) {
            System.out.println(yourSea[i] + LAND + foesSea[i]);
            System.out.println(SEP + LAND + SEP);
        }
    }
}
