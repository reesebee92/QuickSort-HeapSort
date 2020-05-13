
/**
 * QuickSortC 
 * 
 * This class will take an input file of potentially unsorted integers and return an 
 * output file of sorted integers using QuickSortC. This version of a QuickSort uses  
 * an optimized version of QuickSort using the first element of the partition as the 
 * pivot and resorts to a simple selection sort for partition of size k = 50.
 * 
 * @author SDantzler
 * @version 2.0
 * */

import java.util.*;
import java.io.*;

public class QuickSortC {

   public static void main(String[] args) throws IOException {

      // Check for command line arguments
      if (args.length != 2) {
         System.out.println(
               "Usage: java QuickSortC [input file pathname] [output file pathname] ");
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
               + " for QuickSortC is:\n");
         for (Integer x : convert)
            output.print(x + " ");
         output.println("\n");

         output.println("Elapsed Time: " + elapsedTime + " ns");
         output.println();

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
         // do insertion sort if k = 50
         if (high - low == 50) {
            insertionSort(array, low, high);
            break;
         } else {
            int pivot = Partition(array, low, high);

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
    * Title: Hybrid QuickSort Algorithm Author: unknown Code version: 1.0
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
    * Title: HeapSort Author: Shivi_Aggarwal, Akanksha_Rai, RishiAdvani, Vibhav
    * Gupta, kushjaing, rishiraj1996 Code version: 1.0 Availability:
    * https://www.hackerearth.com/practice/algorithms/sorting/
    * quick-sort/tutorial/
    * 
    * This method takes a pivot and partitions the array using said pivot
    **************************************************************************/
   public static int Partition(Integer[] array, int low, int high) {
      // make the first element as pivot element
      int pivot = array[low];

      int i = (low + 1); // Index of smaller element

      for (int j = i; j <= high; j++) {
         // rearrange the array by putting elements which are less than the
         // pivot
         // on one side and which are greater on the other side
         if (array[j] < pivot) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++; // increment index of smaller element
         }
      } // end for loop
        // put the pivot element in its proper place
      int temp = array[i - 1];
      array[i - 1] = array[low];
      array[low] = temp;
      return (i - 1); // return the position of the pivot
   }// end Partition method

}// end class QuickSortC
