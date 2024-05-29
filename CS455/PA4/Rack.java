// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CS 455 PA4
// Fall 2022

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

/**
 A Rack of Scrabble tiles
 Rack class will create a rack object of a word, and calculate
 all subsets of that string
 map will be used to  break hold the strings individual characters
 and keep track of each letter frequency. array will be created to keep
 track of frequency of each letter, and passed to allSubsets() to determine
 all possible word formations which will used to find all anagrams

 */

public class Rack {
   private ArrayList<String> rack;
   public Rack(String str){
      Map<Character, Integer> rackMap = new TreeMap<>();
      int temp = 0;
      for(int i = 0; i< str.length(); i++){
         if(!rackMap.containsKey(str.charAt(i))){
            rackMap.put(str.charAt(i), 1);
         }
         else{
            temp = rackMap.get(str.charAt(i))+1;
            rackMap.put(str.charAt(i), temp);
         }
      }
      //declare mult array used for allSubsets() method call
      int rackMapSize = rackMap.size();
      int[] multArr = new int[rackMapSize];

      int i = 0;
      String unique = "";
      Iterator<Map.Entry<Character, Integer>> itr = rackMap.entrySet().iterator();
      while(itr.hasNext()){
         Map.Entry<Character, Integer> entry = itr.next();
         unique = unique + entry.getKey();
         multArr[i] = entry.getValue();
         i++;
      }
      int k=0;
      rack = allSubsets(unique, multArr, k);

   }
   public ArrayList<String> getRack(){
      return rack;
   }
   /**
    Finds all subsets of the multiset starting at position k in unique and mult.
    unique and mult describe a multiset such that mult[i] is the multiplicity of the char
    unique.charAt(i).
    PRE: mult.length must be at least as big as unique.length()
    0 <= k <= unique.length()
    @param unique a string of unique letters
    @param mult the multiplicity of each letter from unique.
    @param k the smallest index of unique and mult to consider.
    @return all subsets of the indicated multiset.  Unlike the multiset in the parameters,
    each subset is represented as a String that can have repeated characters in it.
    @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();

      if (k == unique.length()) {  // multiset is empty
         allCombos.add("");
         return allCombos;
      }

      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k+1);

      // prepend all possible numbers of the first char (i.e., the one at position k)
      // to the front of each string in restCombos.  Suppose that char is 'a'...

      String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {
         for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets
            // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));
         }
         firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
      }

      return allCombos;
   }


}

