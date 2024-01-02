import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

public class Main {

    static void endGame(List<JButton> buttons)
    {
        for (JButton button: buttons)
        {
            button.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        Memorymaster mem= new Memorymaster();
        List<JButton> buttons = new ArrayList<JButton>();
        ArrayList<String>  assignments = Memorymaster.assignButtons(buttons);

        JFrame frame = new JFrame("Memory Master");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setFocusable(true);

       // JPanel panel = new JPanel();
        frame.setLayout(new GridLayout(5,5));

        Font sfont =new Font("Arial", Font.PLAIN, 40);
        UIManager.put("Button.font", sfont);
        UIManager.put("Label.font", sfont);

       JLabel l1 = new JLabel("Tries: " + mem.getLives());
       JLabel l2 = new JLabel("");
       JLabel l3 = new JLabel("");
       JLabel l4 = new JLabel("");

        for(int i = 0; i < 16; i++) {
            JButton button = new JButton();
            buttons.add(button);
        }

        //b1.setPreferredSize(new Dimension(100, 30));
        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);

        int i = 0;
        for (JButton currbutton : buttons)
        {
            frame.add(currbutton);
            currbutton.setName(String.valueOf(i));
            currbutton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try {
                        Memorymaster.handleBtnClick(currbutton, assignments);
                        l1.setText("Tries: " + mem.getLives());
                        if(mem.getLives() ==0)
                        {
                            endGame(buttons);
                        }
                    }
                    catch (InterruptedException ex) {
                        System.out.println("Interrupted Exception");
                    }
                }
            });
            i++;
        }

        frame.setVisible(true);

    }
}
