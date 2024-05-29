import java.util.ArrayList;
import java.io.*;

public class BookshelfTester {
   
   
   public static void main(String args[]){
      
      ArrayList<Integer> books = new ArrayList<Integer>();
      books.add(4);
      books.add(5);
      books.add(12);
      books.add(10);
      
      Bookshelf bookshelf_1 = new Bookshelf();
      System.out.println("Empty bookshelf: "+ bookshelf_1.toString());
      
      Bookshelf bookshelf_2 = new Bookshelf(books);
      
      System.out.println("Populated bookshelf: "+ bookshelf_2.toString() +"\n");
      
      bookshelf_2.addLast(10);
      System.out.println("Adding 10 to last: "+ bookshelf_2.toString());
      
      bookshelf_2.addFront(7);
      System.out.println("Adding 7 to front: " + bookshelf_2.toString());
      
      bookshelf_2.removeLast();
      System.out.println("Removing from last: " + bookshelf_2.toString());
      
      bookshelf_2.removeFront();
      System.out.println("Removing from front: " + bookshelf_2.toString()+ "\n");
      
      int sizeOfBookshelf = bookshelf_2.size();
      System.out.println("Bookshelf size: " + sizeOfBookshelf);
      
      System.out.println("Bookshelf.getHeight(pos = 2): " + bookshelf_2.getHeight(2));
      
      System.out.println("Bookshelf is sorted? " + bookshelf_2.isSorted());
      
      
   }
   
}


