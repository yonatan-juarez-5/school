//Name: Yonatan Juarez
//usc net ID: yjuarez
//Assignment: PA1
// Fall 2022
import javax.swing.JFrame;
import java.util.Scanner;

public class CoinSimViewer
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      boolean check = true;
      int trials = 0;
      int sum = 0;
      //while loop will used to error-check user input
      while (check == true){
         System.out.print("Enter number of trials: ");
         trials = in.nextInt();
         if (trials <= 0)
            System.out.println("ERROR: Number entered must be greater than 0.");
         else
            check = false;
      }
      
      //System.out.println("Succesful input!!");
      
      JFrame frame = new JFrame();

      frame.setSize(800, 500); //500 x 800 
      frame.setTitle("CoinSimViewer");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      CoinSimComponent component = new CoinSimComponent(trials);
      frame.add(component);

      frame.setVisible(true);
      
   }
}