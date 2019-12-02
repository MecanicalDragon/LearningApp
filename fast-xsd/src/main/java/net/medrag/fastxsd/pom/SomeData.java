package net.medrag.fastxsd.pom;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Data
public class SomeData {
    private int id;

    private String alias;
    private int number;
    private String position;
    private String location;

    private Threshold t1;
    private Threshold t2;
    private Threshold t3;
    private Threshold t4;
}
