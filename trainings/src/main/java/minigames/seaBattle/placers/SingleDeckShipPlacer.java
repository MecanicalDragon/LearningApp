package minigames.seaBattle.placers;

import minigames.seaBattle.ShipDislocationException;

import static minigames.seaBattle.SeaBattle.SHIP;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public class SingleDeckShipPlacer implements ShipPlacer {
    @Override
    public void placeShip(int letterIndex, int digitIndex, int size, char[][] sea) throws ShipDislocationException {
        //  validation on other ships presence
        for (int i = letterIndex - 1; i <= letterIndex + 1; i++) {
            if (i < 0 || i > 9) continue;
            int x1 = digitIndex - 1;
            int x2 = digitIndex + 1;
            if (sea[i][digitIndex] == SHIP) throw new ShipDislocationException();

            if (x1 >= 0) {
                if (sea[i][x1] == SHIP) throw new ShipDislocationException();
            }
            if (x2 <= 9) {
                if (sea[i][x2] == SHIP) throw new ShipDislocationException();
            }
        }

        sea[letterIndex][digitIndex] = SHIP;
    }
}
