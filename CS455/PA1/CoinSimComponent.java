/* Yonatan Juarez
 yjuarez
 PA1
 Fall 2022 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.Random;
import java.awt.Color;
import java.lang.Math;


public class CoinSimComponent extends JComponent{
   
   //constants for colors and width of results bar
   final Color HEAD_TAIL_COLOR = Color.GREEN;
   final Color HEAD_HEAD_COLOR = Color.RED;
   final Color TAIL_TAIL_COLOR = Color.BLUE;
   final int WIDTH = 50;
   
   private int callCount;
   private int numTrials; //number of trials passed from CoinSimViewer.java
   private int twoHeads;
   private int twoTails;
   private int headTails;
   
   private CoinTossSimulator coinToss;
   
   public CoinSimComponent(int trials)
   {
      this.callCount = 0;
      this.numTrials = trials;

      //create coin toss simulation
      this.coinToss = new CoinTossSimulator();
      this.coinToss.run(this.numTrials);
   }
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      //debug print statements
      callCount++;
      System.out.println("Called paintComponent(" + callCount + ")");
      
      //get results that will be used for bar parameters ands scaling calculations
      this.twoHeads = this.coinToss.getTwoHeads();
      this.twoTails = this.coinToss.getTwoTails();
      this.headTails = this.coinToss.getHeadTails();
      this.numTrials = this.coinToss.getNumTrials();
      
      int maxHeight = 0; //var used for scaling
      int vb = 25; //gap between top/bottom of tallest bar
      
      //if statements to find maxHeight for scaling
      if(twoHeads >= twoTails && twoHeads >= headTails){
         maxHeight = twoHeads;
      }
      else if (twoTails >= twoHeads && twoTails >= headTails){
         maxHeight = twoTails;
      }
      else{
         maxHeight = headTails;
      }
      
      //scale is calculated using the height of the frame  and subtracting the vb margin from the top/bottom of bar
      //and then divided by the number of trials entered by user to scale appropriately
      double scale = (double)(getHeight() - 2*vb)/this.numTrials;
      
      //hh_per will hold the percentage of 2heads
      int hh_per = (int)Math.round(((double)twoHeads/this.numTrials)*100);
      String headsheads = "Two Heads: " + twoHeads + " (" + hh_per + "%)";
      //System.out.println(headsheads);
      
      //tt_per will hold the percentage of 2tails
      int tt_per = (int)Math.round(((double)twoTails/this.numTrials)*100);
      String tailstails = "Two Tails: " + twoTails + " (" + tt_per + "%)";
      //System.out.println(tailstails);
      
      //ht_per will hold the percentage of headstails
      int ht_per = (int)Math.round(((double)headTails/this.numTrials)*100);
      String headstails = "A Head and a Tail: " + headTails + " (" + ht_per + "%)";
      //System.out.println(headstails);
      
      //Bar(int bottom, int left, int width, int applicationHeight, double scale, Color color, String label)
      
      //x & y will be used to scale bars when jframe is resized
      int x = getWidth();
      int y = getHeight();
      
      int twoHeadsHeight = (int)Math.round(twoHeads*scale); //var will be used to get scaled bar height
      int bottom_hh = (int)Math.round(y-twoHeadsHeight-vb); //will determine location of bar along y-axis
      
      //create 2x heads bar
      Bar head_2x_bar = new Bar(bottom_hh, (x/4)-(WIDTH/2), WIDTH, twoHeadsHeight, scale, HEAD_HEAD_COLOR, headsheads);
      head_2x_bar.draw(g2);
      
      int headTailsHeight = (int)Math.round(headTails*scale); //var will be used to get scaled bar height
      int bottom_ht = (int)Math.round(y-headTailsHeight-vb); //will determine location of bar along y-axis
      
      //create 2x tails bar
      Bar head_tails_bar = new Bar(bottom_ht, 2*(x/4)-(WIDTH/2), WIDTH, headTailsHeight, scale, HEAD_TAIL_COLOR, headstails);
      head_tails_bar.draw(g2); 
      
      //create head/tails bar
      int twoTailsHeight = (int)Math.round(twoTails*scale); //var will be used to get scaled bar height
      int bottom_tt = (int)Math.round(y-twoTailsHeight-vb); //will determine location of bar along y-axis
      
      Bar tails_2x_bar = new Bar(bottom_tt, 3*(x/4)-(WIDTH/2), WIDTH, twoTailsHeight, scale, TAIL_TAIL_COLOR, tailstails);
      tails_2x_bar.draw(g2);
      
      
      
   }
   
   
   
   
}