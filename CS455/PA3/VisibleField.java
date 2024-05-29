// Name:Yonatan Juarez
// USC NetID: 9778 5289 75
// CS 455 PA3
// Fall 2022


/**
 VisibleField class
 This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
 user can see about the minefield). Client can call getStatus(row, col) for any square.
 It actually has data about the whole current state of the game, including
 the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
 It also has mutators related to actions the player could do (resetGameDisplay(), cycleGuess(), uncover()),
 and changes the game state accordingly.

 It, along with the MineField (accessible in mineField instance variable), forms
 the Model for the game application, whereas GameBoardPanel is the View and Controller in the MVC design pattern.
 It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from
 outside this class via the getMineField accessor.
 */
public class VisibleField {
   // ----------------------------------------------------------
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method
   // getStatus(row, col)).

   // The following are the covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // The following are the uncovered states (all non-negative values):
   public static final int ZERO_ADJ_MINES = 0; //this const will be used to check when a square has no adjacent mines
   // values in the range [0,8] corresponds to number of mines adjacent to this opened square
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------

   // <put instance variables here>
   private MineField mineField; //MineField member variable used to handle mine/non-mine locations
   private int minesRemaining; //will keep track of the mines remaining in visible field
   private int numRows; //# of rows in mine field
   private int numCols; //$ of columns in mine field
   private int[][] mineFieldStates; //2-d array will be used to keep track of the state of each square
   private int squaresCovered; //# of squares covered in the minefield throughout the game
   private int adjMines; //keep track of # of adjacent mines to square of interest

   /**
    Create a visible field that has the given underlying mineField.
    The initial state will have all the locations covered, no mines guessed, and the game
    not over.
    @param mineField  the minefield to use for for this VisibleField
    */
   public VisibleField(MineField mineField) {
      //copy reference of minefield to mine field member variable in order to maintain changes
      // between visible field and GBP
      this.mineField = mineField;
      //initialize numRows, numCols, and minesRemaining
      numRows = mineField.numRows();
      numCols = mineField.numCols();
      minesRemaining = mineField.numMines();
      //create new 2-d array with the dimensions of mine field
      mineFieldStates = new int[numRows][numCols];
      //get number of squares that need to be uncovered (row*cols - mines) in order to win
      squaresCovered = numRows * numCols- mineField.numMines();

      //private helper method that will be used to initialize minefieldStates[][] to COVERED state
      initializeMineFieldStates();
   }

   /**
    Reset the object to its initial state (see constructor comments), using the same underlying
    MineField.
    */
   public void resetGameDisplay() {
      //call private helper method to reset squares to COVERED state
      initializeMineFieldStates();
   }
   /**
    Returns a reference to the mineField that this VisibleField "covers"
    @return the minefield
    */
   public MineField getMineField() {
      return mineField; //return mineField attribute
   }

   /**
    Returns the visible status of the square indicated.
    @param row  row of the square
    @param col  col of the square
    @return the status of the square at location (row, col).  See the public constants at the beginning of the class
    for the possible values that may be returned, and their meanings.
    PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {
      return mineFieldStates[row][col]; //return the state of the square at [row][col] location
   }

   /**
    Returns the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
    or not.  Just gives the user an indication of how many more mines the user might want to guess.  This value will
    be negative if they have guessed more than the number of mines in the minefield.
    @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      int minesGuessed=0;
      //iterate through mineFieldStates[][] to determine how many mine guesses have been made
      for(int row = 0; row < numRows; row++){
         for(int col = 0; col < numCols; col++){
            if(mineFieldStates[row][col] == MINE_GUESS) { minesGuessed++; }
         }
      }
      //return the difference between minesRemaining and minesGuessed
      //will be used to display in GBP
      return minesRemaining-minesGuessed;
   }

   /**
    Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
    changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
    changes it to COVERED again; call on an uncovered square has no effect.
    @param row  row of the square
    @param col  col of the square
    PRE: getMineField().inRange(row, col)
    */
   public void cycleGuess(int row, int col) {

      if(mineFieldStates[row][col] == COVERED){
         //COVERED -> MINE_GUESS
         mineFieldStates[row][col] = MINE_GUESS;
      }

      else if(mineFieldStates[row][col] == MINE_GUESS){
         //MINE_GUESS -> QUESTION
         mineFieldStates[row][col] =  QUESTION;
      }

      else if(mineFieldStates[row][col] == QUESTION){
         //QUESTION -> COVERED
         mineFieldStates[row][col] = COVERED;
      }
   }

   /**
    Uncovers this square and returns false iff you uncover a mine here.
    If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in
    the neighboring area that are also not next to any mines, possibly uncovering a large region.
    Any mine-adjacent squares you reach will also be uncovered, and form
    (possibly along with parts of the edge of the whole field) the boundary of this region.
    Does not uncover, or keep searching through, squares that have the status MINE_GUESS.
    Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
    or a loss (opened a mine).
    @param row  of the square
    @param col  of the square
    @return false   iff you uncover a mine at (row, col)
    PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {

      if(mineField.hasMine(row, col) == true){
         //if square location [row][col] has a mine, return false and set location to EXPLODED_MINE state
         mineFieldStates[row][col] = EXPLODED_MINE;
         return false;
      }
      else{
         //helper method will be used to perform recursive uncover of neighboring squares
         uncoverRecursiveHelper(row, col);
         return true;
      }
   }

   /**
    Returns whether the game is over.
    (Note: This is not a mutator.)
    @return whether game has ended
    */
   public boolean isGameOver() {
      int uncoveredSquared = 0;//keep track of the # of uncovered squares
      for(int row = 0; row < numRows; row++){
         for(int col = 0; col < numCols; col++){
            if(mineFieldStates[row][col] == EXPLODED_MINE){
               //game is over bc of an EXPLODED_MINE state
               //gameOverLose() helper function will display mines location after game is over
               gameOverLose();
               return true;
            }
            else if(isUncovered(row, col) == true){
               //if square location is uncovered, increased the count of uncoveredSquare
               uncoveredSquared++;
            }
         }
      }
      //after checking status of mine field
      //check whether # of covered squares == # of mines
      if(squaresCovered == uncoveredSquared){
         //gameOverWin() helper function will display mien locations are mine_guesses
         gameOverWin();
         return true;
      }
      //return false and continue game sinco no win/lose condition has been met
      return false;
   }

   /**
    Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states,
    vs. any one of the covered states).
    @param row of the square
    @param col of the square
    @return whether the square is uncovered
    PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
      //if square [row][col] location is COVERED/MINE_GUESS/QUESTION, then square is covered
      //else square is uncovered
      if (mineFieldStates[row][col] <= COVERED){
         return false;
      }
      else{
         return true;
      }
   }

   // <put private methods here>
   //private method used to initialize minefield states to COVERED state
   //set each square location to COVERED constant
   //this method will be used by VF constructor and resetGameDisplay()
   private void initializeMineFieldStates(){

      for(int row = 0; row < numRows; row++){
         for(int col = 0; col < numCols; col++){
            mineFieldStates[row][col] = COVERED;
         }
      }
   }

   //this recursive helper will help uncover squares with adjacent/no-adjacent mines
   //if any of the following conditions are true, no squares will uncovered
   // 1->(row, col) is not inRange (can come from recursive part where you check surrounding squares),
   // 2-> (row,col) location has a mine_guess, do not uncover here
   // 3-> (row, col) location is already uncovered, do nothing
   private void uncoverRecursiveHelper(int row, int col){
      adjMines = mineField.numAdjacentMines(row, col);//get number of adj_mines in mine_field

      if(!mineField.inRange(row, col) || getStatus(row,col) == MINE_GUESS || isUncovered(row, col)){
         return; //do not perform any recursive calls
      }

      else if(adjMines == ZERO_ADJ_MINES){
         //uncover neighboring squares since there are no adjacent mines
         mineFieldStates[row][col] = 0;

         //perform 8 recursive calls at (row,col) location
         uncoverRecursiveHelper(row-1, col-1);
         uncoverRecursiveHelper(row-1, col);
         uncoverRecursiveHelper(row-1, col+1);

         uncoverRecursiveHelper(row, col+1);
         uncoverRecursiveHelper(row, col-1);

         uncoverRecursiveHelper(row+1, col+1);
         uncoverRecursiveHelper(row+1, col);
         uncoverRecursiveHelper(row+1, col-1);
         return;
      }
      else{
         //set state of (row,col) location to the # of adj_mines next to it
         mineFieldStates[row][col] = adjMines;//current square will display # of adjacent mines
         return;
      }
   }

   //gameOverLose() helper function will be used to uncover mines in covered/question states
   // and set state at that location to a mine stat and if a square has an incorrect mine_guess,
   // then update state of that square to incorrect_guess
   //will only be called in isGameOver() method
   private void gameOverLose(){
      for(int row = 0; row < numRows; row++){
         for(int col = 0; col < numCols; col++){
            //check whether there is a mine in covered/question square and uncover it
            if(mineField.hasMine(row, col) && (mineFieldStates[row][col] == COVERED || mineFieldStates[row][col] == QUESTION)){
               mineFieldStates[row][col] = MINE;
            }
            //check if there is a mine guess in square, and there is actually no mine at [row][col] location
            //change field state to INCORRECT_GUESS
            else if(mineField.hasMine(row, col) == false && mineFieldStates[row][col] == MINE_GUESS){
               mineFieldStates[row][col] = INCORRECT_GUESS;
            }
         }
      }
   }
   //gameOverWin() helper will be used to determine if a square location has a mine, and will change its state to
   //mine_guess during a win condition
   //will only be called in isGameOver() method
   private void gameOverWin(){
      //check mine location and if user has set any incorrect mine_guess
      for(int row = 0; row < numRows; row++) {
         for (int col = 0; col < numCols; col++) {
            if(mineField.hasMine(row, col) == true){
               mineFieldStates[row][col] = MINE_GUESS;
            }

         }
      }
   }

}


