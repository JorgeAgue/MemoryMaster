import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Memorymaster {

    static JButton prevBtn = null;
    static String currentVal ="";

    static int lives =5;

    static final Icon back = new ImageIcon("src/back.png");

    public int getLives()
    {
        return lives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    //Randomizes the order of the icons and stores them in an array
    static ArrayList<String> assignButtons(List<JButton> buttons ){
        String[] icons= {"\uD83D\uDE42", "\uD83D\uDE80", "\uD83D\uDD25", "\uD83C\uDF19", "\uD83D\uDCB5", "\uD83D\uDD12", "\uD83D\uDDFF", "\uD83D\uDCE2"};
        //🙂, 🚀, 🔥, 🌙, 💵, 🔒, 🗿, 📢
        Integer[] counts ={0, 0, 0, 0, 0, 0, 0, 0};
        ArrayList<String> assignments = new ArrayList<String>();

        Random rand = new Random();
        int randNum= rand.nextInt(8);

        int done = 0;
        int i =0;

        while (done < 8)
        {
            if (counts[randNum] != 2){
                counts[randNum] += 1;
                assignments.add(icons[randNum]);
                //buttons.get(i).setText(icons[randNum]); //Shows solution
                i++;
                if (counts[randNum] == 2)
                {
                    done+=1;
                }
            }
            randNum = rand.nextInt(8);
        }
        return assignments;
    }

    static  void handleBtnClick(JButton button, ArrayList<String>  assignments) throws InterruptedException
    {

        JButton clickedBtn = button;
        clickedBtn.setEnabled(false);
        int assignedIndex= Integer.parseInt(clickedBtn.getName()); //Gets the index of button to access its assignment
        clickedBtn.setText(assignments.get(assignedIndex)); //Shows the letter its assigned

        JButton prevBtnTimeR= prevBtn; //Finally... The timer is soo bad

        Timer tmr= new Timer(500, ((ignored) -> { // Wait 500ms before re-enabling the buttons
           clickedBtn.setText("");
           clickedBtn.setEnabled(true);
           clickedBtn.setIcon(back);

           prevBtnTimeR.setText("");
           prevBtnTimeR.setEnabled(true);
           prevBtnTimeR.setIcon(back);

           if(lives == 0)
           {
               clickedBtn.setEnabled(false);
               prevBtnTimeR.setEnabled(false);
           }
        }));
        tmr.setRepeats(false);

        if (currentVal != "") //If 2nd button flipped
        {
            if(currentVal == clickedBtn.getText()) //Compare stored icon to 2nd button pressed if equal
            {
                System.out.println("Pair made");

                clickedBtn.setVisible(false);
                prevBtn.setVisible(false);
                //Remove both buttons from play

                currentVal = ""; //Reset the current value

            }
            else //Else stored icon and 2nd button not equal
            {
                System.out.println("No pair");
                tmr.start(); //Peform functions in timer after a delay

                currentVal = "";
                lives--;
                }
        }
        else //Else the 1st button
        {
            currentVal = clickedBtn.getText(); //Store the icon
        }
        prevBtn= clickedBtn;
    }
}
