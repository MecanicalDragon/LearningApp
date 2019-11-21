import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * {@author} Stanislav Tretyakov
 * 02.09.2019
 */
public class Streams {

    private static List<Man> list;

    static {
        list = new ArrayList<>();
        list.add(Man.builder().age(23).name("Vasiliy").skills(new String[]{"Java", "Kotlin", "MySQL"}).build());
        list.add(Man.builder().age(15).name("Herbert").skills(new String[]{"React", "Angular", "RabbitMQ"}).build());
        list.add(Man.builder().age(65).name("Alex").skills(new String[]{"Oracle", "SQL", "Kafka"}).build());
        list.add(Man.builder().age(6).name("Stas").skills(new String[]{"Android", "Kotlin", "Docker"}).build());
        list.add(Man.builder().age(98).name("Mark").skills(new String[]{"Java", "SQL", "Kubernetes", "Kafka"}).build());
        list.add(Man.builder().age(115).name("Herbert").skills(new String[]{"React", "C++", "React"}).build());
        list.add(Man.builder().age(30).name("John").skills(new String[]{"Oracle", "Kotlin", "Swagger"}).build());
        list.add(Man.builder().age(30).name("Stas").skills(new String[]{"Tomcat", "Java", "Docker", "SQL"}).build());
        list.add(Man.builder().age(46).name("Vasiliy").skills(new String[]{"WebSphere", "ARS", "XML"}).build());
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(list.stream()
                .filter(m -> m.getName().equals("Stas"))
                .toArray()));   //  filters list according request

        System.out.println(list.stream()
                .skip(3)
                .map(Man::getName)
                .collect(Collectors.toList())); //  skips amount of elements

        System.out.println(list.stream()
                .map(Man::getName)
                .distinct()
                .collect(Collectors.toList())); //  returns stream with distinct values

        System.out.println(list.stream()
                .map(Man::getAge)
                .peek(System.out::print)
                .collect(Collectors.toList())); //  does smth and returns the same, not modified stream

        System.out.println(list.stream()
                .limit(5)
                .map(Man::getName)
                .collect(Collectors.toList())); //  limits amount of elements to procedure

        System.out.println(Arrays.toString(list.stream()
                .sorted((c1, c2) -> c1.getAge() - c2.getAge())
                .sorted(Comparator.comparingInt(Man::getAge))
                .mapToInt(Man::getAge)
                .toArray()));   //  takes Comparable and sorts according it

        System.out.println(Arrays.toString(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .distinct()
                .toArray(String[]::new)));  //  unwrap objects in stream in inner stream

        System.out.println(list.stream()
                .map(Man::getAge)
                .reduce(Integer::sum)); //  .reduce((x, y) -> x+y));   //  reduce stream into one value

        System.out.println(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .reduce("Reduced:", String::concat));   // .reduce((x, y) -> x.concat(y))   //  reduce stream into one value with identity value

        System.out.println(list.stream()
                .filter(man -> man.getName().equals("Stas"))
                .map(Man::getAge)
                .reduce(1000, (x, y) -> x + y, (x, y) -> x + y));    //  normal result. IDK, for what is consumer here

        System.out.println(list.parallelStream()
                .filter(man -> man.getName().equals("Stas"))
                .map(Man::getAge)
                .reduce(1000, (x, y) -> x + y, (x, y) -> x + y));    //  some weird result. As far as I understand,
        // consumer function is needed only in parallelStream, but it's behaviour strange enough. Let's not to use it.


//        GROUPING BY


        System.out.println(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .distinct()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll).toString());   //  grouping example with lambdas.
        //  here you should specify a class and it's 3 functions for new, addOne and addAll functions

        System.out.println(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .distinct()
                .collect(Collectors.groupingBy(String::length)));   //  Simple Grouping by a Single Column

        System.out.println(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .distinct()
                .collect(Collectors.groupingBy(skill -> new Character(skill.charAt(0)))));   //  Grouping by with a Complex Map Key Type

        System.out.println(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .distinct()
                .collect(Collectors.groupingBy(skill -> skill.charAt(0), toSet())));   //  Modifying the Returned Map Value Type

        System.out.println(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .distinct()
                .collect(Collectors.groupingBy(String::length, groupingBy(s -> s.charAt(0)))));   //  Grouping By Multiple Fields

        System.out.println(list.stream()
                .flatMap(man -> Arrays.stream(man.getSkills()))
                .distinct()
                .collect(Collectors.groupingBy(skill -> skill.charAt(0), summarizingInt(String::length))));   //  Getting the Statistics from Grouped Results




    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Man {
    int age;
    String name;
    String[] skills;
}
