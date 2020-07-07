package net.medrag.minigames.seabattle.battle;

import lombok.Getter;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
public enum DigitIndex {
    $0(0), $1(1), $2(2), $3(3), $4(4), $5(5), $6(6), $7(7), $8(8), $9(9);
    public static final String PREFIX = "$";

    @Getter
    private int digit;

    DigitIndex(int digit) {
        this.digit = digit;
    }
}
