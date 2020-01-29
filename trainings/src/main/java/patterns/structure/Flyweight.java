package patterns.structure;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * Allows to save memory, storing big similar data of different objects in the same place
 * <p>
 * + Saves memory
 * <p>
 * - increases processor calculating time
 * - makes program code more difficult
 * <p>
 * IMPLEMENTATION
 * <p>
 * Разделите поля класса, который станет легковесом, на две части:
 * <p>
 * внутреннее состояние: значения этих полей одинаковы для большого числа объектов;
 * внешнее состояние (контекст): значения полей уникальны для каждого объекта.
 * Оставьте поля внутреннего состояния в классе, но убедитесь, что их значения неизменяемы. Эти поля должны
 * инициализироваться только через конструктор.
 * <p>
 * Превратите поля внешнего состояния в параметры методов, где эти поля использовались. Затем удалите поля из класса.
 * <p>
 * Создайте фабрику, которая будет кешировать и повторно отдавать уже созданные объекты. Клиент должен запрашивать из
 * этой фабрики легковеса с определённым внутренним состоянием, а не создавать его напрямую.
 * <p>
 * Клиент должен хранить или вычислять значения внешнего состояния (контекст) и передавать его в методы объекта легковеса.
 */
public class Flyweight {
    public static void main(String[] args) {

        // Consider it as high weight objects
        Object pine = new Object();
        Object oak = new Object();
        Object birch = new Object();
        Object fir = new Object();

        // Consider it as high weight objects too
        Object green = new Object();
        Object red = new Object();
        Object yellow = new Object();

        Object[] textures = {green, red, yellow};
        Object[] models = {pine, oak, birch, fir};

        List<TreeContext> forest = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Object texture = textures[(int) (Math.random() * textures.length)];
            Object model = models[(int) (Math.random() * models.length)];
            int x = (int) (Math.random() * 100_000);
            int y = (int) (Math.random() * 100_000);
            TreeContext tree = new TreeContext(TreeFactory.getModel(model, texture), x, y);
            forest.add(tree);
        }
        System.out.println(forest.size());
        System.out.println(TreeFactory.getTreesSize());
    }
}

/**
 * Not oblivious, but this is the FLYWEIGHT object, and it must be immutable
 */
final class TreeModel {
    private final Object model;
    private final Object texture;

    TreeModel(Object model, Object texture) {
        this.model = model;
        this.texture = texture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeModel)) return false;

        TreeModel treeModel = (TreeModel) o;

        if (!Objects.equals(model, treeModel.model)) return false;
        return Objects.equals(texture, treeModel.texture);
    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (texture != null ? texture.hashCode() : 0);
        return result;
    }
}

class TreeContext {
    private TreeModel model;
    private int coordinateX;
    private int coordinateY;

    TreeContext(TreeModel model, int coordinateX, int coordinateY) {
        this.model = model;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
}

class TreeFactory {
    private final static List<TreeModel> TREES = new ArrayList<>();

    static int getTreesSize() {
        return TREES.size();
    }

    static TreeModel getModel(Object model, Object texture) {
        WeakReference<TreeModel> newTree = new WeakReference<>(new TreeModel(model, texture));
        int index = TREES.indexOf(newTree.get());
        if (index >= 0) {
            newTree.clear();
            newTree = null;
            return TREES.get(index);
        } else {
            TreeModel tree = newTree.get();
            TREES.add(tree);
            newTree.clear();
            newTree = null;
            return tree;
        }
    }
}
