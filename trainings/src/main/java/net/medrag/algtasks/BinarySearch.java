package net.medrag.algtasks;

/**
 * @author Stanislav Tretyakov
 * 26.03.2023
 */
public class BinarySearch {

    private static final int[] ARRAY = new int[]{
            -50, -47, -45, -42, -42, -42, -37, -35, -34, -32,
            -32, -29, -28, -26, -23, -21, -21, -20, -16, -15,
            -12, -12, -12, -9, -6, -3, -1, 0, 0, 2,
            4, 4, 7, 9, 15, 15, 16, 17, 20, 24,
            24, 24, 24, 27, 29, 35, 37, 39, 39, 43,
            48, 48, 50, 51, 55, 57, 58, 60, 60, 60
    };

    private static final int[] SEARCH = new int[]{-12, 5, 0, 48, -42, -50, 50, 60};

    public static void main(String[] args) {
        for (int search : SEARCH) {
            System.out.println(bs(ARRAY, search));
        }
    }

    private static int bs(int[] array, int x) {
        int startIndex = 0, endIndex = array.length - 1;
        int suspect = -1;
        while (startIndex <= endIndex) {
            int mid = (startIndex + endIndex) / 2;
            final var found = array[mid];
            if (found == x) {
                suspect = mid;
                break;
            } else if (found > x) {
                endIndex = mid - 1;
            } else {
                startIndex = mid + 1;
            }
        }
        while (suspect > 0) {
            if (array[suspect - 1] == x) {
                suspect--;
            } else {
                break;
            }
        }
        return suspect;
    }
}
