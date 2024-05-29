// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CS 455 PA3
// Fall 2022

import java.util.Random;
import java.util.Arrays;
/**
 MineField
 class with locations of mines for a game.
 This class is mutable, because we sometimes need to change it once it's created.
 mutators: populateMineField, resetEmpty
 includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {

    // <put instance variables here>
    private int num_columns; //keep track of # of columns
    private int num_rows; //keep track of # of rows
    private int num_mines; //keep track of # of mines
    private int adj_mines; //# of adjmines to a mine field location
    private boolean[][] mine_field; //boolean 2-d array used for mine field
    private Random rnd;//Random object used to generate random mine placement


    /**
     Create a minefield with same dimensions as the given array, and populate it with the mines in the array
     such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
     this minefield will corresponds to the number of 'true' values in mineData.
     @param mineData  the data for the mines; must have at least one row and one col,
     and must be rectangular (i.e., every row is the same length)
     */
    public MineField(boolean[][] mineData) {
        //get # of rows/cols using mineData dimensions
        num_rows = mineData.length;
        num_columns = mineData[0].length;
        mine_field = new boolean[num_rows][num_columns];

        //initialize mine field and avoid making defensive copy
        initializeMineField(num_rows, num_columns, mineData);
        num_mines = getNumMinesFromConstructor(mine_field);

        adj_mines = 0; // set # of adj_mines to 0 at start of game
    }

    /**
     Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once
     populateMineField is called on this object).  Until populateMineField is called on such a MineField,
     numMines() will not correspond to the number of mines currently in the MineField.
     @param numRows  number of rows this minefield will have, must be positive
     @param numCols  number of columns this minefield will have, must be positive
     @param numMines   number of mines this minefield will have,  once we populate it.
     PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations).
     */
    public MineField(int numRows, int numCols, int numMines) {
        //initialize attributes for mine field... rows/cols/mines
        num_rows = numRows;
        num_columns = numCols;
        num_mines = numMines;
        //create new mine field using rows/cols/mines passed in constructor
        mine_field = new boolean[numRows][numCols];

        adj_mines = 0;
        emptyMineField(num_rows, num_columns, mine_field);
    }

    /**
     Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
     ensuring that no mine is placed at (row, col).
     @param row the row of the location to avoid placing a mine
     @param col the column of the location to avoid placing a mine
     PRE: inRange(row, col) and numMines() < (1/3 * numRows() * numCols())
     */
    public void populateMineField(int row, int col) {
        rnd = new Random();
        int temp_row= 0;
        int temp_col= 0;
        int temp_mines = numMines();

        //clear all current mines in the mine field
        resetEmpty();

        //add numMines() to minefield
        while(temp_mines > 0){
            temp_row = rnd.nextInt(numRows());
            temp_col = rnd.nextInt(numCols());

            // check whether random location is the same as passed in parameter and if location has mine already
            if(temp_row != row && temp_col != col && mine_field[temp_row][temp_col] != true){
                mine_field[temp_row][temp_col] = true;
                temp_mines--;
            }
        }
    }

    /**
     Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
     Thus, after this call, the actual number of mines in the minefield does not match numMines().
     Note: This is the state a minefield created with the three-arg constructor is in
     at the beginning of a game.
     */
    public void resetEmpty() {
        //reset each mine field location to non-mine state
        for(int row = 0; row< num_rows; row++){
            for(int col = 0; col < num_columns; col++){
                mine_field[row][col] = false;
            }
        }
    }

    /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
     */
    public int numAdjacentMines(int row, int col) {
        adj_mines = 0;
        //making use of inRange() and hasMine() we will check 8 possible locations
        //only when indices are valid
        //this will help avoid any out of range index errors
        //using inRange() will help cover the cases where are you
        //checking the boundary and corner of the mine field
        if(inRange(row-1, col) && hasMine(row-1,col)){ adj_mines++;}

        if(inRange(row-1, col+1) && hasMine(row-1, col+1)){ adj_mines++; }

        if(inRange(row, col+1) && hasMine(row, col+1)){ adj_mines++; }

        if(inRange(row+1, col+1) && hasMine(row+1, col+1)){ adj_mines++; }

        if(inRange(row+1, col) && hasMine(row+1, col)){ adj_mines++; }

        if(inRange(row+1, col-1) && hasMine(row+1, col-1)){ adj_mines++; }

        if(inRange(row, col-1) && hasMine(row, col-1)){ adj_mines++; }

        if(inRange(row-1, col-1) && hasMine(row-1, col-1)){ adj_mines++;}

        return adj_mines;
    }

    /**
     Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
     start from 0.
     @param row  row of the location to consider
     @param col  column of the location to consider
     @return whether (row, col) is a valid field location
     */
    public boolean inRange(int row, int col) {
        //if row/col < 0 and row/col > numRows/numCols
        //return false since it will be out range
        if((row < num_rows && row >= 0) && (col < num_columns && col >= 0)){
            return true;
        }

        return false;
    }

    /**
     Returns the number of rows in the field.
     @return number of rows in the field
     */
    public int numRows() {
        return num_rows;//# of rows in mine field
    }

    /**
     Returns the number of columns in the field.
     @return number of columns in the field
     */
    public int numCols() {
        return num_columns; //# of columns in mine field
    }


    /**
     Returns whether there is a mine in this square
     @param row  row of the location to check
     @param col  column of the location to check
     @return whether there is a mine in this square
     PRE: inRange(row, col)
     */
    public boolean hasMine(int row, int col) {
        //if mine field location is true, then there is a mine there
        if(mine_field[row][col] == true){
            return true;
        }

        return false;
    }

    /**
     Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
     some of the time this value does not match the actual number of mines currently on the field.  See doc for that
     constructor, resetEmpty, and populateMineField for more details.
     * @return
     */
    public int numMines() {
        return num_mines;//# of mines in mine field
    }
    //toString() method used for debugging purposes
    public String toString(){

        return Arrays.deepToString(mine_field);
    }

    // <put private methods here>
    //helper method to avoid making a defensive copy of mine field
    private void initializeMineField(int numRow, int numCol, boolean[][] mineData){
        for(int row = 0; row < numRow; row++){
            for(int col = 0; col < numCol; col++){
                mine_field[row][col] = mineData[row][col];
                if(hasMine(row, col) == true){
                    num_mines++;
                }
            }
        }
    }
    //private helper method to create an empty mine field with paramters from 3-parameter constructor
    //initiliaze mine field to have no mines
    private void emptyMineField(int rows, int cols, boolean[][] mf){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                mine_field[row][col] = false;
            }
        }
    }
    //helper method used to determine number of mines in a mine field that
    // is passed in 1-parameter constructor
    private int getNumMinesFromConstructor(boolean[][] mf){
        int mines = 0;

        for(int i = 0; i < mf.length; i++){
            for(int j = 0; j < mf[0].length; j++){
                if(mf[i][j] == true){
                    mines++;
                }
            }
        }
        return mines;
    }
}



