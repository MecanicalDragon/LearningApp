package net.medrag.minigames.deadlock;

import java.time.LocalTime;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * @author Stanislav Tretyakov
 * 26.10.2021
 */
public class DeadLockGame {

    static final int STATE_SIZE = 10;

    static Parlor[] GOV_HOUSE;

    static Official[] STATE;

    static class Official {

        public Official(Parlor residence) {
            this.residence = residence;
        }

        private final Parlor residence;

        void deliverDocument(Document document, Parlor target) {
            residence.lockParlorAndDo(
                    (p, d) -> target.deliverDocument(document), target, document
            );
        }

        public String toString() {
            return String.valueOf(hashCode());
        }
    }

    static class Parlor {
        synchronized void lockParlorAndDo(BiConsumer<Parlor, Document> job, Parlor parlor, Document document) {
            job.accept(parlor, document);
        }

        synchronized void deliverDocument(Document document) {
            System.out.printf("%s: Document %s has been passed to parlor %s.\n", LocalTime.now(), document, this);
        }

        public String toString() {
            return String.valueOf(hashCode());
        }
    }

    static class Document {

        public String toString() {
            return String.valueOf(hashCode());
        }
    }

    static Parlor getRandomParlor() {
        return GOV_HOUSE[(int) (Math.random() * 10)];
    }

    public static void main(String[] args) {

        GOV_HOUSE = new Parlor[STATE_SIZE];
        STATE = new Official[STATE_SIZE];

        Deque<Document> documentsToProcess = new ConcurrentLinkedDeque<>();
        final ExecutorService documentGenerator = Executors.newSingleThreadExecutor((r) -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        documentGenerator.submit(() -> {
            while (true) {
                if (documentsToProcess.size() < 50) {
                    documentsToProcess.add(new Document());
                }
            }
        });
        final ExecutorService government = Executors.newFixedThreadPool(10);


        for (int i = 0; i < GOV_HOUSE.length; i++) {
            GOV_HOUSE[i] = new Parlor();
        }

        for (int i = 0; i < STATE.length; i++) {
            STATE[i] = new Official(GOV_HOUSE[i]);
        }

        for (Official official : STATE) {
            government.submit(() -> {
                while (true) {
                    final Document document = documentsToProcess.poll();
                    System.out.printf("%s: Official %s has taken new document to process.\n", LocalTime.now(), official);
                    official.deliverDocument(document, getRandomParlor());
                }
            });
        }

        documentGenerator.shutdown();
        government.shutdown();
    }
}
