package oldLessons.stack;

public interface PriorityQueueable <K extends Comparable<K>, T> {

    T getMax();

    void add(K key, T value);
}
