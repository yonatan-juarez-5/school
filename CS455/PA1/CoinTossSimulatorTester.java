//Unit tester for CoinTossSimulator.java
// Yonatan Juarez
// yjuarez
//Assignment: PA1
//Fall 2022
import java.util.Random;

public class CoinTossSimulatorTester
{
   public static void main(String[] args)
   {
      System.out.println("Hello from CoinTossSimulatorTest\n");
      CoinTossSimulator coinToss = new CoinTossSimulator();
      int sum = 0, trials = 0;
      
      //initial results after creating CoinTossSimulator() object
      System.out.println("After CoinTossSimulator constructor");
      System.out.println("Number of trials [exp: 0]: " + coinToss.getNumTrials());
      System.out.println("twoHeads: " + coinToss.getTwoHeads());
      System.out.println("twoTails: " + coinToss.getTwoTails());
      System.out.println("HeadTails: " + coinToss.getHeadTails());
      sum = coinToss.getTwoHeads() + coinToss.getTwoTails() + coinToss.getHeadTails();
      System.out.println("Tosses add up correctly? " + checkTrialSum(sum, coinToss.getNumTrials()));
      System.out.println();
      
      
      runSim(coinToss, 1);
      runSim(coinToss, 10);
      runSim(coinToss, 100);
      runSim(coinToss, 50);
      
      coinToss.reset();
      runSim(coinToss, 0);
      runSim(coinToss, 1001);
      
      coinToss.reset();
      Random randomTrial = new Random(); //run simulation with random number up to 100,000
      runSim(coinToss, randomTrial.nextInt(100001));
      
   }
   
   //static method used to check correct summation of  2xheads/ 2xtails/ and head/tails
   public static boolean checkTrialSum(int sum, int trials){
      if (sum == trials)
         return true;
      else
         return false;
   }
   //runSim method will print results from unit test
   public static void runSim(CoinTossSimulator coinToss, int trials){
      
      coinToss.run(trials);
      System.out.println("After run (" + trials + ")");
      System.out.println("Number of trials [exp: " + coinToss.getNumTrials() + "]: " + coinToss.getNumTrials());
      System.out.println("twoHeads: " + coinToss.getTwoHeads());
      System.out.println("twoTails: " + coinToss.getTwoTails());
      System.out.println("HeadTails: " + coinToss.getHeadTails());
      int sum = coinToss.getTwoHeads() + coinToss.getTwoTails() + coinToss.getHeadTails();
      System.out.println("Tosses add up correctly? " + checkTrialSum(sum, coinToss.getNumTrials()));
      System.out.println();
      
   }
}