
/**
 * HeapSort 
 * 
 * This class will take an input file of potentially unsorted integers and return an 
 * output file of sorted integers using a HeapSort. The HeapSort has two phases to perform 
 * the sort which includes building a heap and then using said heap to extract elements
 * in sorted order from the heap.
 * 
 * @author SDantzler
 * @version 2.0
 * */
import java.util.*; 
import java.io.*;

public class HeapSort {

   public static void main(String[] args) throws IOException {

      // Check for command line arguments
      if (args.length != 2) {
         System.out.println(
               "Usage: java HeapSort [input file pathname] [output file pathname] ");
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

         // HeapSort with timing
         long startTime = System.nanoTime();

         HeapSort numberSort = new HeapSort();
         numberSort.sort(convert);

         long endTime = System.nanoTime();
         long elapsedTime = (endTime - startTime);

         // Print out the results
         output.println("The sorted array for the input of size " + arraySize
               + " is:\n");
         for (Integer x : convert)
            output.print(x + " ");
         output.println("Elapsed Time: " + elapsedTime + " ns");
         output.println("\n");

      } catch (IOException e) {
         System.out.print("File Error: " + e);
      } // end try/catch
   }// end main method

   /***************************************************************************
    * Title: HeapSort 
    * Author: Shivi_Aggarwal, Akanksha_Rai, RishiAdvani, Vibhav
    * Gupta, kushjaing, rishiraj1996 
    * Code version: 1.0 
    * Availability: https://www.geeksforgeeks.org/heap-sort/
    * 
    * This method builds a heap and sorts a file by pulling items in order
    * from the heap
    **************************************************************************/
   public void sort(Integer[] array) {
      int arrayLength = array.length;

      // Build heap (rearrange array)
      for (int i = arrayLength / 2 - 1; i >= 0; i--) {
         heapify(array, arrayLength, i);
      }
      // One by one extract an element from heap
      for (int i = arrayLength - 1; i > 0; i--) {
         // Move current root to end
         int temp = array[0];
         array[0] = array[i];
         array[i] = temp;

         // call max heapify on the reduced heap
         heapify(array, i, 0);
      }
   }// end sort method

   /***************************************************************************
    * Title: HeapSort 
    * Author: Shivi_Aggarwal, Akanksha_Rai, RishiAdvani, Vibhav
    * Gupta, kushjaing, rishiraj1996 
    * Code version: 1.0 
    * Availability: https://www.geeksforgeeks.org/heap-sort/
    * 
    * This method heapifys a subtree rooted with node i which is an index in 
    * array[]. Rearrange a heap to maintain the max heap property required
    **************************************************************************/
   void heapify(Integer[] array, int heapSize, int index) {
      int largest = index; // Initialize largest as root
      int left = 2 * index + 1; // left child node
      int right = 2 * index + 2; // right child node

      // If left child is larger than root
      if (left < heapSize && array[left] > array[largest])
         largest = left;

      // If right child is larger than largest so far
      if (right < heapSize && array[right] > array[largest])
         largest = right;

      // If largest is not root
      if (largest != index) {
         int temp = array[index];
         array[index] = array[largest];
         array[largest] = temp;

         // Recursively heapify the affected sub-tree
         heapify(array, heapSize, largest);
      }
   }// end heapify method

}// end class HeapSort
