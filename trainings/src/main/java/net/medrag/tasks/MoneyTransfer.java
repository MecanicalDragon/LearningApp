package net.medrag.tasks;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The task is plain and straightforward: implement a service that transfers money from one account to another.
 */
public class MoneyTransfer {
    private static final Map<Long, Account> STORAGE = new ConcurrentHashMap<>();
    private static final Deque<Transaction> ACTIVE_TRANSACTIONS = new ConcurrentLinkedDeque<>();
    private static final Thread JOB = new Thread(() -> {
        while (true) {
            try {
                final var transaction = ACTIVE_TRANSACTIONS.peekFirst();
                if (transaction == null) {
                    Thread.sleep(1000);
                    continue;
                }
                final Reference<Transaction> reference = new Reference<>();
                STORAGE.compute(transaction.to, (id, account) -> {
                    if (account.activeTransactions.containsValue(transaction)) {
                        return account;
                    } else {
                        reference.set(transaction);
                        return account.add(transaction.amount, transaction);
                    }
                });
                if (reference.get() != null) {
                    ACTIVE_TRANSACTIONS.remove(transaction);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception ignored) {
            }
        }
    });

    static {
        JOB.setDaemon(true);
        JOB.start();
    }

    public boolean transfer(long from, long to, BigDecimal amount) {
        if (from == to || !STORAGE.containsKey(to)) {
            return false;
        }
        final Reference<Transaction> transaction = new Reference<>();

        STORAGE.compute(from, (id, account) -> {
            if (account == null || account.money.doubleValue() < amount.doubleValue()) {
                return account;
            }
            final Transaction t = new Transaction(amount, from, to);
            transaction.set(t);
            ACTIVE_TRANSACTIONS.add(t);
            return account.subtract(amount, t);
        });

        if (transaction.get() == null) {
            return false;
        }

        STORAGE.compute(to, (id, account) -> {
            final var t = transaction.get();
            if (account.activeTransactions.containsValue(t)) {
                transaction.set(null);
                return account;
            }
            return account.add(amount, t);
        });

        if (transaction.get() != null) {
            ACTIVE_TRANSACTIONS.remove(transaction.get());
        }
        return true;
    }

    private static final class Reference<T> {
        private T object = null;

        T get() {
            return object;
        }

        void set(T object) {
            this.object = object;
        }
    }
}

final class Account {
    final long id;
    final BigDecimal money;
    final Map<Long, Transaction> activeTransactions;

    public Account(long id, BigDecimal money, Map<Long, Transaction> activeTransactions) {
        this.id = id;
        this.money = money;
        this.activeTransactions = activeTransactions;
    }

    Account subtract(BigDecimal subtrahend, Transaction transaction) {
        return new Account(
            this.id,
            this.money.subtract(subtrahend),
            copyTransactions(transaction)
        );
    }

    Account add(BigDecimal addendum, Transaction transaction) {
        return new Account(
            this.id,
            this.money.add(addendum),
            copyTransactions(transaction)
        );
    }

    private Map<Long, Transaction> copyTransactions(Transaction transaction) {
        final Map<Long, Transaction> newMap = new HashMap<>(this.activeTransactions.size() + 1, 1.0f);
        newMap.put(transaction.id, transaction);
        final var now = LocalDateTime.now();
        this.activeTransactions.forEach((i, t) -> {
            if (t.timestamp.plusDays(7).isAfter(now)) {
                newMap.put(i, t);
            }
        });
        return Map.copyOf(newMap);
    }
}

final class Transaction {
    private static final AtomicLong TRANSACTION_SEQUENCE = new AtomicLong(0);
    final long id = uniqueId();
    final BigDecimal amount;
    final long from;
    final long to;
    final LocalDateTime timestamp = LocalDateTime.now();

    public Transaction(BigDecimal amount, long from, long to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    private long uniqueId() {
        return TRANSACTION_SEQUENCE.incrementAndGet();
    }
}
