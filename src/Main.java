import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.util.Random;

public class Main {
    static ArrayList<String> assignments;
    static void endGame(List<JButton> buttons, boolean hasWon)
    {
        if(!hasWon) {
            int i = 0;
            for (JButton button : buttons) {
                button.setEnabled(false);
                button.setIcon(null);
                button.setText(assignments.get(i));
                //Fix a bug where the two previous buttons are not shown
                i++;
            }
            System.out.println("You lost");
            JOptionPane.showMessageDialog(null, "You lose...", "Game has ended", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        else {
            System.out.println("You won");
            JOptionPane.showMessageDialog(null, "You won!", "Game has ended", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    static void startGame(List<JButton> buttons, Icon back) //Reveal some of the cards at the beginning of the game
    {
        Random rand = new Random();
        int randNum= rand.nextInt(16);
        ArrayList<Integer>  randNums = new ArrayList<>();

        for(int i= 0; i <4; i++)
        {
            if(randNums.contains(randNum))
            {
                while(randNums.contains(randNum)) {
                    randNum = rand.nextInt(16);
                }
            }
                buttons.get(randNum).setEnabled(false);
                buttons.get(randNum).setIcon(null);
                buttons.get(randNum).setText(assignments.get(randNum));
                randNums.add(randNum);
                randNum= rand.nextInt(15);
        }
        Timer tmr= new Timer(1000, ((ignored) -> { // Wait 500ms before re-enabling the buttons
            for ( int num: randNums)
            {
                buttons.get(num).setText("");
                buttons.get(num).setIcon(back);
                buttons.get(num).setEnabled(true);
            }
        }));
        tmr.setRepeats(false);
        tmr.start();
    }

    static void restartGame(List<JButton> buttons, Icon back, int tries)
    {
        Memorymaster mem= new Memorymaster();
        mem.setLives(tries);
        //Need to fix the reset lives counter only updating after clicking
        assignments = Memorymaster.assignButtons(buttons);
        for (JButton currbutton : buttons)
        {
            currbutton.setIcon(back); //Icon on most recently clicked is slanted after restart
            currbutton.setVisible(true);
            currbutton.setEnabled(true);
        }
        startGame(buttons, back);
    }
    public static void main(String[] args) {
        Memorymaster mem= new Memorymaster();
        List<JButton> buttons = new ArrayList<JButton>();
        assignments = Memorymaster.assignButtons(buttons);
       int tries= 5;

        JFrame frame = new JFrame("Memory Master");
        frame.getContentPane().setBackground(new java.awt.Color(49, 150, 48));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setFocusable(true);

       // JPanel panel = new JPanel();
        frame.setLayout(new GridLayout(5,5));

        Font bfont =new Font("", Font.PLAIN, 60);
        Font lfont =new Font("Times New Roman", Font.BOLD, 50);
        Icon back = new ImageIcon("src/back.png");

        UIManager.put("Button.font", bfont);
        UIManager.put("Label.font", lfont);

       JLabel l1 = new JLabel("Tries: " + mem.getLives());
       l1.setForeground(Color.WHITE);
       JLabel l2 = new JLabel("");
       JLabel l3 = new JLabel("");
       JLabel l4 = new JLabel("");

       //Add the buttons to the frame
       for(int i = 0; i < 16; i++) {
            JButton button = new JButton();
            button.setIcon(back);
            button.setBackground(Color.WHITE);
            buttons.add(button);
        }
        JMenuBar menuBar = new JMenuBar();

        JMenu  menu= new JMenu("Options");
        JMenu  difmenu= new JMenu("Difficulty");
        JMenuItem retry= new JMenuItem("Retry");

        JMenuItem easyM= new JMenuItem("Easy");
        JMenuItem normM= new JMenuItem("Normal");
        JMenuItem hardM= new JMenuItem("Hard");

        retry.addActionListener(e -> {restartGame(buttons, back, tries);});

        easyM.addActionListener(e -> {restartGame(buttons, back, tries+1);});
        normM.addActionListener(e -> {restartGame(buttons, back, tries);});
        hardM.addActionListener(e -> {restartGame(buttons, back, tries-1);});

        menu.add(retry);

        menuBar.add(menu);
        menuBar.add(difmenu);

        difmenu.add(easyM);
        difmenu.add(normM);
        difmenu.add(hardM);
        frame.setJMenuBar(menuBar);

        //b1.setPreferredSize(new Dimension(100, 30));
        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);

        startGame(buttons,back);

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
                        currbutton.setIcon(null);
                        Memorymaster.handleBtnClick(currbutton, assignments);
                        l1.setText("Tries: " + mem.getLives());
                        if(mem.getLives() ==0)
                        {
                            endGame(buttons,false);
                        }
                        if(mem.getPairsMade()==8)
                        {
                            endGame(buttons,true);
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
