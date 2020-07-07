package net.medrag.minigames.seabattle.battle;

import lombok.Data;
import lombok.NonNull;

/**
 * @author Stanislav Tretyakov
 * 06.07.2020
 */
@Data
public class Shot {
    private final @NonNull LetterIndex letterIndex;
    private final @NonNull DigitIndex digitIndex;

    public String toString(){
        return "" + letterIndex + digitIndex.getDigit();
    }
}
