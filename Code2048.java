//Name: Griffin Tattongeyer
//Date: 2024 Dec 13-
//Purpose:

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;

public class Code2048 extends Applet implements ActionListener
{
    Panel p_card;  //to hold all of the screens
    Panel card1, card2, card3, card4, card5;
    CardLayout cdLayout = new CardLayout ();
    //grid
    int row = 4;
    int col = 4;
    JButton a[] = new JButton [row * col];
    int b[] [] = {{2, 0, 0, 0},
	    {2, 0, 0, 0},
	    {0, 0, 0, 0},
	    {0, 0, 0, 0}};
    int score = 0;
    JLabel Dscore;

    public void init ()
    {
	p_card = new Panel ();
	p_card.setLayout (cdLayout);        
	screen1 ();
	screen2 ();
	screen3 ();
	screen4 ();
	screen5 ();
	resize (450, 600);
	setLayout (new BorderLayout ());
	add ("Center", p_card);
    }


    public void screen1 ()
    { //screen 1 is set up.
	card1 = new Panel ();
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
	card2 = new Panel ();
	card2.setBackground (Color.white);
	JLabel title = new JLabel ("Instructions");
	JButton next = new JButton ("Next");
	next.setActionCommand ("s3");
	next.addActionListener (this);
	card2.add (title);
	card2.add (next);
	p_card.add ("2", card2);
    }


    public void screen3 ()
    { //screen 3 is set up.
	card3 = new Panel ();
	card3.setBackground (new Color (239, 228, 176));
	JLabel title = new JLabel ("2048");
	title.setFont (new Font ("Arial", Font.BOLD, 45));
	title.setPreferredSize (new Dimension (400, 100));
	JButton reset = new JButton ("reset");
	reset.setForeground (Color.white);
	reset.setBackground (Color.black);
	reset.setActionCommand ("reset");
	reset.addActionListener (this);
	JButton instruction = new JButton ("instructions");
	instruction.setForeground (Color.white);
	instruction.setBackground (Color.black);
	instruction.setActionCommand ("s2");
	instruction.addActionListener (this);
	JButton up = new JButton ("Up");
	up.setForeground (Color.white);
	up.setBackground (Color.black);
	up.setActionCommand ("up");
	up.addActionListener (this);
	JButton down = new JButton ("Down");
	down.setForeground (Color.white);
	down.setBackground (Color.black);
	down.setActionCommand ("down");
	down.addActionListener (this);
	JButton right = new JButton ("Right");
	right.setForeground (Color.white);
	right.setBackground (Color.black);
	right.setActionCommand ("right");
	right.addActionListener (this);
	JButton left = new JButton ("Left");
	left.setForeground (Color.white);
	left.setBackground (Color.black);
	left.setActionCommand ("left");
	left.addActionListener (this);
	Dscore = new JLabel ("score; 000" );
	Dscore.setPreferredSize (new Dimension (100, 100));
	//Set up grid
	Panel p = new Panel (new GridLayout (row, col));
	int move = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		//add in when you have pictures
		a [move] = new JButton (createImageIcon (b [i] [j] + ".png"));
		//change to be your size
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
    }


    public void screen4 ()
    { //screen 4 is set up.
	card4 = new Panel ();
	card4.setBackground (Color.yellow);
	JLabel title = new JLabel ("You Win!");
	JButton next = new JButton ("Next");
	next.setActionCommand ("s5");
	next.addActionListener (this);
	card4.add (title);
	card4.add (next);
	p_card.add ("4", card4);
    }


    public void screen5 ()
    { //screen 5 is set up.
	card5 = new Panel ();
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
    { //change the red to your class name
	java.net.URL imgURL = Code2048.class.getResource (path);
	if (imgURL != null)
	{
	    return new ImageIcon (imgURL);
	}
	else
	{
	    System.err.println ("Couldn't find file: " + path);
	    return null;
	}
    }


    public void redraw ()
    {
	int move = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		a [move].setIcon (createImageIcon (b [i] [j] + ".png"));
		move++;
	    }
	}
Dscore.setText("score " + score);
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
	    for
		(int i = 0 ; i < 2 ; i++)
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
	    if (b [2] [n] == b [1] [n]&& (b [1] [n] != 0) && (b [2] [n] != 0))
	    {
		b [1] [n] += b [2] [n];
		b [2] [n] = 0;
		score += b [1] [n];
	    }
	    if (b [3] [n] == b [2] [n]&& (b [2] [n] != 0) && (b [3] [n] != 0))
	    {
		b [2] [n] += b [3] [n];
		b [3] [n] = 0;
		score += b [2] [n];
	    }
	}
    }


    public void moveUp ()
    {
	ShuffleUp ();
	UpBuddies ();
	ShuffleUp ();
	ShuffleUp ();
	picknew ();
	redraw ();
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
	    if (b [3] [n] == b [2] [n]&& (b [3] [n] != 0) && (b [2] [n] != 0))
	    {
		b [2] [n] += b [3] [n];
		b [3] [n] = 0;
		score += b [2] [n];
	    }
	    if (b [2] [n] == b [1] [n]&& (b [1] [n] != 0) && (b [2] [n] != 0))
	    {
		b [1] [n] += b [2] [n];
		b [2] [n] = 0;
		score += b [1] [n];
	    }
	    if (b [1] [n] == b [0] [n]&& (b [1] [n] != 0) && (b [0] [n] != 0))
	    {
		b [0] [n] += b [1] [n];
		b [1] [n] = 0;
		score += b [0] [n];
	    }
	}
    }


    public void moveDown ()
    {
	ShuffleDown ();
	DownBuddies ();
	ShuffleDown ();
	ShuffleDown ();
	picknew ();
	redraw ();
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
	    if (b [n] [1] == b [n] [0]&& (b [n] [1] != 0) && (b [n] [0] != 0))
	    {
		b [n] [0] += b [n] [1];
		b [n] [1] = 0;
		score += b [n] [0];
	    }
	    if (b [n] [2] == b [n] [1]&& (b [n] [1] != 0) && (b [n] [2] != 0))
	    {
		b [n] [1] += b [n] [2];
		b [n] [2] = 0;
		score += b [n] [1];
	    }
	    if (b [n] [3] == b [n] [2]&& (b [n] [2] != 0) && (b [n] [3] != 0))
	    {
		b [n] [2] += b [n] [3];
		b [n] [3] = 0;
		score += b [n] [2];
	    }
	}
    }


    public void moveLeft ()
    {
	ShuffleLeft ();
	LeftBuddies ();
	ShuffleLeft ();
	ShuffleLeft ();
	picknew ();
	redraw ();
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
	    if (b [n] [3] == b [n] [2]&& (b [n] [3] != 0) && (b [n] [2] != 0))
	    {
		b [n] [2] += b [n] [3];
		b [n] [3] = 0;
		score += b [n] [2];
	    }
	    if (b [n] [2] == b [n] [1]&& (b [n] [1] != 0) && (b [n] [2] != 0))
	    {
		b [n] [1] += b [n] [2];
		b [n] [2] = 0;
		score += b [n] [1];
	    }
	    if (b [n] [1] == b [n] [0]&& (b [n] [1] != 0) && (b [n] [0] != 0))
	    {
		b [n] [0] += b [n] [1];
		b [n] [1] = 0;
		score += b [n] [0];
	    }
	}
    }


    public void moveRight ()
    {
	ShuffleRight ();
	RightBuddies ();
	ShuffleRight ();
	ShuffleRight ();
	picknew ();
	redraw ();
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
	else if (e.getActionCommand ().equals ("reset"))
	{
	    for (int i = 0 ; i < col ; i++)
	    {
		for (int j = 0 ; j < row ; j++)
		{
		    b [i] [j] = 0;
		}
	    }
	    b [0] [0] = 2;
	    b [1] [0] = 2;
	    score = 0;
	}
	redraw ();
    }
}


