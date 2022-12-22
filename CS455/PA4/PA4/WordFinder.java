// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CS 455 PA4
// Fall 2022

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

/**
 WordFinder class will take a possible paramter that is a dictionary if user chooses to
 use their own dictionary txt file. If program is run without argument, sowpods.txt will
 be the default dictionary used for determining possible word anagrams from user input.
 WordFinder will make use of AnagramDictionary, Rack, and ScoreTable objects to handle
 the different operations. Code will continuously ask user for a new rack after printing
 results for previous user input. Results will be printed with score:words in descending
 score, if user enters '.', the program will terminate. Exceptions will be thrown for
 dictionary files that are not found, and if dictionary file contains duplicate words.
 */
public class WordFinder {

    public static void main(String[] args) throws IOException {
        String dictFile = "sowpods.txt";
        String userInput="";
        //check if user is passing dictionary file argument
        if(args.length > 0){
            dictFile = args[0];
        }
        try{
            Scanner in = new Scanner(System.in);

            AnagramDictionary anagramDict = new AnagramDictionary(dictFile);
            ScoreTable scores = new ScoreTable();
            System.out.println("Type . to quit.");
            System.out.print("Rack? ");
            while(in.hasNextLine()){

                userInput = in.nextLine();
                if(userInput.equals(".")) {
                    in.close();
                    break;
                }
                else {
                    userInput = removeNonLetterCharacters(userInput);
                    Rack rack = new Rack(userInput);
                    ArrayList<String> wordsFound = rackHelper(rack, anagramDict);
                    System.out.println("We can make " + wordsFound.size() + " words from \"" + userInput + "\"");
                    if(wordsFound.size() >0){
                        printResults(scores, wordsFound);
                    }

                }
                System.out.print("Rack? ");
            }
        }
        catch(FileNotFoundException ex){
            //ex.getMessage()
            //throw new FileNotFoundException("ERROR: Dictionary file \"" + dictFile + "\" does not exist.\nExiting program.");
            System.out.println("ERROR: Dictionary file \"" + dictFile + "\" does not exist.\nExiting program.");
        }
        catch(IllegalDictionaryException ex){
            //ex.getMessage()
            //throw new FileNotFoundException("ERROR: Dictionary file \"" + dictFile + "\" does not exist.\nExiting program.");
            System.out.println("ERROR: Illegal dictionary: dictionary file has a duplicate word: " + ex.getMessage() +"\nExiting program.");
        }
    }
    //static helper method will setup anagrams using a rack containing the user's input
    //arrayList will be created to hold all possible words that can be constructed from rack input
    //param: rack object and anagramDictionary object
    //return: arraylist of words from rack
    public static ArrayList<String> rackHelper(Rack rack, AnagramDictionary dict){
        ArrayList<String> arr = rack.getRack();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> anagramList = new ArrayList<>();
        for(String i: arr){
            //System.out.println("rackHelper: "+i);
            anagramList = dict.getAnagramsOf(i);
            //System.out.println("rackHelper: "+anagramList);
            if(anagramList.size()>0)// != null)
                words.addAll(anagramList);

        }
        return words;
    }
    //static method used to print score results for user input
    //makes use of comparator class to sort how scores will be displayed
    //param: takes in a score table object, and arrayList of words
    //determines score for each word in arrayList using score table object
    public static void printResults(ScoreTable scores, ArrayList<String> words){
        System.out.println("All of the words with their scores (sorted by score):");

        Map<String, Integer> scoresMap = new TreeMap<>();
        for (String word: words) {
            scoresMap.put(word, scores.getScore(word));
        }
        ArrayList<Map.Entry<String, Integer>> scoresList = new ArrayList<>(scoresMap.entrySet());
        Collections.sort(scoresList, new SortScore());

        if(scoresList.size() >0){
            for(int i = 0; i < scoresList.size(); i++){
                System.out.println(scoresList.get(i).getValue() + ": " + scoresList.get(i).getKey());
            }
        }
    }
    //SoreScore: Comparator class used to sort by scores (value)
    static class SortScore implements Comparator<Map.Entry<String, Integer>>{
        public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b){
            return b.getValue() - a.getValue();
        }
    }
    //param: static method takes in string that possibly contains non letter characters
    //return: returns the string removing non-alphanumeric characters and numbers
    public static String removeNonLetterCharacters(String string) {
        String retStr="";
        for(int i=0; i < string.length(); i++){
            if(Character.isAlphabetic(string.charAt(i))){
                retStr += string.charAt(i);
            }
        }
        return retStr;
    }
}

