package net.medrag.tasks;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * @author Stanislav Tretyakov
 * 09.01.2022
 */
public class PredicateIterator<E> implements Iterator<E> {

    private final Iterator<E> iterator;
    private final Predicate<E> predicate;
    private E next;

    public PredicateIterator(Iterable<E> innerCollection, Predicate<E> predicate) {
        this.iterator = innerCollection.iterator();
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        while (iterator.hasNext()) {
            E e = iterator.next();
            if (predicate.test(e)) {
                this.next = e;
                return true;
            }
        }
        return false;
    }

    @Override
    public E next() {
        return next;
    }
}
