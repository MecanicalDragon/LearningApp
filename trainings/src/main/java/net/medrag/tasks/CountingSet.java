package net.medrag.tasks;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Необходимо реализовать структуру данных с тремя операциями:
 * add(x) - добавить элемент
 * getUnique() - вернуть какой-то элемент, который был добавлен в структуру только один раз (или null, если таких элементов нет).
 * delete(x) - удалить ровно одно вхождение элемента x.
 * Если элемента равного x в структуре нет, то в структуре ничего не меняется.
 * <p>
 * Дополнительно: модифицировать структуру для многопоточного использования. Для этой цели мы можем использовать встроенный
 * в ConcurrentHashMap StripedLock.
 *
 * @author Stanislav Tretiakov
 * 02.05.2023
 */
public interface CountingSet<T>{
    void add(T element);
    void delete(T element);
    T getUnique();
}

class SimpleCountingSet<T> implements CountingSet<T> {
    private final Map<T, Counter> innerStorage = new HashMap<>();
    private final Set<T> unique = new HashSet<>();

    private T cachedUnique;

    public void add(T element) {
        var counter = innerStorage.get(element);
        if (counter == null) {
            innerStorage.put(element, new Counter());
            addUnique(element);
        } else {
            counter.inc();
            removeUnique(element);
        }
    }

    public void delete(T element) {
        var counter = innerStorage.get(element);
        if (counter != null) {
            if (counter.isOne()) {
                innerStorage.remove(element);
                removeUnique(element);
            } else {
                counter.dec();
                if (counter.isOne()) {
                    addUnique(element);
                }
            }
        }
    }

    public T getUnique() {
        if (cachedUnique != null) {
            return cachedUnique;
        }
        if (unique.isEmpty()) {
            return null;
        }
        cachedUnique = unique.iterator().next();
        return cachedUnique;
    }

    private void removeUnique(T element) {
        unique.remove(element);
        if (Objects.equals(element, cachedUnique)) {
            cachedUnique = null;
        }
    }

    private void addUnique(T element) {
        unique.add(element);
        cachedUnique = element;
    }

    private static class Counter {
        int count = 1;

        private void inc() {
            ++count;
        }

        private void dec() {
            --count;
        }

        private boolean isOne() {
            return count == 1;
        }
    }
}

class ConcurrentCountingSet<T> implements CountingSet<T> {
    private final ConcurrentMap<T, Integer> innerStorage = new ConcurrentHashMap<>();
    private final ConcurrentMap<T, T> unique = new ConcurrentHashMap<>();
    private final AtomicReference<T> cachedUnique = new AtomicReference<>(null);

    public void add(T element) {
        innerStorage.compute(element, (key, counter) -> {
            if (counter == null) {
                addUnique(element);
                return 1;
            } else {
                if (counter == 1) {
                    removeUnique(element);
                }
                return ++counter;
            }
        });
    }

    public void delete(T element) {
        innerStorage.computeIfPresent(element, (key, counter) -> {
            if (counter == 1) {
                removeUnique(element);
                return null;
            } else {
                if (counter == 2) {
                    addUnique(element);
                }
                return --counter;
            }
        });
    }

    public T getUnique() {
        var temp = cachedUnique.get();
        if (temp != null) {
            return temp;
        }
        if (unique.isEmpty()) {
            return null;
        }
        final var iterator = unique.keySet().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    private void addUnique(T element) {
        unique.compute(element, (key, value) -> {
            cachedUnique.set(element);
            return element;
        });
    }

    private void removeUnique(T element) {
        unique.compute(element, (key, value) -> {
            if (Objects.equals(element, cachedUnique.getAcquire())) {
                cachedUnique.setRelease(null);
            }
            return null;
        });
    }
}
