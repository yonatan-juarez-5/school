// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CS 455 PA4
// Fall 2022

import java.util.ArrayList;

/**
 ScoreTable class to handle the scoring of rack
 ScoreTable object will handle all possible combinations for each of the alphabet letters.
 For each word passed to the score table object, a score will be able to be determined for
 each word in the anagram list.
 */

public class ScoreTable {
   public static final int FIRST_POINT = 1;	 // A, E, I, O, U, L, N, S, T, R = 10 elements
   public static final int SECOND_POINT = 2; // D, G = 2 elements
   public static final int THIRD_POINT = 3;	 // B, C, M, P = 4 elements
   public static final int FOURTH_POINT = 4; // F, H, V, W, Y  = 5 elements
   public static final int FIFTH_POINT = 5;	 // K = 1 element
   public static final int EIGHTH_POINT = 8; // J, X = 2 elements
   public static final int TENTH_POINT = 10; // Q, Z = 2 elements
   public static final char FIRST_LETTER = 'A';
   public static final int NUM_LETTERS = 26;

   private int[] scoreTable;
   private int score;

   public ScoreTable(){
      scoreTable = new int[NUM_LETTERS];
      score = 0;
      //set each letter in array to its corresponding point value
      scoreTable['A' - FIRST_LETTER] = scoreTable['E'-FIRST_LETTER] = scoreTable['I'-FIRST_LETTER] =
              scoreTable['O' - FIRST_LETTER] = scoreTable['U' - FIRST_LETTER] = scoreTable['L' - FIRST_LETTER] =
                      scoreTable['N' - FIRST_LETTER] = scoreTable['S' - FIRST_LETTER] = scoreTable['T' - FIRST_LETTER] =
                              scoreTable['R' - FIRST_LETTER] = FIRST_POINT;

      scoreTable['D' - FIRST_LETTER] = scoreTable['G'-FIRST_LETTER] = SECOND_POINT;

      scoreTable['B' - FIRST_LETTER] = scoreTable['C'-FIRST_LETTER] = scoreTable['M' - FIRST_LETTER] =
              scoreTable['P'-FIRST_LETTER] = THIRD_POINT;

      scoreTable['F' - FIRST_LETTER] = scoreTable['H'-FIRST_LETTER] = scoreTable['V' - FIRST_LETTER] =
              scoreTable['W'-FIRST_LETTER] = scoreTable['Y' - FIRST_LETTER] = FOURTH_POINT;
      scoreTable['K' - FIRST_LETTER] = FIFTH_POINT;
      scoreTable['J' - FIRST_LETTER] = scoreTable['X'-FIRST_LETTER] = EIGHTH_POINT;
      scoreTable['Q' - FIRST_LETTER] = scoreTable['Z'-FIRST_LETTER] = TENTH_POINT;
   }

   public int getScore(String str){
      char ch;
      str = str.toUpperCase();
      score = 0;
      for(int i = 0; i < str.length(); i++){
         //ch = str.charAt(i);
         score += scoreTable[str.charAt(i) - FIRST_LETTER];
      }
      return score;
   }
}

