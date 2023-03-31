package net.medrag.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * @author Stanislav Tretyakov
 * 09.01.2022
 */
public class PredicateIterator<E> implements Iterator<E> {

    private final Iterator<E> iterator;
    private final Predicate<E> predicate;
    private E next;
    private boolean nextComputed = false;

    public PredicateIterator(Iterable<E> innerCollection, Predicate<E> predicate) {
        this.iterator = innerCollection.iterator();
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (nextComputed) {
            return this.next != null;
        }
        computeNext();
        return this.next != null;
    }

    @Override
    public E next() {
        if (!nextComputed) {
            computeNext();
        }
        if (this.next == null) {
            throw new NoSuchElementException();
        }
        this.nextComputed = false;
        return this.next;
    }

    private void computeNext() {
        this.nextComputed = true;
        while (iterator.hasNext()) {
            E e = iterator.next();
            if (predicate.test(e)) {
                this.next = e;
                return;
            }
        }
        this.next = null;
    }
}
