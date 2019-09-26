import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@author} Stanislav Tretyakov
 * 02.09.2019
 */
public class Streams {

    private static List<Man> list;

    static {
        list = new ArrayList<>();
        list.add(Man.builder().age(23).name("Vasiliy").build());
        list.add(Man.builder().age(15).name("Herbert").build());
        list.add(Man.builder().age(65).name("Alex").build());
        list.add(Man.builder().age(6).name("Stas").build());
        list.add(Man.builder().age(98).name("Mark").build());
        list.add(Man.builder().age(115).name("Herbert").build());
        list.add(Man.builder().age(30).name("John").build());
        list.add(Man.builder().age(30).name("Stas").build());
        list.add(Man.builder().age(46).name("Vasiliy").build());
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(list.stream()
                .filter(m -> m.getName().equals("Stas"))
                .toArray()));
        System.out.println(list.stream()
                .skip(3)
                .map(Man::getName)
                .collect(Collectors.toList()));
        System.out.println(list.stream()
                .map(Man::getName)
                .distinct()
                .collect(Collectors.toList()));
//        Returns the same, not modified stream
        System.out.println(list.stream()
                .map(Man::getAge)
                .peek(System.out::print)
                .collect(Collectors.toList()));
        System.out.println(list.stream()
                .limit(5)
                .map(Man::getName)
                .collect(Collectors.toList()));
        System.out.println(Arrays.toString(list.stream()
                .sorted((c1, c2) -> c1.getAge() - c2.getAge())
                .sorted(Comparator.comparingInt(Man::getAge))
                .mapToInt(Man::getAge)
                .toArray()));
        System.out.println(Arrays.toString(list.stream()
                .flatMap(man -> Arrays.stream(man.getName().split("e")))
                .toArray(String[]::new)));
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Man {
    int age;
    String name;
}
