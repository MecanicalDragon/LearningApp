package net.medrag.algtasks;

/**
 * Finding the midpoint of a linked list: This is a classic example of using fast and slow pointers.
 * The slow pointer moves one node at a time, while the fast pointer moves two nodes at a time.
 * When the fast pointer reaches the end of the list, the slow pointer is at the midpoint.
 * <p>
 * Detecting a cycle in a linked list: Again, using the same concept of fast and slow pointers, if there is a cycle in the linked list,
 * the fast pointer will eventually catch up with the slow pointer, indicating the presence of a cycle.
 * <p>
 * Finding the kth element from the end of a linked list: Using two pointers, we can move the fast pointer k nodes ahead of the slow pointer.
 * Then we move both pointers at the same pace until the fast pointer reaches the end of the list.
 * The slow pointer will be pointing to the kth element from the end.
 * <p>
 * Checking for palindrome: We can use fast and slow pointers to find the midpoint of the list.
 * Then, we reverse the second half of the list and compare it with the first half. If they are the same, the list is a palindrome.
 *
 * @author Stanislav Tretiakov
 * 04.05.2023
 */
public class FastAndSlowPointers {
}
