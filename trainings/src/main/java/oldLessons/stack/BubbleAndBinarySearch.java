package oldLessons.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BubbleAndBinarySearch {

    public static void main(String[] args) {
        int[]arr = {1,2,3,4,5};
        List<Integer> inputNumbers = new ArrayList<>();
        Collections.addAll(inputNumbers, 1,2,3,4,5);
        System.out.println(inputNumbers.size());

        for (int i = 0; i<arr.length;i++){
            for(int j = 0;j<arr.length-1-i;j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }
            }
        }
        for(int i :arr)
            System.out.print(i+",");
        System.out.println("");
        System.out.println(findInt(arr, 0));

        for (int i = 0; i < inputNumbers.size(); i++){
            for (int j = 0; j < inputNumbers.size() - 1 - i; j++) {
                if (inputNumbers.get(j) > inputNumbers.get(j + 1)) {
                int t = inputNumbers.get(j);
                inputNumbers.set(j, inputNumbers.get(j + 1));
                inputNumbers.set(j + 1, t);
                }
            }
        }
    }

    private static int findInt(int[] arr, int x){
        int min = 0, max = arr.length;
        int mid;
        while (min<max) {
            mid = (min + max) / 2;
            if (x == arr[mid]) return mid;
            else {
                if (arr[mid] > x) {
                    max = mid;

                } else {
                    min = mid+1;
                }
            }
        }
        return -1;
    }
}
