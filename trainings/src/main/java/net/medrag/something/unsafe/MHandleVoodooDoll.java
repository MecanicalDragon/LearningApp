package net.medrag.something.unsafe;

import lombok.ToString;

/**
 * @author Stanislav Tretyakov
 * 31.10.2021
 */
@ToString
public class MHandleVoodooDoll {
    private int privateInt = 1;
    int defaultInt = 50;
    public int publicInt = 999;

    private int getPrivate() {
        return privateInt;
    }

    void addToDefault(int add) {
        defaultInt += add;
    }

    public int getPublic(int i1, Integer i2) {
        return publicInt + i1 + i2;
    }
}
