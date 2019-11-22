package oldLessons.stack;

public interface BiOperator<T> {
    T apply( T reduced, T reducible);
}
