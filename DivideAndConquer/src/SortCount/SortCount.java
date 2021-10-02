package SortCount;

public class SortCount {
    static int countInversions(int[] array) {
        // TODO
        if (array == null) return 0;
        return mergesort(0, array.length - 1, array, new int[array.length]);
    }

    private static int mergesort(int lo, int hi, int[] arr, int[] copyArr) {
        if (hi <= lo) return 0;

        int inversions = 0;
        int mid = lo + (hi - lo) / 2;

        inversions += mergesort(lo, mid, arr, copyArr);
        inversions += mergesort(mid + 1, hi, arr, copyArr);
        inversions += merge(lo, mid, hi, arr, copyArr);

        return inversions;
    }


    private static int merge(int lo, int mid, int hi, int[] arr, int[] copyArr) {

        int inversions = 0;

        for (int i = lo; i <= hi; i++) {
            copyArr[i] = arr[i];
        }

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid)                        arr[k] = copyArr[j++];
            else if (j > hi)                    arr[k] = copyArr[i++];
            else if (copyArr[i] <= copyArr[j])  arr[k] = copyArr[i++];
            else                                {arr[k] = copyArr[j++]; inversions += mid - i + 1;}
        }

        return inversions;
    }

    public static void main(String[] args) {
        int[] input = { 2, 1, 0, 8 };

        System.out.println(countInversions(input));

    }
}
