// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CSCI455 PA2
// Fall 2022


/**
 * Class Bookshelf
 * Implements idea of arranging books into a bookshelf.
 * Books on a bookshelf can only be accessed in a specific way so books don’t fall down;
 * You can add or remove a book only when it’s on one of the ends of the shelf.
 * However, you can look at any book on a shelf by giving its location (starting at 0).
 * Books are identified only by their height; two books of the same height can be
 * thought of as two copies of the same book.
 */
import java.util.ArrayList;
import java.io.*;

public class Bookshelf {

   /**Representation invariant:
    //bookshelf attribute cannot be null
    //heights of books must be > 0

    // put position has to be within bounds -> 0 <= position < this.size()
    <put rep. invar. comment here> */

   // <add instance variables here>
   private ArrayList<Integer> bookshelf;

   /**
    * Creates an empty Bookshelf object i.e. with no books
    */
   public Bookshelf() {

      bookshelf = new ArrayList<Integer>();

      assert isValidBookshelf();  // sample assert statement (you will be adding more of these calls)
   }

   /**
    * Creates a Bookshelf with the arrangement specified in pileOfBooks. Example
    * values: [20, 1, 9].
    *
    * PRE: pileOfBooks contains an array list of 0 or more positive numbers
    * representing the height of each book.
    */
   public Bookshelf(ArrayList<Integer> pileOfBooks) {
      this.bookshelf = new ArrayList<>(pileOfBooks);

      assert isValidBookshelf();
   }

   /**
    * Inserts book with specified height at the start of the Bookshelf, i.e., it
    * will end up at position 0.
    *
    * PRE: height > 0 (height of book is always positive)
    */
   public void addFront(int height) {
      this.bookshelf.add(0, height);

      assert isValidBookshelf();
   }
   /**
    * Inserts book with specified height at the end of the Bookshelf.
    *
    * PRE: height > 0 (height of book is always positive)
    */
   public void addLast(int height) {
      this.bookshelf.add(height);

      assert isValidBookshelf();

   }

   /**
    * Removes book at the start of the Bookshelf and returns the height of the
    * removed book.
    *
    * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
    */
   public int removeFront() {

      assert isValidBookshelf();
      return this.bookshelf.remove(0);   // dummy code to get stub to compile

   }

   /**
    * Removes book at the end of the Bookshelf and returns the height of the
    * removed book.
    *
    * PRE: this.size() > 0 i.e. can be called only on non-empty BookShelf
    */
   public int removeLast() {
      assert isValidBookshelf();

      return this.bookshelf.remove(this.bookshelf.size() - 1);   // dummy code to get stub to compile
   }

   /*
    * Gets the height of the book at the given position.
    *
    * PRE: 0 <= position < this.size()
    */
   public int getHeight(int position) {

      assert isValidBookshelf();

      return this.bookshelf.get(position);   // dummy code to get stub to compile

   }

   /**
    * Returns number of books on the this Bookshelf.
    */
   public int size() {

      return this.bookshelf.size();   // dummy code to get stub to compile
   }

   /**
    * Returns string representation of this Bookshelf. Returns a string with the height of all
    * books on the bookshelf, in the order they are in on the bookshelf, using the format shown
    * by example here:  “[7, 33, 5, 4, 3]”
    */
   public String toString() {
      /*
      int size = this.bookshelf.size();

      String retStr = "[";

      if (size == 0){
         retStr += "]";
         return retStr;

      }
      for(int i = 0; i< size-1; i++){
         retStr += this.bookshelf.get(i);
         retStr += ", ";
      }
      retStr += String.valueOf(this.bookshelf.get(size-1) + "]");
      */
      return bookshelf.toString();//retStr;   // dummy code to get stub to compile

   }

   /**
    * Returns true iff the books on this Bookshelf are in non-decreasing order.
    * (Note: this is an accessor; it does not change the bookshelf.)
    */
   public boolean isSorted() {

      if (size() == 0)
         return true;

      int temp = this.bookshelf.get(0); //get first element to use to compare

      for(int i = 1; i < this.bookshelf.size(); i++){

         if (temp < this.bookshelf.get(i))
            temp = this.bookshelf.get(i);
         else
            return false;

      }
      return true;  // dummy code to get stub to compile
   }

   /**
    * Returns true iff the Bookshelf data is in a valid state.
    * (See representation invariant comment for more details.)
    */
   private boolean isValidBookshelf() {
      //System.out.println("Hello");
      if (bookshelf == null){
         System.out.println("ERROR: Bookshelf cannot be NULL");
         return false;
      }
      if (size() == 0)
         return true;
      else{
         for(int i = 0; i < size(); i++){
            //System.out.println("Hello");
            if(getHeight(i) < 1){
               System.out.println("ERROR: Height of a book must be positive.");
               return false;
            }
         }
      }
      return true;  // dummy code to get stub to compile

   }
   private boolean isValidHeight(){
      for (int i = 0; i < size(); i++){
         if (getHeight(i) < 1){
            System.out.println("ERROR: Height of a book must be positive.");
            return false;
         }
      }
      return true;
   }
   private boolean isValidSize(){
      if (size() < 1){
         System.out.println("ERROR: Invalid size");
         return false;
      }
      return true;

   }
   private boolean isValidPosition(int pos){
      if ( pos >= 0 && pos < size())
         return true;

      System.out.println("ERROR: Invalid position");
      return false;
   }

}


