package net.medrag.minigames.seabattle.placers;

import net.medrag.minigames.seabattle.ShipDislocationException;
import net.medrag.minigames.seabattle.player.Player;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public class VerticalShipPlacer implements ShipPlacer {

    public final static ShipPlacer VERTICAL_PLACER = new VerticalShipPlacer();

    @Override
    public void placeShip(int letterIndex, int digitIndex, int size, char[][] sea) throws ShipDislocationException {
        //  validation on other ships presence
        for (int i = letterIndex - 1; i < letterIndex + size + 1; i++) {
            if (i < 0 || i > 9) continue;
            int x1 = digitIndex - 1;
            int x2 = digitIndex + 1;
            if (sea[i][digitIndex] == Player.SHIP) throw new ShipDislocationException();

            if (x1 >= 0) {
                if (sea[i][x1] == Player.SHIP) throw new ShipDislocationException();
            }
            if (x2 <= 9) {
                if (sea[i][x2] == Player.SHIP) throw new ShipDislocationException();
            }
        }

        for (int i = letterIndex; i < letterIndex + size; i++) {
            sea[i][digitIndex] = Player.SHIP;
        }
    }
}
