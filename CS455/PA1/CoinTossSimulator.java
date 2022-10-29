// Name:Yonatan Juarez
// USC NetID: 9778 5289 75
// CS 455 PA1
// Fall 2022

/**
 * class CoinTossSimulator
 *
 * Simulates trials of repeatedly tossing two coins and allows the user to access the
 * cumulative results.
 *
 * NOTE: we have provided the public interface for this class.  Do not change
 * the public interface.  You can add private instance variables, constants,
 * and private methods to the class.  You will also be completing the
 * implementation of the methods given.
 *
 * Invariant: getNumTrials() = getTwoHeads() + getTwoTails() + getHeadTails()
 *
 */
import java.util.Random;

public class CoinTossSimulator {

   private int numTrials;
   private int twoHeads;
   private int twoTails;
   private int headTails;
   /**
    Creates a coin toss simulator with no trials done yet.
    */
   public CoinTossSimulator() {
      this.numTrials = 0; //initialize numTrials attribute

   }

   /**
    Runs the simulation for numTrials more trials. Multiple calls to this method
    without a reset() between them *add* these trials to the current simulation.

    @param numTrials  number of trials to for simulation; must be >= 1
    */
   public void run(int numTrials) {
      Random coinToss = new Random();//create random num generator

      //check whether input is valid
      if (numTrials < 1)
         System.out.println("Invalid input for trials!");
      else{
         for(int i = 0; i < numTrials; i++){
            this.numTrials++;

            //variables will used to get 2 random numbers (0 or 1)
            int n1 = coinToss.nextInt(2);
            int n2 = coinToss.nextInt(2);

            //conditional statements used to determine count for each result
            if (n1 == 0 && n2 == 0) //0 -> tails
               twoTails++;
            else if (n1 == 1 && n2 == 1) // 1 -> heads
               twoHeads++;
            else
               headTails++;
         }
      }
   }

   /**
    Get number of trials performed since last reset.
    */
   public int getNumTrials() {
      return numTrials; // DUMMY CODE TO GET IT TO COMPILE
   }

   /**
    Get number of trials that came up two heads since last reset.
    */
   public int getTwoHeads() {
      return twoHeads; // DUMMY CODE TO GET IT TO COMPILE
   }

   /**
    Get number of trials that came up two tails since last reset.
    */
   public int getTwoTails() {
      return twoTails; // DUMMY CODE TO GET IT TO COMPILE
   }

   /**
    Get number of trials that came up one head and one tail since last reset.
    */
   public int getHeadTails() {
      return headTails; // DUMMY CODE TO GET IT TO COMPILE
   }

   /**
    Resets the simulation, so that subsequent runs start from 0 trials done.
    */
   public void reset() {
      System.out.println("Resetting Coin Toss Simulation");
      this.numTrials = 0;
      this.twoHeads = 0;
      this.twoTails = 0;
      this.headTails = 0;
   }

}

