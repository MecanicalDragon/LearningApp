package oldLessons.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        PriorityQTest();
        StrategyPattern();
        stack();

    }

    private static void stack() {
        Stack<String> stack = new GenericStack<>(1);
        try {
            stack.push("ololo");
        } catch (StackException e) {
            e.printStackTrace();
        }
        try {
            stack.push("azaza");
        } catch (StackException e) {
            e.printStackTrace();
        }
    }

    private static void StrategyPattern() {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 2,5,3,7,1,8,4,5,3,9);

        List resultList = filter(list, i -> i % 2 == 0);
        System.out.println(resultList);

        List<String>strings = new ArrayList<>();
        Collections.addAll(strings, "aaa","bbb","ccc","ddd","eee");

        String reducedString = reduce(strings, "", String::concat);
        System.out.println(reducedString);

        List convertedList = convert(list, Integer::floatValue);

        System.out.println(convertedList);
    }

    static <S, R extends S,T extends S> List<R> convert(List<T> convertable, Operator<R, T> operator){
        List<R>convertedList = new ArrayList<>();
        for (T t : convertable){
            convertedList.add(operator.apply(t));
        }
        return convertedList;
    }

    static <T> List<T> filter(List<T>list, Predicate<T> predicate){
        List<T>filteredList = new ArrayList<>();
        for(T t : list){
            if (predicate.test(t)) filteredList.add(t);
        }
        return filteredList;
    }

    static <T> T reduce(List<T>list,T reducedList, BiOperator<T> biOperator){
        for (T t : list){
            reducedList = biOperator.apply(reducedList, t);
        }
        return reducedList;
    }

    private static void PriorityQTest() {
        PriorityQueue<Integer, String> x = new PriorityQueue<>();
        x.add(14,"c");
        x.add(35,"f");
        x.add(86,"i");
        x.add(31,"e");
        x.add(47,"h");
        x.add(13,"a");
        x.add(24,"d");
        x.add(93,"j");
        x.add(44,"g");
        x.add(13,"b");
        System.out.println(x.queue.size());

        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
        System.out.println(x.getMax());
    }
}
