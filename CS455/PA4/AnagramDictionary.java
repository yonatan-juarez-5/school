// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CS 455 PA4
// Fall 2022

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;


/**
 A dictionary of all anagram sets.
 Note: the processing is case-sensitive; so if the dictionary has all lower
 case words, you will likely want any string you test to have all lower case
 letters too, and likewise if the dictionary words are all upper case.
 */
public class AnagramDictionary {
   private Map<String, ArrayList<String>> anagramDictionary;
   private File inputFile;
   /**
    Create an anagram dictionary from the list of words given in the file
    indicated by fileName.
    @param fileName  the name of the file to read from
    @throws FileNotFoundException  if the file is not found
    @throws IllegalDictionaryException  if the dictionary has any duplicate words
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException,
           IllegalDictionaryException {
      anagramDictionary = new HashMap<String, ArrayList<String>>();
      Set<String> uniqueSet = new HashSet<>();
      inputFile = new File(fileName);
      Scanner in = null;
      in = new Scanner(inputFile);
      while (in.hasNext()) {

         String word = in.next();
         if(uniqueSet.contains(word)) {
            //throw exception if there are duplicate words in dictionary file
            throw new IllegalDictionaryException(word);
         }
         else {
            uniqueSet.add(word);
            String sortedWord = sortHelper(word);

            if (anagramDictionary.get(sortedWord) == null) {
               ArrayList<String> anagramList = new ArrayList<>();
               anagramDictionary.put(sortedWord, anagramList);
            }
            anagramDictionary.get(sortedWord).add(word);
         }
      }
   }

   /**
    Get all anagrams of the given string. This method is case-sensitive.
    E.g. "CARE" and "race" would not be recognized as anagrams.
    @param  string to process
    @return a list of the anagrams of s
    */
   public ArrayList<String> getAnagramsOf(String string) {
      if (anagramDictionary.get(sortHelper(string)) == null){
         ArrayList<String> res = new ArrayList<>();
         return res;
      }
      return anagramDictionary.get(sortHelper(string));
   }

   //private helper used to convert string input into a
   //array of characters, and sort the characters
   //and return a string version of that char array
   private String sortHelper(String str){
      char[] charArr = str.toCharArray();
      //sort char arr, then return string version of char array
      Arrays.sort(charArr);
      return new String(charArr);
   }

}

