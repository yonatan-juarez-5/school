// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CSCI455 PA2
// Fall 2022
/**
 * Class BookshelfKeeper 
 *
 * Enables users to perform efficient putPos or pickHeight operation on a bookshelf of books kept in 
 * non-decreasing order by height, with the restriction that single books can only be added 
 * or removed from one of the two *ends* of the bookshelf to complete a higher level pick or put 
 * operation.  Pick or put operations are performed with minimum number of such adds or removes.
 */
import java.util.ArrayList;
import java.io.*;

public class BookshelfKeeper {
   /**
    Representation invariant:

    <put rep. invar. comment here>
    bookshelf passed into constructor must be sorted in ascending order and
    must only include heights of > 0
    */
   // <add instance variables here>
   private Bookshelf bookshelf;
   private int numOperations;
   private int numBooks;
   private int currOperations;
   /**
    * Creates a BookShelfKeeper object with an empty bookshelf
    */
   public BookshelfKeeper() {
      this.bookshelf = new Bookshelf();
      this.numOperations = 0;
      this.numBooks = 0;
      this.currOperations = 0;
   }
   /**
    * Creates a BookshelfKeeper object initialized with the given sorted bookshelf.
    * Note: method does not make a defensive copy of the bookshelf.
    * PRE: sortedBookshelf.isSorted() is true.
    */
   public BookshelfKeeper(Bookshelf sortedBookshelf) {
      this.bookshelf = sortedBookshelf;
      this.numOperations = 0;
      this.numBooks = 0;
      this.currOperations = 0;

      assert this.isValidBookshelfKeeper();
   }
   /**
    * Removes a book from the specified position in the bookshelf and keeps bookshelf sorted
    * after picking up the book.
    * Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    * PRE: 0 <= position < getNumBooks()
    */
   public int pickPos(int position) {
      //check if bookshelf is empty and position is 0
      if (position == 0 && getNumBooks() == 0) {
         return 0;
      }
      //check if there is only 1 element and user enters value greater than size()-1
      else if(position > getNumBooks()-1){
         return 0;
      }
      int removeFront = position*2 + 1; //calculate steps required for picking from the front
      int removeBack = (getNumBooks() - 1 - position)*2 +1; //calculate steps required for picking from the back

      //create arraylist used to used to store temporary element during pick process
      ArrayList<Integer> removedBooks = new ArrayList<Integer>();
      int positionRemoved = 0;

      //this conditional will perform operation for shortest removal from the front of bookshelf
      if(removeFront <= removeBack){
         this.numOperations += removeFront;
         for(int i=0; i < removeFront/2; i++){
            removedBooks.add(bookshelf.removeFront());
         }
         positionRemoved = bookshelf.removeFront();
         for(int i = 0; i < removeFront/2; i++){
            bookshelf.addFront(removedBooks.get(i));
         }
      }
      //this conditional will perform operation for shortest removal from the back of bookshelf
      else{
         this.numOperations += removeBack;
         for(int i = getNumBooks()-1; i > position; i--){
            removedBooks.add(bookshelf.removeLast());
         }
         positionRemoved = bookshelf.removeLast();
         for(int i = removedBooks.size()-1; i>= 0 ; i--){
            bookshelf.addLast(removedBooks.get(i));
         }
      }
      //calculation to determine least amount of operations
      this.currOperations = removeFront <= removeBack? removeFront:removeBack;
      return this.numOperations;   // dummy code to get stub to compile
   }

   /**
    * Inserts book with specified height into the shelf.  Keeps the contained bookshelf sorted
    * after the insertion.
    *
    * Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    *
    * PRE: height > 0
    */
   public int putHeight(int height) {
      //first will detertmine index of where to insert height, then determine least amount of steps
      //will make use of 2 helper functions (removeFrontHelper() and removeBackHelper())
      int insertPos = 0; //variable used to determine where height will be inserted
      for(int i=0; i< getNumBooks(); i++){ //for loop will determine location of height nsertion
         if(bookshelf.getHeight(i) > height){
            insertPos = i;
            break;
         }
         insertPos++;
      }
      int insertOperations = 0; //keep track of number of operations needed to insert height

      if (insertPos == 0){
         bookshelf.addFront(height);//insert at the front of bookshelf
         insertOperations = 1;
      }
      else if(insertPos == bookshelf.size()){
         bookshelf.addLast(height); //insert at the end of bookshelf
         insertOperations = 1;
      }
      else{ //this condition will handle insert between (start, end) exclusive bookshelf positions
         //calculate number of operations needed to insert height
         insertOperations = insertPos <= bookshelf.size()/2? insertPos * 2 + 1:(bookshelf.size()-insertPos)*2+1;

         if (insertPos <= (bookshelf.size())/2){
            removeFrontHelper(insertPos, height);
         }
         else
            removeBackHelper(insertPos, height);
      }
      numOperations += insertOperations;//increment total operations sum
      currOperations = insertOperations; //increment current operations sum

      return insertOperations;   // dummy code to get stub to compile
   }

   /**
    * Returns the total number of calls made to mutators on the contained bookshelf
    * so far, i.e., all the ones done to perform all of the pick and put operations
    * that have been requested up to now.
    */
   public int getTotalOperations() {
      return this.numOperations;   //return bookshelfkeeper tracker of operations
   }

   /**
    * Returns the number of books on the contained bookshelf.
    */
   public int getNumBooks() {
      return bookshelf.size();
   }

   /**
    * Returns string representation of this BookshelfKeeper. Returns a String containing height
    * of all books present in the bookshelf in the order they are on the bookshelf, followed
    * by the number of bookshelf mutator calls made to perform the last pick or put operation,
    * followed by the total number of such calls made since we created this BookshelfKeeper.
    *
    * Example return string showing required format: “[1, 3, 5, 7, 33] 4 10”
    *
    */
   public String toString() {
      String retStr = bookshelf.toString() +  " " + currOperations + " " + getTotalOperations();
      return retStr;   // dummy code to get stub to compile

   }

   /**
    * Returns true iff the BookshelfKeeper data is in a valid state.
    * (See representation invariant comment for details.)
    */
   private boolean isValidBookshelfKeeper() {
      for(int i = 0; i< getNumBooks()-1; i++){
         if (bookshelf.getHeight(i) > this.bookshelf.getHeight(i+1))
            return false;
      }
      return true;  // dummy code to get stub to compile
   }

   // add any other private methods here
   //removeFrontHelper() will take insert posiiton and height and insert it into the bookshelf
   private void removeFrontHelper(int pos, int height){
      ArrayList<Integer> removedBooks = new ArrayList<Integer>();
      for(int i = 0; i< pos; i++){
         removedBooks.add(bookshelf.removeFront());
      }
      bookshelf.addFront(height);
      for(int i = removedBooks.size()-1; i >= 0; i--){
         bookshelf.addFront(removedBooks.get(i));
      }
   }

   //removeBackHelper() will take insert position and height and insert it into the bookshelf
   private void removeBackHelper(int pos, int height){
      ArrayList<Integer> removedBooks = new ArrayList<Integer>();
      for(int i = bookshelf.size()-1;  i >= pos; i--){
         removedBooks.add(bookshelf.removeLast());
      }
      bookshelf.addLast(height);
      for(int i = removedBooks.size()-1; i >= 0; i--){
         bookshelf.addLast(removedBooks.get(i));
      }
   }
}


