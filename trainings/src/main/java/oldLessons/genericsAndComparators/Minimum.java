package oldLessons.genericsAndComparators;

public interface Minimum<T extends Comparable<T>> {
    T min();
}
