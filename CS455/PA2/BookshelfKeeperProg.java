//main interface for user
// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CSCI455 PA2
// Fall 2022
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
/**
 * Class BookshelfKeeperProg
 *
 *
 * Allows user to create a bookshelf of different heights as an input to the terminal.
 * User can enter the different integer heights in any white space seperated fashion as long as they are ascending
 * and heights >0. Once entered, a bookshelf will be displayed to the temrinal containing the heights in ascending order.
 * user will be allowed to perform 3 operations( put height, pick index , and end)
 * If user enters invalid input, program will terminate. As each put or pick operation, an updated bookshlf will be updated
 * along with the current least operations needed to perform operation and sum of all operations executed
 * An example output looks like this [1, 3, 5] 0 0
 * put 2 -> will result in -> [1, 2, 3, 5] 3 3
 */
public class BookshelfKeeperProg{
   public static void main(String args[]){
      Scanner in = new Scanner(System.in);
      System.out.println("Please enter initial arrangement of books followed by newline:");
      ArrayList<Integer> books = new ArrayList<Integer>(); //new array list that will be used to hold user initial input

      books = readAndValidateString(in.nextLine()); //validate user input and populate books arraylist
      Bookshelf bookshelf = new Bookshelf(books); //instantiate new bookshelf object with books arraylist
      boolean exitProgramFlag = false;//boolean flag to control user input
      String operation = ""; //operation will hold what the enters (i.e; pick, put, end, etc")
      int inputVal = 0; // inputVal will hold value used for pick and put operations

      if(!bookshelf.isSorted()) //check whether bookshelf is sorted
         unSortedBookshelf();

      /*confirm bookshelf includes only positive height values and bookshelf is sorted
      [-1 4 6 7] should not execute bc of negative values although it is technically sorted */
      if (!isNegativeHeight(bookshelf) && bookshelf.isSorted()) {
         //BookshelfKeeper object is instantiated
         BookshelfKeeper bookshelfKeeper = new BookshelfKeeper(bookshelf);
         System.out.println(bookshelfKeeper.toString());
         System.out.println("Type pick <index> or put <height> followed by newline. Type end to exit.");

         //while look will be controlled by boolean variable that exits upon "end" or wrong input
         while(!exitProgramFlag){
            //read in first part of user input
            operation = in.next();

            //program will end if operation == end
            if(operation.equals("end")) {
               exitProgramFlag = true;
            }
            //check if first part of scanner is put or pick
            else if (operation.equals("put") || operation.equals("pick")){
               inputVal = in.nextInt(); //assumes user will enter integer following put/pick input
               
               if (inputVal == 0 && bookshelfKeeper.getNumBooks() == 0) {
                  System.out.println("ERROR: Entered pick operation is invalid on this shelf.");
                  break;
               }
               //check if there is only 1 element and user enters value greater than size()-1
               else if(inputVal > bookshelfKeeper.getNumBooks()-1){
                  System.out.println("ERROR: Entered pick operation is invalid on this shelf.");
                  break;
               }
               
               if(operation.equals("put")){
                  if(validHeight(inputVal)){
                     bookshelfKeeper.putHeight(inputVal);
                     System.out.println(bookshelfKeeper.toString());
                  }
                  else
                     break;
               }
               else{// if(operation.equals("pick")){
                  if(validHeight(inputVal)){
                     bookshelfKeeper.pickPos(inputVal);
                     System.out.println(bookshelfKeeper.toString());
                  }
                  else
                     break;
               }
            }
            //user enters anything other than put, pick, or end
            //exit program
            else{
               invalidInput();
               exitProgramFlag = true;
            }
         }
      }
      System.out.println("Exiting Program.");
   }
   ///usSortedBookshelf() will be used to print out error message for bookshelf that isn't sorted in ascending order
   public static void unSortedBookshelf(){
      System.out.println("ERROR: Heights must be specified in non-decreasing order.");
   }
   //isNegativeHeight() will be used to determine if provided heights contain a negative height
   //input is bookshelf and returns true/false
   public static boolean isNegativeHeight(Bookshelf bookshelf) {
      for (int i = 0; i < bookshelf.size(); i++){
         if(bookshelf.getHeight(i) < 1){
            System.out.println("ERROR: Height of a book must be positive.");
            return true;
         }
      }
      return false;
   }
   //readAndValidateString() will take a string of heights as an input and convert them into an arraylist
   //that will be used to populate the bookshelf
   public static ArrayList<Integer> readAndValidateString(String input){
      ArrayList<Integer> returnInput = new ArrayList<Integer>();//arraylist used to return
      Scanner convert = new Scanner(input);//scanner object

      while(convert.hasNextInt()){
         returnInput.add(convert.nextInt());
      }
      return returnInput;
   }
   //invalidInput() will simply display error message for wrong input to terminal
   public static void invalidInput(){
      System.out.println("ERROR: Invalid command. Valid commands are pick, put, or end.");
   }

   public static boolean validHeight(int height){
    
      if (height <1 ){
         System.out.println("ERROR: Height of a book must be positive.");
         return false;
      }
      
      return true;
      
   }
}