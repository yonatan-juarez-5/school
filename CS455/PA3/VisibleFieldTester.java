public class VisibleFieldTester {

   public static void main(String[] args) {

      boolean arr[][] = {
       {false, false, false, false},
       {false, false, false, false},
       {false, false, true, true},
       {false, false, true, false} };

      MineField mf = new MineField(arr);
      VisibleField vf = new VisibleField(mf);
      System.out.println(vf.getMineField());
      System.out.println(vf.isGameOver());
      System.out.println(vf.getStatus(2,3));
      /*
      System.out.println("numMines() - " + mf.numMines());
      System.out.println("numRows() - " + mf.numRows());
      System.out.println("numCols() - " + mf.numCols());

      System.out.println("inRange(3,4)? " + mf.inRange(3,4));
      System.out.println("inRange(2,3)? " + mf.inRange(2,3));

      System.out.println("hasMine(2,3)? " + mf.hasMine(2,3));
      System.out.println("hasMine(1,3)? " + mf.hasMine(1,3));

      System.out.println("mf.toString()? " + mf.toString());
      System.out.println("AdjMines(3,3)? " + mf.numAdjacentMines(3,3));
      System.out.println();
        */
      //Reset initialized mine field that used 1-parameter constructor
      /*
      mf.resetEmpty();
      System.out.println("*After resetEmpty()");

      System.out.println("numMines() - " + mf.numMines());
      System.out.println("numRows() - " + mf.numRows());
      System.out.println("numCols() - " + mf.numCols());

      System.out.println("inRange(3,4)? " + mf.inRange(3,4));
      System.out.println("inRange(2,3)? " + mf.inRange(2,3));

      System.out.println("hasMine(2,3)? " + mf.hasMine(2,3));
      System.out.println("hasMine(1,3)? " + mf.hasMine(1,3));

      System.out.println("mf.toString() - " + mf.toString());
      System.out.println();

      //create mine field with 3-parameter constructor

      MineField newMf = new MineField(5,5, 6);
      System.out.println("*After 3-paramter MineField()");
      System.out.println("numMines() - " + newMf.numMines());
      System.out.println("numRows() - " + newMf.numRows());
      System.out.println("numCols() - " + newMf.numCols());

      System.out.println("inRange(3,4)? " + newMf.inRange(3,4));
      System.out.println("inRange(2,3)? " + newMf.inRange(2,3));

      System.out.println("hasMine(2,3)? " + newMf.hasMine(2,3));
      System.out.println("hasMine(1,3)? " + newMf.hasMine(1,3));

      System.out.println("mf.toString() - " + newMf.toString());
      System.out.println();

      //populate mine field
      newMf.populateMineField(0,4);
      System.out.println("After populateMineField():");
      System.out.println("mf.toString() - " + newMf.toString());

      System.out.println("numMines() - " + newMf.numMines());
      System.out.println("numRows() - " + newMf.numRows());
      System.out.println("numCols() - " + newMf.numCols());

      System.out.println("inRange(3,4)? " + newMf.inRange(3,4));
      System.out.println("inRange(2,3)? " + newMf.inRange(2,3));

      System.out.println("hasMine(2,3)? " + newMf.hasMine(2,3));
      System.out.println("hasMine(1,3)? " + newMf.hasMine(1,3));
        */



   }

}