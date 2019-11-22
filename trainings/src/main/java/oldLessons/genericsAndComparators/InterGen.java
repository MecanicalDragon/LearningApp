package oldLessons.genericsAndComparators;

public class InterGen<T extends Comparable<T>> implements Minimum<T> {
    T[] objects;

    public InterGen(T[] objects) {
        this.objects = objects;
    }
    @Override
    public T min(){
        T m = objects[0];
        for ( T t : objects){
            if (t.compareTo(m)<0) m = t;
        }
        return m;
    }
}

// Example of using of generic interface.
