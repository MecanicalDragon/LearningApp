package net.medrag.fastxsd.pom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Threshold {

//    static Threshold T1 = new Threshold(10, "#0000FF");
//    static Threshold T2 = new Threshold(20, "#FF0000");
//    static Threshold T3 = new Threshold(50, "#FFCC00");
//    static Threshold T4 = new Threshold(70, "#008000");
//    T1(10, "#0000FF"),
//    T2(20, "#FF0000"),
//    T3(50, "#FFCC00"),
//    T4(70, "#008000");

    int value;
    String color;
}
