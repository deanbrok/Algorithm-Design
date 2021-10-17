package Tutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MaxSubarray {

    public static int largestSum(int[] arr) {
        // TODO
        if (arr.length == 1) return arr[0];
        return largestSum(arr, 0, arr.length - 1);
    }

    private static int largestSum(int[] arr, int lo, int hi) {
        if (lo >= hi) return arr[lo];

        // Recurse
        int mid = lo + (hi - lo) / 2;
        int leftResult = largestSum(arr, lo, mid);
        int rightResult = largestSum(arr, mid + 1, hi);

        // Combine
        int currentSum = 0;
        int leftSum = Integer.MIN_VALUE;

        for (int i = mid; i >= lo ; i--) {
            currentSum += arr[i];
            if (leftSum < currentSum) {
                leftSum = currentSum;
            }
        }

        currentSum = 0;
        int rightSum = Integer.MIN_VALUE;

        for (int i = mid + 1; i <= hi ; i++) {
            currentSum += arr[i];
            if (rightSum < currentSum) {
                rightSum = currentSum;
            }
        }

        return Math.max(leftSum + rightSum, Math.max(leftResult, rightResult));

    }


    public static void main(String[] args) {
        int[] input = new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(largestSum(input));
    }

}
