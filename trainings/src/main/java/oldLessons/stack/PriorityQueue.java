package oldLessons.stack;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<K extends Comparable<K>, T> implements PriorityQueueable<K, T>{

     List<Pair<K,T>> queue;
     private int currentSize;

    PriorityQueue() {
        this.queue = new ArrayList<>();
    }

    @Override
    public T getMax() {
        /**
         Axe principle:
         1. return zero element.
         2. last index element becomes zero element and takes a name of sinkable.
         3. sinkable element swaps with greatest of child elements, if it more, then sinkable.
            left child: i*2+1, right child: i*2+2;
         4. goto 3.
        **/
        if (!queue.isEmpty()) {
            T returnable = queue.get(0).getValue();
            currentSize--;
            Pair<K, T> sinkable = queue.get(currentSize);
            queue.remove(currentSize);
            if (!queue.isEmpty()) {
                queue.set(0, sinkable);
                int sinkIndex = 0;

                while (true) {
                    int leftIndex = sinkIndex * 2 + 1 > currentSize-1
                            ? sinkIndex : sinkIndex * 2 + 1;
                    int rightIndex = sinkIndex * 2 + 2> currentSize-1
                            ? sinkIndex : sinkIndex * 2 + 2;
                    Pair<K, T> maxChild = (queue.get(leftIndex).getKey().compareTo
                            (queue.get(rightIndex).getKey()) > 0)
                            ? queue.get(leftIndex) : queue.get(rightIndex);
                    if (maxChild.getKey().compareTo(sinkable.getKey()) > 0) {
                        queue.set(sinkIndex, maxChild);
                        sinkIndex = queue.lastIndexOf(maxChild);
                        queue.set(sinkIndex, sinkable);
                    } else break;
                }
            }
            return returnable;
        }
        return null;
    }

    @Override
    public void add(K key, T type) {
        /**
         *  Float principle:
         *  1. set element to a first free index
         *  2. if element of parent index ( (i-1)/2 ) fewer, swap them.
         *  3. goto 2.
         */
        Pair <K ,T> afloat = new Pair<>(key,  type);
        queue.add(currentSize, afloat);
        int afloatIndex = currentSize;
        currentSize++;
        Pair<K,T> parent;

        while (true) {
            parent = queue.get((afloatIndex - 1) / 2);
            if (parent.getKey().compareTo(afloat.getKey()) < 0) {
                queue.set(afloatIndex,  parent);
                afloatIndex = queue.indexOf(parent);
                queue.set(afloatIndex, afloat);
                afloat = parent;
            }
            else break;
        }

    }

}
