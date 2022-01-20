package Midterm;

import java.util.Arrays;
import java.util.Collections;

public class WeekFour {
    public static int findIth(int[] arr, int index) {
        return quickSelect(arr, index, 0, arr.length - 1);
    }

    private static int quickSelect(int[] arr, int index, int lo, int hi) {
        if (lo >= hi) return arr[lo];

        int pivot = partition(arr, lo, hi);

        if (pivot == index) {
            return arr[pivot];
        } else if (pivot < index) {
            return quickSelect(arr, index, pivot + 1, hi);
        } else {
            return quickSelect(arr, index, lo, pivot - 1);
        }

    }

    private static int partition(int[] arr, int lo, int hi) {
        int pivot = arr[lo];

        int i = lo;
        int j = hi + 1;

        while (true) {
            while (arr[++i] < pivot) if (i >= hi) break;
            while (pivot < arr[--j]) if (j < lo) break;
            if (i >= j) break;

            swap(arr, i, j);
        }

        swap(arr, lo, j);

        return j;

    }


    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] =  arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {

        int[] arr = {0, 4, 3, 1, 2, 11};
        System.out.println(findIth(arr, 5));
    }
}
