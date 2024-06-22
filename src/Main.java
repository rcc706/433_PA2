import java.util.*;

public class Main {

    // Measuring Time
    public static double totalTime;
    public static double averageTimeMilli;
    public static double averageTimeNano;

    // Measuring the number of basic operations
    public static long basicOp;


    public static void main(String[] args) {

        // args:  arg0       arg1           arg2
        // type:  sort     #ofArrays    sizeOfArrays
        // ex  :  merge      1000           2000

        totalTime = 0;
        averageTimeMilli = 0;
        averageTimeNano = 0;
        basicOp = 0;

        // Checking if the args are valid --> else = Invalid Input
        if (Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[2]) > 0) {

            // Each iteration will check the sort type and generate a random array
            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                switch (args[0]) {
                    case "merge" -> {

                        // Generating random array before measuring time
                        double[] genArray1 = genRandArray(Integer.parseInt(args[2]));
                        long startTime1 = System.nanoTime();
                        merge(genArray1);
                        long endTime1 = System.nanoTime();
                        totalTime += (endTime1 - startTime1);
                    }
                    case "selection" -> selection(genRandArray(Integer.parseInt(args[2])));
                    case "bubble" -> bubble(genRandArray(Integer.parseInt(args[2])));
                    case "insertion" -> insertion(genRandArray(Integer.parseInt(args[2])));
                    case "quick" -> {

                        // Generating random array before measuring time
                        double[] genArray = genRandArray(Integer.parseInt(args[2]));
                        long startTime = System.nanoTime();
                        quick(genArray, 0, genArray.length - 1);
                        long endTime = System.nanoTime();
                        totalTime += (endTime - startTime);
                    }
                    default -> {
                        // If input is invalid
                        System.out.println("Invalid input. Exiting program.");
                        System.exit(0);
                    }
                }
            }

            // Calculating the average time and converting it to milliseconds.
            averageTimeNano = totalTime / Integer.parseInt(args[1]);
            averageTimeMilli = (totalTime / Integer.parseInt(args[1])) / 1000000;
            System.out.println(args[0] + " sort had an average time of: " + averageTimeMilli + " milliseconds");
            System.out.println(args[0] + " sort had an average time of: " + averageTimeNano + " nanoseconds");
            System.out.println("The basic operation count is: " + basicOp);

        }

        else {
            System.out.println("Invalid input. Exiting the program.");
            System.exit(0);
        }
    }

    public static void merge(double[] toSort) {
        if (toSort.length > 1) {

            // Finding middle, left, and right side of array
            int middle = toSort.length / 2;
            double[] left = new double[middle];
            double[] right = new double[toSort.length - middle];

            // Filling the left and right side of array
            for (int i = 0; i < middle; i++)
                left[i] = toSort[i];
            for (int j = middle; j < toSort.length; j++)
                right[j - middle] = toSort[j];

            // Merging left and Merging right, then sorting
            merge(left);
            merge(right);
            mergeSort(toSort, left, right);
        }
    }

    public static void mergeSort(double[] toSort, double[] toSortL, double[] toSortR) {

        basicOp++;

        // Local variables
        int l = toSortL.length;
        int r = toSortR.length;
        int i = 0;
        int j = 0;
        int k = 0;

        // Merging the arrays back
        while (i < l && j < r) {
            if (toSortL[i] <= toSortR[j])
                toSort[k++] = toSortL[i++];
            else
                toSort[k++] = toSortR[j++];
        }

        // Copying the elements back to the original array
        while (i < l)
            toSort[k++] = toSortL[i++];
        while (j < r)
            toSort[k++] = toSortR[j++];
    }

    public static void selection(double[] toSort) {
        long startTime = System.nanoTime();

        for (int i = 0; i < toSort.length - 1; i++) {

            // Find minimum element in the unsorted array
            int min = i;
            for (int j = i + 1; j < toSort.length; j++) {
                if (toSort[j] < toSort[min])
                    min = j; // minimum found
                    basicOp++;
            }

            // Swap the minimum with the first element found
            if(min != i) {
                double temp = toSort[min];
                toSort[min] = toSort[i];
                toSort[i] = temp;
            }
        }

        long endTime = System.nanoTime();
        totalTime += (endTime - startTime);
    }

    public static void bubble(double[] toSort) {
        long startTime = System.nanoTime();

        boolean swapped = true;
        while (swapped) {
            swapped = false;

            // Iterate through all elements in the array
            // if the previous element > current element --> swap them
            for (int i = 1; i < toSort.length; i++) {
                if (toSort[i-1] > toSort[i]) {
                    double temp = toSort[i-1];
                    toSort[i-1] = toSort[i];
                    toSort[i] = temp;
                    swapped = true;
                    basicOp++;
                }
            }
        }

        long endTime = System.nanoTime();
        totalTime += (endTime - startTime);
    }

    public static void insertion(double[] toSort) {
        long startTime = System.nanoTime();

        // Iterates through all elements in the array
        for (int i = 1; i < toSort.length; ++i) {
            double val = toSort[i]; // saving ith value
            int position = i - 1;   // index

            // Move elements that are greater than val 1 ahead of their position
            while (position >= 0 && toSort[position] > val) {
                basicOp++;
                toSort[position + 1] = toSort[position];
                position = position - 1;
            }
            toSort[position + 1] = val;
        }

        long endTime = System.nanoTime();
        totalTime += (endTime - startTime);
    }

    public static void quick(double[] toSort, int low, int high) {
        if (low < high) {

            // Partitioning then sorting elements before and ahead of the partition
            int p = partition(toSort, low, high);
            quick(toSort, low, p - 1);
            quick(toSort, p + 1, high);
        }
    }

    public static int partition(double[] toSort, int low, int high) {
        double pivot = toSort[high];
        int i = low - 1;
        basicOp++;

        for (int j = low; j <= high - 1; j++) {

            // if the current element is smaller than the pivot
            if (toSort[j] < pivot) {
                i += 1;

                // Swapping
                double temp = toSort[i];
                toSort[i] = toSort[j];
                toSort[j] = temp;
            }
        }

        // Swapping
        double temp1 = toSort[i+1];
        toSort[i+1] = toSort[high];
        toSort[high] = temp1;
        return i + 1;
    }

    public static double[] genRandArray(int arraySize) {

        // Generates a random array of size args[1] of double-type and returns the array
        double[] dubs = new double[arraySize];
        Random rand = new Random();

        for (int i = 0; i < arraySize; i++) {
            dubs[i] = rand.nextDouble();
        }
        return dubs;
    }
}