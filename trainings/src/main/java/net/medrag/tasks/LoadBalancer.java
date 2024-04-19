package net.medrag.tasks;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Реализовать простенький лоад балансер.
 * Он должен хранить не более 10 сервисов, и никаких дубликатов.
 * Должен уметь добавлять и удалять сервисы.
 * Реализовать рандомный выбор сервиса и раунд-робин.
 *
 * @author Stanislav Tretiakov
 * 15.04.2024
 */
public interface LoadBalancer<T> {
    boolean add(T service);

    boolean remove(T service);

    T random();

    T next();
}

class ReadWriteLockLoadBalancer<T> implements LoadBalancer<T> {
    private static final int CAP = 10;

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final List<T> services = new CopyOnWriteArrayList<>();
    private final Map<T, T> indices = new ConcurrentHashMap<>(16);
    private final AtomicInteger nextIndex = new AtomicInteger(0);

    @Override
    public boolean add(T service) {
        if (services.size() >= CAP) {
            return false;
        }
        lock.writeLock().lock();
        try {
            if (services.size() >= CAP || indices.containsKey(service)) {
                return false;
            } else {
                services.add(service);
                indices.put(service, service);
                return true;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean remove(T service) {
        if (services.isEmpty()) {
            return false;
        }
        lock.writeLock().lock();
        try {
            if (services.isEmpty() || indices.remove(service) == null) {
                return false;
            } else {
                services.remove(service);
                return true;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public T random() {
        lock.readLock().lock();
        try {
            return services.get(ThreadLocalRandom.current().nextInt(services.size()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public T next() {
        lock.readLock().lock();
        try {
            return services.get(nextIndex.getAndIncrement() % services.size());
        } finally {
            lock.readLock().unlock();
        }
    }
}

class WriteLockLoadBalancer<T> implements LoadBalancer<T> {
    private static final int CAP = 10;

    private static class Holder<T> {

        private final T current;
        private Holder<T> next;
        private Holder<T> prev;

        public Holder(T current) {
            this.current = current;
        }
    }

    private final AtomicReference<Holder<T>> next = new AtomicReference<>();
    private final Lock writeLock = new ReentrantLock();
    private final Map<T, Holder<T>> indices = new ConcurrentHashMap<>(16);
    private final List<T> services = new CopyOnWriteArrayList<>();

    @Override
    public boolean add(T service) {
        if (indices.size() >= CAP) {
            return false;
        }
        writeLock.lock();
        try {
            if (indices.size() >= CAP || indices.containsKey(service)) {
                return false;
            } else {
                final Holder<T> newHolder = new Holder<>(service);
                if (indices.isEmpty()) {
                    next.set(newHolder);
                } else {
                    Holder<T> nextHolder = next.get();
                    newHolder.next = nextHolder;
                    Holder<T> prevHolder = nextHolder.prev;
                    if (prevHolder == null) {
                        newHolder.prev = nextHolder;
                        nextHolder.prev = newHolder;
                        nextHolder.next = newHolder;
                    } else {
                        newHolder.prev = prevHolder;
                        prevHolder.next = newHolder;
                    }
                }
                services.add(service);
                indices.put(service, newHolder);
                return true;
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean remove(T service) {
        if (indices.isEmpty()) {
            return false;
        }
        writeLock.lock();
        try {
            if (indices.isEmpty()) {
                return false;
            } else {
                final Holder<T> removed = indices.remove(service);
                if (removed == null) {
                    return false;
                } else {
                    services.remove(service);
                    final int size = indices.size();
                    if (size == 0) {
                        next.set(null);
                    } else if (size == 1) {
                        removed.next.prev = null;
                        removed.next.next = null;
                    } else {
                        final Holder<T> nextHolder = removed.next;
                        final Holder<T> prevHolder = removed.prev;
                        nextHolder.prev = prevHolder;
                        prevHolder.next = nextHolder;
                    }
                    return true;
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public T random() {
        T result = null;
        while (result == null) {
            try {
                result = services.get(ThreadLocalRandom.current().nextInt(services.size()));
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        return result;
    }

    @Override
    public T next() {
        return next.getAndUpdate(h -> {
            if (h == null || h.next == null) {
                return h;
            } else {
                return h.next;
            }
        }).current;
    }
}
