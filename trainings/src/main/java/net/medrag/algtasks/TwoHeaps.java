package net.medrag.algtasks;

/**
 * The Two Heaps algorithm is a technique used to solve problems that involve maintaining two heaps - a min-heap and a max-heap.
 * The algorithm is particularly useful for problems that involve finding the median of a set of numbers,
 * but can be applied to a wide range of problems that require maintaining two heaps.
 * <p>
 * The algorithm works by dividing the input into two halves - one half is added to a max-heap, and the other half is added to a min-heap.
 * The max-heap contains the smaller half of the input, and the min-heap contains the larger half.
 * <p>
 * To find the median, we need to look at the top elements of the two heaps. If the heaps have an equal number of elements,
 * the median is the average of the top elements. Otherwise, the median is the top element of the heap with more elements.
 *
 * @author Stanislav Tretiakov
 * 04.05.2023
 */
public class TwoHeaps {
}
