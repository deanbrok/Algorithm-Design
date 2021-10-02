package Mergesort;

public class Mergesort {

    private int[] arr;
    private int[] copyArr;
    /**
     * Takes an array and sorts it in an ascending order.
     *
     * @param arr
     *     - the array that needs to be sorted.
     */
    public void sort(int[] arr) {
        // TODO
        this.arr = arr;
        this.copyArr = new int[arr.length];

        mergesort(0, arr.length - 1);

    }

    private void mergesort(int lo, int hi) {

        if (hi <= lo) return;

        int mid = lo + (hi - lo) / 2;

        mergesort(lo, mid);
        mergesort(mid + 1, hi);

        merge(lo, mid, hi);

    }


    private void merge(int lo, int mid, int hi) {

        for (int i = lo; i <= hi; i++) {
            copyArr[i] = arr[i];
        }

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid)                        arr[k] = copyArr[j++];
            else if (j > hi)                    arr[k] = copyArr[i++];
            else if (copyArr[i] < copyArr[j])   arr[k] = copyArr[i++];
            else                                arr[k] = copyArr[j++];
        }

    }

    public static void main(String[] args) {
        int[] input = { 4, 2, 5, 1, 3 };
        new Mergesort().sort(input);

        for (int i: input) {
            System.out.println(i);
        }

    }


}
