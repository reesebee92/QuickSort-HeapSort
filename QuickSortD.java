/**
 * QuickSortD 
 * 
 * This class will take an input file of potentially unsorted integers and return an 
 * output file of sorted integers using QuickSortD. This version of a QuickSort uses  
 * an optimized version of QuickSort using the median of three elements in the partition 
 * as the pivot and resorts to a simple selection sort for partition of size k <= 2.
 * 
 * @author SDantzler
 * @version 2.0
 * */

import java.util.*;
import java.io.*;

public class QuickSortD {

   public static void main(String[] args) throws IOException {

   // Check for command line arguments
      if (args.length != 2) {
         System.out.println(
               "Usage: java QuickSortD [input file pathname] [output file pathname] ");
         System.exit(1);
      }

      // try with resources
      try (BufferedReader input = new BufferedReader(new FileReader(args[0]));
            PrintWriter output = new PrintWriter(
                  new BufferedWriter(new FileWriter(args[1])))) {

      // variables needed to create array for sorting
         String line;
         int index;
         ArrayList<Integer> numbers = new ArrayList<Integer>();
         Integer[] convert;

      // read in the file
         while ((line = input.readLine()) != null) {

            String numberStr = "";

            for (index = 0; index < line.length(); index++) {
               if (line.charAt(index) >= '0' && line.charAt(index) <= '9') {
                  numberStr = numberStr + line.charAt(index);
               }
            } // end for loop

            numbers.add(Integer.parseInt(numberStr)); // add parsed integer to
                                                      // ArrayList numbers

         } // end while

         // convert ArrayList to regular array convert
         convert = new Integer[numbers.size()];
         convert = numbers.toArray(convert);

         // Value needed to run QuickSort
         int arraySize = numbers.size();

         // Sorting with Timer
         long startTime = System.nanoTime();

         // optimizedQuickSort(convert, 0, arraySize - 1);
         optimizedQuickSort(convert, 0, arraySize - 1);

         long endTime = System.nanoTime();
         long elapsedTime = endTime - startTime;

         // Print out results
         output.println("The sorted array for the input of size " + arraySize
               + " for QuickSortD is:\n");
         for (Integer x : convert)
            output.print(x + " ");
         output.println("\n");
         output.println("Elapsed Time: " + elapsedTime + " ns");
         

      } catch (IOException e) {
         System.out.println("File Error!" + e);
      }

   }// end main method
   
   /***************************************************************************
    * Title: Hybrid QuickSort Algorithm Author: unknown Code version: 1.0
    * Availability: https://www.techiedelight.com/hybrid-quicksort/
    * 
    * This method performs an optimized QuickSort on data by the use of
    * partition. The optimization occurs through the use of recurring first into
    * the smaller side of the partition, then use a tail call to recur into the
    * other side
    **************************************************************************/
   public static void optimizedQuickSort(Integer[] array, int low, int high) {
      while (low < high) {
         // do array simple comparison and swap if partition is 2 or less
         if (high - low <= 2) {
            insertionSort(array, low, high);
            break;
         } else {
            double median = medianOfThree(array, low, high);
            int pivot = Partition(array, low, high, median);

            // tail call optimizations - recur on smaller sub-array
            if (pivot - low < high - pivot) {
               optimizedQuickSort(array, low, pivot - 1);
               low = pivot + 1;
            } else {
               optimizedQuickSort(array, pivot + 1, high);
               high = pivot - 1;
            } // end if/else statement
         } // end if/else statement
      } // end while loop
   }// end optimizedQuickSort method

   /***************************************************************************
    * Title: Hybrid QuickSort Algorithm 
    * Author: unknown
    * Code version: 1.0 
    * Availability: https://www.techiedelight.com/hybrid-quicksort/
    * 
    * This method performs an insertion sort on the converted numbers array
    **************************************************************************/
   public static void insertionSort(Integer[] array, int low, int high) {
      // Start from second element (element at index 0
      // is already sorted)
      for (int i = low + 1; i <= high; i++) {
         int value = array[i];
         int j = i;

         // Find the index j within the sorted subset array[0..i-1]
         // where element array[i] belongs
         while (j > low && array[j - 1] > value) {
            array[j] = array[j - 1];
            j--;
         }

         // Note that subarray array[j..i-1] is shifted to
         // the right by one position i.e. array[j+1..i]
         array[j] = value;
      }
   }// end insertionSort method

   /***************************************************************************
    * Title: Quick sort with median-of-three partitioning : Sort « Collections «
    * Java Tutorial Author: unknown Code version: 1.0 Availability:
    * http://www.java2s.com/Tutorial/Java/0140__Collections/
    * Quicksortwithmedianofthreepartitioning.htm
    * 
    * This method selects 3 values (first, middle, last) to be used as the pivot
    * and rearranges the values if unsorted
    **************************************************************************/
   public static int medianOfThree(Integer[] array, int low, int high) {
      int center = (low + high) / 2; // third value

      // swap index values in array if low is greater than center
      if (array[low] > array[center])
         swap(array, low, center);

      // swap index values in array if low is greater than high
      if (array[low] > array[high])
         swap(array, low, high);

      // swap index values in array if center is greater than high
      if (array[center] > array[high])
         swap(array, center, high);

      // swap index values in array if center is greater than high - 1
      swap(array, center, high - 1);
      return array[high - 1]; // return the median value
   }// end medianOfThree method

   /***************************************************************************
    * Title: Quick sort with median-of-three partitioning : Sort « Collections
    *  « Java Tutorial Author: unknown Code version: 1.0
    * Availability: http://www.java2s.com/Tutorial/Java/0140__Collections/
    * Quicksortwithmedianofthreepartitioning.htm
    * 
    * This method swaps two index elements in an array
    **************************************************************************/
    public static void swap(Integer[] array, int index1, int index2) {
      int temp = array[index1];
      array[index1] = array[index2];
      array[index2] = temp;
    }// end swap method

   /***************************************************************************
    * Title: Quick sort with median-of-three partitioning : Sort « Collections
    *  « Java Tutorial Author: unknown Code version: 1.0
    * Availability: http://www.java2s.com/Tutorial/Java/0140__Collections/
    * Quicksortwithmedianofthreepartitioning.htm
    * 
    * This method takes a pivot and partitions the array using said pivot
    **************************************************************************/
   public static int Partition(Integer[] array, int left, int right,
         double pivot) {
      // make the first element as pivot element
      int leftPtr = left;
      // Index of smaller element
      int rightPtr = right - 1;

      // rearrange the array by putting elements which are less than the
      // pivot
      // on one side and which are greater on the other side
      while (true) {
         while (array[++leftPtr] < pivot)
            ;
         while (array[--rightPtr] > pivot)
            ;
         if (leftPtr >= rightPtr)
            break;
         else
            swap(array, leftPtr, rightPtr);
      }
      // put the pivot element in its proper place
      swap(array, leftPtr, right - 1);
      return leftPtr; // return the position of the pivot
   }// end Partition method

}// end class QuickSortD