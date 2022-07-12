import supportClasses.Man;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * {@author} Stanislav Tretyakov
 * 02.09.2019
 */
public class Streams {

    private static class Data{

        static int actions = 0;
        private int value;

        public Data(int value) {
            this.value = value;
        }

        public int getValue() {
            actions++;
            return value;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "value=" + value +
                    '}';
        }
    }

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

    /**
     * This part of code just instantiates, that stream uses collection's state at the moment of terminal method.
     */
    private static void collection(){
        System.out.println("Start collection");
        List<String>list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        Stream<String> stream = list.stream();
        list.remove(1);
        list.add("fourth");
        String reduce = stream.reduce("", String::concat);
        System.out.println(reduce);
        System.out.println("End collection");
    }

    public static void main(String[] args) {

//        collection();

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

        System.out.println(list.stream()
                .filter(man -> man.getName().equals("Stas") ||  man.getName().equals("Vasiliy"))
                .reduce(0, (i, m) -> i + m.getAge(), Integer::sum) );    //  reducing men to integer example


//        GROUPING BY


        final var x = list.stream()
            .flatMap(man -> Arrays.stream(man.getSkills()))
            .distinct()
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll).toString();
        System.out.println(x);   //  grouping example with lambdas.
        //  here you should specify a class and it's 3 functions for new, addOne and addAll functions

        final var collect = list.stream()
            .flatMap(man -> Arrays.stream(man.getSkills()))
            .distinct()
            .collect(groupingBy(String::length));
        System.out.println(collect);   //  Simple Grouping by a Single Column

        final var collect1 = list.stream()
            .flatMap(man -> Arrays.stream(man.getSkills()))
            .distinct()
            .collect(groupingBy(skill -> skill.charAt(0)));
        System.out.println(collect1);   //  Grouping by with a Complex Map Key Type

        final var collect2 = list.stream()
            .flatMap(man -> Arrays.stream(man.getSkills()))
            .distinct()
            .collect(groupingBy(skill -> skill.charAt(0), toSet()));
        System.out.println(collect2);   //  Modifying the Returned Map Value Type

        final var collect3 = list.stream()
            .flatMap(man -> Arrays.stream(man.getSkills()))
            .distinct()
            .collect(groupingBy(String::length, groupingBy(s -> s.charAt(0))));
        System.out.println(collect3);   //  Grouping By Multiple Fields

        final var collect4 = list.stream()
            .flatMap(man -> Arrays.stream(man.getSkills()))
            .distinct()
            .collect(groupingBy(skill -> skill.charAt(0), summarizingInt(String::length)));
        System.out.println(collect4);   //  Getting the Statistics from Grouped Results

        final var concat = Stream.of("aaa", "bbb", "ccc").collect(Collectors.joining(", "));
        System.out.println(concat);

        // ALSO

        System.out.println("");
        System.out.println("_____");
        System.out.println("");

        int size = 10000;
        var random = new Random(31);
        List<Data> data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add(new Data(random.nextInt(100000)));
        }
        System.out.println("Data list size: " + data.size());
        final Optional<Data> first = data.stream().sorted(Comparator.comparingInt(Data::getValue)).findFirst();
        System.out.println("'findFirst()' result: " + first + " Actions: " + Data.actions);
        Data.actions = 0;
        final Optional<Data> min = data.stream().min(Comparator.comparingInt(Data::getValue));
        System.out.println("'min()' result: " + min + " Actions: " + Data.actions);

    }
}
