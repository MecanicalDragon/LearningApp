package patterns.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Groups a lot of objects in a single tree structure.
 * <p>
 * Useful when:
 * > you need to implement tree structure of objects
 * > client have to interpret simple and complex objects with the same way
 * <p>
 * + Simplifies the architecture
 * + Makes adding of new components easier
 * <p>
 * - makes classes design more common
 * <p>
 * IMPLEMENTATION
 * <p>
 * Убедитесь, что вашу бизнес-логику можно представить как древовидную структуру. Попытайтесь разбить её на простые
 * компоненты и контейнеры. Помните, что контейнеры могут содержать как простые компоненты, так и другие вложенные контейнеры.
 * <p>
 * Создайте общий интерфейс компонентов, который объединит операции контейнеров и простых компонентов дерева. Интерфейс
 * будет удачным, если вы сможете использовать его, чтобы взаимозаменять простые и составные компоненты без потери смысла.
 * <p>
 * Создайте класс компонентов-листьев, не имеющих дальнейших ответвлений. Имейте в виду, что программа может содержать
 * несколько таких классов.
 * <p>
 * Создайте класс компонентов-контейнеров и добавьте в него массив для хранения ссылок на вложенные компоненты. Этот
 * массив должен быть способен содержать как простые, так и составные компоненты, поэтому убедитесь, что он объявлен
 * с типом интерфейса компонентов.
 * <p>
 * Реализуйте в контейнере методы интерфейса компонентов, помня о том, что контейнеры должны делегировать основную
 * работу своим дочерним компонентам.
 * <p>
 * Добавьте операции добавления и удаления дочерних компонентов в класс контейнеров.
 * <p>
 * Имейте в виду, что методы добавления/удаления дочерних компонентов можно поместить и в интерфейс компонентов. Да,
 * это нарушит принцип разделения интерфейса, так как реализации методов будут пустыми в компонентах-листьях. Но зато
 * все компоненты дерева станут действительно одинаковыми для клиента.
 */
public class Composite {
    public static void main(String[] args) {
        Order phone = new Order(253);
        Order tv = new Order(560);
        Order player = new Order(116);
        Box clothes = new Box(new Order(265), new Order(860), new Order(23));
        Box total = new Box(clothes, phone, tv, player);
        System.out.println("Total price: " + total.getPrice());
    }
}

interface Good {
    int getPrice();
}

class Order implements Good {
    private int price;

    public Order(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return this.price;
    }
}

class Box implements Good {
    private List<Good> content = new ArrayList<>();

    Box(Good... goods) {
        Collections.addAll(content, goods);
    }

    @Override
    public int getPrice() {
        return content.stream().map(Good::getPrice).reduce(0, Integer::sum);
    }
}
