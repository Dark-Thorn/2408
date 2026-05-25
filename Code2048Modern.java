//Name: Griffin Tattongeyer
//Date: 2024 Dec 13-
//Modernized: runnable Java Swing version with keyboard controls and undo
//Purpose: 2048 game

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Code2048Modern extends JFrame implements ActionListener
{
    JPanel p_card;  //to hold all of the screens
    JPanel card1, card2, card3, card4, card5;
    CardLayout cdLayout = new CardLayout ();

    //grid
    int row = 4;
    int col = 4;
    JButton a[] = new JButton [row * col];
    int b[] [] = {{2, 0, 0, 0},
            {2, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}};
    int b2[] [] = {{2, 0, 0, 0},
            {2, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}};

    int score = 0;
    int score2 = 0;
    boolean canUndo = false;

    JLabel Dscore;
    JButton undo;
    JButton win;
    boolean winB = false;

    public Code2048Modern ()
    {
        super ("2048");
        init ();
    }

    public static void main (String[] args)
    {
        SwingUtilities.invokeLater (new Runnable ()
        {
            public void run ()
            {
                Code2048Modern game = new Code2048Modern ();
                game.setVisible (true);
            }
        });
    }

    public void init ()
    {
        p_card = new JPanel ();
        p_card.setLayout (cdLayout);
        screen1 ();
        screen2 ();
        screen3 ();
        screen4 ();
        screen5 ();

        setSize (450, 600);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);
        setLayout (new BorderLayout ());
        add (p_card, BorderLayout.CENTER);
        addKeyboardControls ();
    }

    public void addKeyboardControls ()
    {
        JRootPane root = getRootPane ();
        InputMap inputMap = root.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = root.getActionMap ();

        addKey (inputMap, actionMap, "UP", "up");
        addKey (inputMap, actionMap, "W", "up");
        addKey (inputMap, actionMap, "DOWN", "down");
        addKey (inputMap, actionMap, "S", "down");
        addKey (inputMap, actionMap, "LEFT", "left");
        addKey (inputMap, actionMap, "A", "left");
        addKey (inputMap, actionMap, "RIGHT", "right");
        addKey (inputMap, actionMap, "D", "right");
        addKey (inputMap, actionMap, "U", "undo");
    }

    public void addKey (InputMap inputMap, ActionMap actionMap, String keyStroke, String command)
    {
        inputMap.put (KeyStroke.getKeyStroke (keyStroke), command);
        actionMap.put (command, new AbstractAction ()
        {
            public void actionPerformed (ActionEvent e)
            {
                Code2048Modern.this.actionPerformed (
                        new ActionEvent (Code2048Modern.this, ActionEvent.ACTION_PERFORMED, command));
            }
        });
    }

    public void screen1 ()
    { //screen 1 is set up.
        card1 = new JPanel ();
        card1.setBackground (Color.white);
        JLabel logo = new JLabel (createImageIcon ("logo.jpg"));
        logo.setPreferredSize (new Dimension (400, 450));
        JLabel title = new JLabel ("Welcome");
        JButton next = new JButton ("Next");
        next.setActionCommand ("s2");
        next.addActionListener (this);
        card1.add (logo);
        card1.add (title);
        card1.add (next);
        p_card.add ("1", card1);
    }

    public void screen2 ()
    { //screen 2 is set up.
        card2 = new JPanel ();
        card2.setBackground (Color.white);
        JLabel instructions = new JLabel (createImageIcon ("instructions.PNG"));
        JLabel title = new JLabel ("Instructions: use buttons, arrow keys, or WASD. Press U to undo.");
        JButton next = new JButton ("Next");
        next.setActionCommand ("s3");
        next.addActionListener (this);
        card2.add (instructions);
        card2.add (title);
        card2.add (next);
        p_card.add ("2", card2);
    }

    public void screen3 ()
    { //screen 3 is set up.
        card3 = new JPanel ();
        card3.setBackground (new Color (239, 228, 176));
        JLabel title = new JLabel ("2048");
        title.setFont (new Font ("Arial", Font.BOLD, 45));
        title.setPreferredSize (new Dimension (400, 70));
        title.setHorizontalAlignment (0);

        JButton reset = makeBlackButton ("reset", "reset");
        JButton instruction = makeBlackButton ("instructions", "s2");
        JButton up = makeBlackButton ("Up", "up");
        JButton down = makeBlackButton ("Down", "down");
        JButton right = makeBlackButton ("Right", "right");
        JButton left = makeBlackButton ("Left", "left");

        Dscore = new JLabel ("score 0");
        win = makeBlackButton ("win", "s4");
        undo = makeBlackButton ("undo", "undo");
        Dscore.setPreferredSize (new Dimension (100, 100));

        //Set up grid
        JPanel p = new JPanel (new GridLayout (row, col));
        int move = 0;
        for (int i = 0 ; i < row ; i++)
        {
            for (int j = 0 ; j < col ; j++)
            {
                a [move] = new JButton ();
                setTileDisplay (a [move], b [i] [j]);
                a [move].setPreferredSize (new Dimension (100, 100));
                a [move].addActionListener (this);
                a [move].setActionCommand ("" + move);
                p.add (a [move]);
                move++;
            }
        }

        card3.add (title);
        card3.add (up);
        card3.add (down);
        card3.add (left);
        card3.add (right);
        card3.add (p);
        p_card.add ("3", card3);
        card3.add (instruction);
        card3.add (reset);
        card3.add (Dscore);
        card3.add (undo);
        card3.add (win);
    }

    public JButton makeBlackButton (String label, String command)
    {
        JButton button = new JButton (label);
        button.setForeground (Color.white);
        button.setBackground (Color.black);
        button.setActionCommand (command);
        button.addActionListener (this);
        return button;
    }

    public void screen4 ()
    { //screen 4 is set up.
        card4 = new JPanel ();
        JLabel title = new JLabel ("You Win!");
        title.setFont (new Font ("Arial", Font.BOLD, 45));
        JLabel thank = new JLabel (createImageIcon ("crown.jpg"));
        JButton next = new JButton ("End game");
        next.setActionCommand ("s6");
        next.addActionListener (this);
        card4.add (thank);
        card4.add (title);
        card4.add (next);
        p_card.add ("4", card4);
    }

    public void screen5 ()
    { //screen 5 is set up.
        card5 = new JPanel ();
        card5.setBackground (Color.cyan);
        JLabel title = new JLabel ("You Lose.");
        JButton next = new JButton ("Back to Introduction?");
        next.setActionCommand ("s1");
        next.addActionListener (this);
        JButton end = new JButton ("Quit?");
        end.setActionCommand ("s6");
        end.addActionListener (this);
        card5.add (title);
        card5.add (next);
        card5.add (end);
        p_card.add ("5", card5);
    }

    protected static ImageIcon createImageIcon (String path)
    {
        java.net.URL imgURL = Code2048Modern.class.getResource (path);
        if (imgURL != null)
        {
            return new ImageIcon (imgURL);
        }

        File imageFile = new File (path);
        if (imageFile.exists ())
        {
            return new ImageIcon (path);
        }

        return null;
    }

    public void setTileDisplay (JButton button, int value)
    {
        ImageIcon icon = createImageIcon (value + ".png");
        if (icon != null)
        {
            button.setIcon (icon);
            button.setText ("");
        }
        else
        {
            button.setIcon (null);
            button.setText (value == 0 ? "" : "" + value);
            button.setFont (new Font ("Arial", Font.BOLD, 28));
        }
    }

    public void redraw ()
    {
        int move = 0;
        for (int i = 0 ; i < row ; i++)
        {
            for (int j = 0 ; j < col ; j++)
            {
                setTileDisplay (a [move], b [i] [j]);
                move++;
            }
        }
        Dscore.setText ("score " + score);
    }

    public void picknew ()
    {
        int empty = 0;
        for (int i = 0 ; i < row ; i++)
        {
            for (int j = 0 ; j < col ; j++)
            {
                if (b [i] [j] == 0)
                    empty++;
            }
        }
        if (empty > 0)
        {
            int x = (int) (Math.random () * 4);
            int y = (int) (Math.random () * 4);
            while (b [x] [y] != 0)
            {
                x = (int) (Math.random () * 4);
                y = (int) (Math.random () * 4);
            }
            b [x] [y] = 2;
        }
        else
            cdLayout.show (p_card, "5");
    }

    public void wincondition ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            for (int i = 0 ; i < 3 ; i++)
            {
                if (b [n] [i] == 2048 && winB == false)
                {
                    win.setForeground (Color.white);
                    win.setBackground (Color.black);
                    winB = true;
                }
            }
        }
    }

    public void store ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            for (int i = 0 ; i < 4 ; i++)
            {
                b2 [n] [i] = b [n] [i];
            }
        }
        score2 = score;
        canUndo = true;
    }

    public void undoMove ()
    {
        if (canUndo)
        {
            for (int n = 0 ; n < 4 ; n++)
            {
                for (int i = 0 ; i < 4 ; i++)
                {
                    b [n] [i] = b2 [n] [i];
                }
            }
            score = score2;
            canUndo = false;
            redraw ();
            cdLayout.show (p_card, "3");
        }
    }

    public void ShuffleUp ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            for (int i = 0 ; i < 3 ; i++)
            {
                if (b [0] [n] == 0)
                {
                    b [0] [n] = b [1] [n];
                    b [1] [n] = b [2] [n];
                    b [2] [n] = b [3] [n];
                    b [3] [n] = 0;
                }
            }
            for (int i = 0 ; i < 2 ; i++)
            {
                if (b [1] [n] == 0)
                {
                    b [1] [n] = b [2] [n];
                    b [2] [n] = b [3] [n];
                    b [3] [n] = 0;
                }
            }
            if (b [2] [n] == 0)
            {
                b [2] [n] = b [3] [n];
                b [3] [n] = 0;
            }
        }
    }

    //method to add tiles when going up
    public void UpBuddies ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            if ((b [1] [n] == b [0] [n]) && (b [1] [n] != 0) && (b [0] [n] != 0))
            {
                b [0] [n] += b [1] [n];
                b [1] [n] = 0;
                score += b [0] [n];
            }
            if (b [2] [n] == b [1] [n] && (b [1] [n] != 0) && (b [2] [n] != 0))
            {
                b [1] [n] += b [2] [n];
                b [2] [n] = 0;
                score += b [1] [n];
            }
            if (b [3] [n] == b [2] [n] && (b [2] [n] != 0) && (b [3] [n] != 0))
            {
                b [2] [n] += b [3] [n];
                b [3] [n] = 0;
                score += b [2] [n];
            }
        }
    }

    public void moveUp ()
    {
        store ();
        ShuffleUp ();
        UpBuddies ();
        ShuffleUp ();
        ShuffleUp ();
        picknew ();
        redraw ();
        wincondition ();
    }

    public void ShuffleDown ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            for (int i = 0 ; i < 3 ; i++)
            {
                if (b [3] [n] == 0)
                {
                    b [3] [n] = b [2] [n];
                    b [2] [n] = b [1] [n];
                    b [1] [n] = b [0] [n];
                    b [0] [n] = 0;
                }
            }
            for (int i = 0 ; i < 2 ; i++)
            {
                if (b [2] [n] == 0)
                {
                    b [2] [n] = b [1] [n];
                    b [1] [n] = b [0] [n];
                    b [0] [n] = 0;
                }
            }
            if (b [1] [n] == 0)
            {
                b [1] [n] = b [0] [n];
                b [0] [n] = 0;
            }
        }
    }

    //method to add tiles when going up
    public void DownBuddies ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            if (b [3] [n] == b [2] [n] && (b [3] [n] != 0) && (b [2] [n] != 0))
            {
                b [2] [n] += b [3] [n];
                b [3] [n] = 0;
                score += b [2] [n];
            }
            if (b [2] [n] == b [1] [n] && (b [1] [n] != 0) && (b [2] [n] != 0))
            {
                b [1] [n] += b [2] [n];
                b [2] [n] = 0;
                score += b [1] [n];
            }
            if (b [1] [n] == b [0] [n] && (b [1] [n] != 0) && (b [0] [n] != 0))
            {
                b [0] [n] += b [1] [n];
                b [1] [n] = 0;
                score += b [0] [n];
            }
        }
    }

    public void moveDown ()
    {
        store ();
        ShuffleDown ();
        DownBuddies ();
        ShuffleDown ();
        ShuffleDown ();
        picknew ();
        redraw ();
        wincondition ();
    }

    public void ShuffleLeft ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            for (int i = 0 ; i < 3 ; i++)
            {
                if (b [n] [0] == 0)
                {
                    b [n] [0] = b [n] [1];
                    b [n] [1] = b [n] [2];
                    b [n] [2] = b [n] [3];
                    b [n] [3] = 0;
                }
            }
            for (int i = 0 ; i < 2 ; i++)
            {
                if (b [n] [1] == 0)
                {
                    b [n] [1] = b [n] [2];
                    b [n] [2] = b [n] [3];
                    b [n] [3] = 0;
                }
            }
            if (b [n] [2] == 0)
            {
                b [n] [2] = b [n] [3];
                b [n] [3] = 0;
            }
        }
    }

    //method to add tiles when going up
    public void LeftBuddies ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            if (b [n] [1] == b [n] [0] && (b [n] [1] != 0) && (b [n] [0] != 0))
            {
                b [n] [0] += b [n] [1];
                b [n] [1] = 0;
                score += b [n] [0];
            }
            if (b [n] [2] == b [n] [1] && (b [n] [1] != 0) && (b [n] [2] != 0))
            {
                b [n] [1] += b [n] [2];
                b [n] [2] = 0;
                score += b [n] [1];
            }
            if (b [n] [3] == b [n] [2] && (b [n] [2] != 0) && (b [n] [3] != 0))
            {
                b [n] [2] += b [n] [3];
                b [n] [3] = 0;
                score += b [n] [2];
            }
        }
    }

    public void moveLeft ()
    {
        store ();
        ShuffleLeft ();
        LeftBuddies ();
        ShuffleLeft ();
        ShuffleLeft ();
        picknew ();
        redraw ();
        wincondition ();
    }

    public void ShuffleRight ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            for (int i = 0 ; i < 3 ; i++)
            {
                if (b [n] [3] == 0)
                {
                    b [n] [3] = b [n] [2];
                    b [n] [2] = b [n] [1];
                    b [n] [1] = b [n] [0];
                    b [n] [0] = 0;
                }
            }
            for (int i = 0 ; i < 2 ; i++)
            {
                if (b [n] [2] == 0)
                {
                    b [n] [2] = b [n] [1];
                    b [n] [1] = b [n] [0];
                    b [n] [0] = 0;
                }
            }
            if (b [n] [1] == 0)
            {
                b [n] [1] = b [n] [0];
                b [n] [0] = 0;
            }
        }
    }

    //method to add tiles when going up
    public void RightBuddies ()
    {
        for (int n = 0 ; n < 4 ; n++)
        {
            if (b [n] [3] == b [n] [2] && (b [n] [3] != 0) && (b [n] [2] != 0))
            {
                b [n] [2] += b [n] [3];
                b [n] [3] = 0;
                score += b [n] [2];
            }
            if (b [n] [2] == b [n] [1] && (b [n] [1] != 0) && (b [n] [2] != 0))
            {
                b [n] [1] += b [n] [2];
                b [n] [2] = 0;
                score += b [n] [1];
            }
            if (b [n] [1] == b [n] [0] && (b [n] [1] != 0) && (b [n] [0] != 0))
            {
                b [n] [0] += b [n] [1];
                b [n] [1] = 0;
                score += b [n] [0];
            }
        }
    }

    public void moveRight ()
    {
        store ();
        ShuffleRight ();
        RightBuddies ();
        ShuffleRight ();
        ShuffleRight ();
        picknew ();
        redraw ();
        wincondition ();
    }

    public void actionPerformed (ActionEvent e)
    { //moves between the screens
        if (e.getActionCommand ().equals ("s1"))
            cdLayout.show (p_card, "1");
        else if (e.getActionCommand ().equals ("s2"))
            cdLayout.show (p_card, "2");
        else if (e.getActionCommand ().equals ("s3"))
            cdLayout.show (p_card, "3");
        else if (e.getActionCommand ().equals ("s4"))
            cdLayout.show (p_card, "4");
        else if (e.getActionCommand ().equals ("s5"))
            cdLayout.show (p_card, "5");
        else if (e.getActionCommand ().equals ("s6"))
            System.exit (0);
        else if (e.getActionCommand ().equals ("up"))
            moveUp ();
        else if (e.getActionCommand ().equals ("down"))
            moveDown ();
        else if (e.getActionCommand ().equals ("left"))
            moveLeft ();
        else if (e.getActionCommand ().equals ("right"))
            moveRight ();
        else if (e.getActionCommand ().equals ("undo"))
            undoMove ();
        else if (e.getActionCommand ().equals ("reset"))
        {
            for (int i = 0 ; i < col ; i++)
            {
                for (int j = 0 ; j < row ; j++)
                {
                    b [i] [j] = 0;
                    b2 [i] [j] = 0;
                }
            }
            b [0] [0] = 2;
            b [1] [0] = 2;
            score = 0;
            score2 = 0;
            canUndo = false;
            winB = false;
            redraw ();
        }
    }
}
