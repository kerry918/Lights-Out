<<<<<<< HEAD
import java.awt.Color;
import javax.swing.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class LightsOut extends JFrame implements ActionListener{
	public static final int W=800; 
	public static final int H=1000; 
	public JButton[][] l = new JButton[7][5];
	 public int COL = 5, ROW = 5;
	 public boolean win = false;
	 public int countMove = 0;
	 public int leastmove = Integer.MAX_VALUE;
	 public int wins = 0;
	 
	 static int [][] count = new int [5][5];
	 static String [][] restart = new String [5][5];
	 static int getSolution [][] = new int [5][5];
	 
	 
	 public static void main(String[] args){
	       LightsOut LO = new LightsOut();
	       LO.setVisible(true);
	       LO.setResizable(false);
	}

	public LightsOut () {  //game window
	super("Lights Out");    //naming window
	setSize(W,H);   //setting the size of the window
	setLayout(new GridLayout(7, 5));	// set the layout of the window 7 by 5
	String input[][]=new String[5][5];   //string l[i][j] in the puzzle
	
		for(int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				input[i][j]="0";
			}
		}
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				l[i][j] = new JButton();   //let l[i][j] become a button
				
				l[i][j].addActionListener(this);
				
				l[i][j].setText(input[i][j]);
				
				l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.black));
				
				if(input[i][j].equals("1")) {   // if the text of the button is '1'
					l[i][j].setBackground(Color.yellow);    // set the color of the button to become yellow 
					l[i][j].setForeground(Color.yellow);
				}
				else if (input[i][j].equals("0")) {   // if the text of the button is '0'
					l[i][j].setBackground(Color.white);   // set the color of the button to become yellow
					l[i][j].setForeground(Color.white);
				}
				add(l[i][j]);   // add the buttons to the window
			}
		}
		
		// control buttons
        for(int i=0; i<5; i++) {	// for loop
       	 l[5][i] = new JButton();	// let l[5][i] become a new button
       	 l[5][i].addActionListener(this);	
       	 l[5][i].setBackground(Color.white);	// set the background of the button is black
       	 l[5][i].setForeground(Color.black);	// set the foreground of the button is white
       	 if(i==4) l[5][i].setText("Randomize");	// if 'i' is 4 set text for l[5][4]
       	 else if(i==3) l[5][i].setText("Solution");	// if 'i' is 3 set the text for l[5][3]
       	 else if(i==2) l[5][i].setText("Wins: " + wins);	// if 'i' is 2 set the text for l[5][2]
       	 else if(i==1) l[5][i].setText("Moves: " + countMove);	// if 'i' is 1 set the text for l[5][1]
       	 else if(i==0) l[5][i].setText("Rules");	// if 'i' is 0 set the text for l[5][0]
       	 add(l[5][i]);	// add the buttons to the window
        }
        l[6][0]=new JButton();	// let l[6][0] become a new button
        l[6][0].addActionListener(this);
        l[6][0].setBackground(Color.white);	// set the background of the button to black
        l[6][0].setForeground(Color.black);	// set the foreground of the button to white
        l[6][0].setText("Restart");	// set the text for l[6][0]
        add(l[6][0]);	// add l[6][0] into the window
        
        l[6][1]=new JButton();
        l[6][1].addActionListener(this);
        l[6][1].setBackground(Color.white);
        l[6][1].setForeground(Color.black);
        l[6][1].setText("Least Moves: " + "0");
        add(l[6][1]);
        
   }
 	
 	public void getPuzzle(){	// let the data remember the puzzle before the user click button 'randomize'
 		for(int i=0;i<5;i++){	// for loop
 			for(int j=0; j<5;j++){
 				restart[i][j]=l[i][j].getText();	// let restart[i][j] get the text of l[i][j]
 			}
      	}
 	}
 	
 	public void getSolution() {	// get the solution of the puzzle
 		for(int i=0;i<5;i++){
 	       	 for(int j=0; j<5;j++){
 	       		 getSolution[i][j]=count[i][j];	// let getSolution[i][j] equals to the buttons that the data and the user clicked
 	       	 }
 	      }
 	}
 	
 	public void putInSolution() {
 		for(int i=0;i<5;i++){
	       	 for(int j=0; j<5;j++){
	       		count[i][j]=getSolution[i][j];
	       	 }
	    }
 	}
 	
 	public void actionPerformed(ActionEvent e){	// a method that can read the clicked button and let the button action
       JButton click = (JButton)e.getSource();	// read which button is clicked
       
       for(int i = 0; i < 5; i++) {
          for(int j = 0; j < 5; j++) {
             if (click == l[i][j]) {	// if the user clicked the buttons
           	 l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
                reverse(i, j);	// go to method 'reverse' and change the buttons around the button
                count [i][j] ++;
                countMove = countMove + 1;	// move plus one after the user clicked a button
                l[5][1].setText("Moves: "+ countMove);	
                win=check();	// check if all the lights turned off
                if (win==true) {	// if all the lights
               	 wins = wins + 1;
                    l[5][2].setText("Wins: "+ wins);
                }
             }
          }
       }
       // the buttons in the control center are clicked
       if(click == l[5][4]) {
       	Random rand = new Random();	// randomize the puzzle
       	for (int i = 0; i < 5; i ++) {
       		for (int j = 0; j < 5; j++) {
       			l[i][j].setText("0");	// set the text l[i][j] is '0'
       			l[i][j].setBackground(Color.white);	// set the background of the button to white
       			l[i][j].setForeground(Color.white);	// set the foreground of the button to white
       		}
       	}
       	
       	for (int i = 0; i < 5; i++) {
       		for (int j = 0; j < 5; j++) {
       			count [i][j] = 0;	// let count[i][j] equals to zero
       			l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));	// set the color of the border around the buttons in the puzzle to black and set the size in to 5
       		}
       	}
           for(int a=0; a<10; a++) {	// click the button for 10 times
           	int i = rand.nextInt(5);
           	int j = rand.nextInt(5);
           	count [i][j] ++;
           	reverse(i, j);	// go to method 'reverse' and change the buttons around the button
           }
           getPuzzle();
           getSolution();
           countMove = 0;	// let move equals to zero
           l[5][1].setText("Moves: "+ countMove);	// set the text on the button
       }
       
       else if(click == l[5][3]) {
       	for (int i = 0; i < 5; i++) {
       		for (int j = 0; j < 5; j++) {
       			if (count [i][j] % 2 == 1) {	// if the times that count has been clicked is odd
       				l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.red));	// set the color of the border around the buttons in the puzzle to red and set the size in to 5
       			}
       			
       		}
       	}
       }
       
       else if(click == l[5][0]) {
       	JOptionPane.showMessageDialog(this, "Lights Out is a puzzle game includes a grid of lights that are either yellow or white. Pressing any light will toggles the respective light and the surrounding non-diagonal lights. The objective is to turn off ALL lights in as few moves as possible");
       	// make a message dialog to tell the user the rules of this game
       }
       
       else if (click == l[6][0]) {
       	
       	for (int i = 0; i < 5; i ++) {
       		for (int j = 0; j < 5; j++) {
       			l[i][j].setText("0");
       			l[i][j].setBackground(Color.white);	// set the background color of the button to white
       			l[i][j].setForeground(Color.white);	// set the foreground color of the button to white
       		}
       	}
       	for (int i = 0; i < 5; i++) {
       		for (int j = 0; j < 5; j++) {
       			count [i][j] = 0;
       			l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
       		}
       	}
       	putInSolution();
       	
       	for(int i=0;i<5;i++){
    	       	 for(int j=0; j<5;j++){
    	       		 l[i][j].setText(restart[i][j]);	// let the text of l[i][j] be the text of restart[i][j] 
    	       		 if (l[i][j].getText().equals("0")) {
    	       			 l[i][j].setBackground(Color.white);
    	       			 l[i][j].setForeground(Color.white);
    	       		 }
    	       		 else if (l[i][j].getText().equals("1")) {
    	       			 l[i][j].setBackground(Color.yellow);
    	       			 l[i][j].setForeground(Color.yellow);
    	       		 }
    	       	 }
    		}
       	countMove = 0;
       	l[5][1].setText("Moves: "+ countMove);
       }
 }
 	
 	public void reverse(int i, int j) { // change buttons around the button that has been clicked
     change(i, j);
     change(i+1, j);
     change(i-1, j);
     change(i, j+1);
     change(i, j-1);
 }
 
 	public void change(int i, int j) {
     if (0 <= i && i < ROW && 0 <= j && j < COL) {	// check the button that has been clicked is in the game 
   	  if ("0".equals(l[i][j].getText())){	// if the text of the button is '0' then change the button into yellow
   		  l[i][j].setBackground(Color.yellow); 
   		  l[i][j].setForeground(Color.yellow);
   		  l[i][j].setText("1");	// change the text of the button into '1'
   	  }
         else if("1".equals(l[i][j].getText())){	// if the text of the button is '1' then change the button into white
       	  l[i][j].setBackground(Color.white);
       	  l[i][j].setForeground(Color.white);
       	  l[i][j].setText("0");	// change the text of the button into '0'
         }
     }
 	}

 	public boolean check() {
 		boolean check = false;
 		int right = 0;	// let right be '0'
 		for(int i = 0; i < 5; i++) {
 			for (int j = 0; j < 5; j++) {
 				if (l[i][j].getText().equals("0")) {	// if the text of l[i][j] is '0'
 					right++;	// right plus 1
 				}
 			}
 		}
 		if (right==25) {	// if there are 25 l[i][j] are equals to 0
 			check = true;	// check is true
 			JOptionPane.showMessageDialog(this, "GOOD JOB!!! YOU HAVE SOLVED THE GAME!!! It took you " + countMove + " moves.");	// show a message dialog with 'congratulation' and how many steps user used
 			leastmove = Math.min(leastmove, countMove);	// let least move equals to the least move
       	l[6][1].setText("Least Moves: " + leastmove);	// set the text of l[6][1]
       	add(l[6][1]);	// add l[6][1] into the window
       	countMove = 0;	// turn move to 0
 		}
		return check;
 	}
=======
import java.awt.Color;
import javax.swing.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class LightsOut extends JFrame implements ActionListener{
	public static final int W=800; 
	public static final int H=1000; 
	public JButton[][] l = new JButton[7][5];
	 public int COL = 5, ROW = 5;
	 public boolean win = false;
	 public int countMove = 0;
	 public int leastmove = Integer.MAX_VALUE;
	 public int wins = 0;
	 
	 static int [][] count = new int [5][5];
	 static String [][] restart = new String [5][5];
	 static int getSolution [][] = new int [5][5];
	 
	 
	 public static void main(String[] args){
	       LightsOut LO = new LightsOut();
	       LO.setVisible(true);
	       LO.setResizable(false);
	}

	public LightsOut () {  //game window
	super("Lights Out");    //naming window
	setSize(W,H);   //setting the size of the window
	setLayout(new GridLayout(7, 5));	// set the layout of the window 7 by 5
	String input[][]=new String[5][5];   //string l[i][j] in the puzzle
	
		for(int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				input[i][j]="0";
			}
		}
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				l[i][j] = new JButton();   //let l[i][j] become a button
				
				l[i][j].addActionListener(this);
				
				l[i][j].setText(input[i][j]);
				
				l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.black));
				
				if(input[i][j].equals("1")) {   // if the text of the button is '1'
					l[i][j].setBackground(Color.yellow);    // set the color of the button to become yellow 
					l[i][j].setForeground(Color.yellow);
				}
				else if (input[i][j].equals("0")) {   // if the text of the button is '0'
					l[i][j].setBackground(Color.white);   // set the color of the button to become yellow
					l[i][j].setForeground(Color.white);
				}
				add(l[i][j]);   // add the buttons to the window
			}
		}
		
		// control buttons
        for(int i=0; i<5; i++) {	// for loop
       	 l[5][i] = new JButton();	// let l[5][i] become a new button
       	 l[5][i].addActionListener(this);	
       	 l[5][i].setBackground(Color.white);	// set the background of the button is black
       	 l[5][i].setForeground(Color.black);	// set the foreground of the button is white
       	 if(i==4) l[5][i].setText("Randomize");	// if 'i' is 4 set text for l[5][4]
       	 else if(i==3) l[5][i].setText("Solution");	// if 'i' is 3 set the text for l[5][3]
       	 else if(i==2) l[5][i].setText("Wins: " + wins);	// if 'i' is 2 set the text for l[5][2]
       	 else if(i==1) l[5][i].setText("Moves: " + countMove);	// if 'i' is 1 set the text for l[5][1]
       	 else if(i==0) l[5][i].setText("Rules");	// if 'i' is 0 set the text for l[5][0]
       	 add(l[5][i]);	// add the buttons to the window
        }
        l[6][0]=new JButton();	// let l[6][0] become a new button
        l[6][0].addActionListener(this);
        l[6][0].setBackground(Color.white);	// set the background of the button to black
        l[6][0].setForeground(Color.black);	// set the foreground of the button to white
        l[6][0].setText("Restart");	// set the text for l[6][0]
        add(l[6][0]);	// add l[6][0] into the window
        
        l[6][1]=new JButton();
        l[6][1].addActionListener(this);
        l[6][1].setBackground(Color.white);
        l[6][1].setForeground(Color.black);
        l[6][1].setText("Least Moves: " + "0");
        add(l[6][1]);
        
   }
 	
 	public void getPuzzle(){	// let the data remember the puzzle before the user click button 'randomize'
 		for(int i=0;i<5;i++){	// for loop
 			for(int j=0; j<5;j++){
 				restart[i][j]=l[i][j].getText();	// let restart[i][j] get the text of l[i][j]
 			}
      	}
 	}
 	
 	public void getSolution() {	// get the solution of the puzzle
 		for(int i=0;i<5;i++){
 	       	 for(int j=0; j<5;j++){
 	       		 getSolution[i][j]=count[i][j];	// let getSolution[i][j] equals to the buttons that the data and the user clicked
 	       	 }
 	      }
 	}
 	
 	public void putInSolution() {
 		for(int i=0;i<5;i++){
	       	 for(int j=0; j<5;j++){
	       		count[i][j]=getSolution[i][j];
	       	 }
	    }
 	}
 	
 	public void actionPerformed(ActionEvent e){	// a method that can read the clicked button and let the button action
       JButton click = (JButton)e.getSource();	// read which button is clicked
       
       for(int i = 0; i < 5; i++) {
          for(int j = 0; j < 5; j++) {
             if (click == l[i][j]) {	// if the user clicked the buttons
           	 l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
                reverse(i, j);	// go to method 'reverse' and change the buttons around the button
                count [i][j] ++;
                countMove = countMove + 1;	// move plus one after the user clicked a button
                l[5][1].setText("Moves: "+ countMove);	
                win=check();	// check if all the lights turned off
                if (win==true) {	// if all the lights
               	 wins = wins + 1;
                    l[5][2].setText("Wins: "+ wins);
                }
             }
          }
       }
       // the buttons in the control center are clicked
       if(click == l[5][4]) {
       	Random rand = new Random();	// randomize the puzzle
       	for (int i = 0; i < 5; i ++) {
       		for (int j = 0; j < 5; j++) {
       			l[i][j].setText("0");	// set the text l[i][j] is '0'
       			l[i][j].setBackground(Color.white);	// set the background of the button to white
       			l[i][j].setForeground(Color.white);	// set the foreground of the button to white
       		}
       	}
       	
       	for (int i = 0; i < 5; i++) {
       		for (int j = 0; j < 5; j++) {
       			count [i][j] = 0;	// let count[i][j] equals to zero
       			l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));	// set the color of the border around the buttons in the puzzle to black and set the size in to 5
       		}
       	}
           for(int a=0; a<10; a++) {	// click the button for 10 times
           	int i = rand.nextInt(5);
           	int j = rand.nextInt(5);
           	count [i][j] ++;
           	reverse(i, j);	// go to method 'reverse' and change the buttons around the button
           }
           getPuzzle();
           getSolution();
           countMove = 0;	// let move equals to zero
           l[5][1].setText("Moves: "+ countMove);	// set the text on the button
       }
       
       else if(click == l[5][3]) {
       	for (int i = 0; i < 5; i++) {
       		for (int j = 0; j < 5; j++) {
       			if (count [i][j] % 2 == 1) {	// if the times that count has been clicked is odd
       				l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.red));	// set the color of the border around the buttons in the puzzle to red and set the size in to 5
       			}
       			
       		}
       	}
       }
       
       else if(click == l[5][0]) {
       	JOptionPane.showMessageDialog(this, "Lights Out is a puzzle game includes a grid of lights that are either yellow or white. Pressing any light will toggles the respective light and the surrounding non-diagonal lights. The objective is to turn off ALL lights in as few moves as possible");
       	// make a message dialog to tell the user the rules of this game
       }
       
       else if (click == l[6][0]) {
       	
       	for (int i = 0; i < 5; i ++) {
       		for (int j = 0; j < 5; j++) {
       			l[i][j].setText("0");
       			l[i][j].setBackground(Color.white);	// set the background color of the button to white
       			l[i][j].setForeground(Color.white);	// set the foreground color of the button to white
       		}
       	}
       	for (int i = 0; i < 5; i++) {
       		for (int j = 0; j < 5; j++) {
       			count [i][j] = 0;
       			l[i][j].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
       		}
       	}
       	putInSolution();
       	
       	for(int i=0;i<5;i++){
    	       	 for(int j=0; j<5;j++){
    	       		 l[i][j].setText(restart[i][j]);	// let the text of l[i][j] be the text of restart[i][j] 
    	       		 if (l[i][j].getText().equals("0")) {
    	       			 l[i][j].setBackground(Color.white);
    	       			 l[i][j].setForeground(Color.white);
    	       		 }
    	       		 else if (l[i][j].getText().equals("1")) {
    	       			 l[i][j].setBackground(Color.yellow);
    	       			 l[i][j].setForeground(Color.yellow);
    	       		 }
    	       	 }
    		}
       	countMove = 0;
       	l[5][1].setText("Moves: "+ countMove);
       }
 }
 	
 	public void reverse(int i, int j) { // change buttons around the button that has been clicked
     change(i, j);
     change(i+1, j);
     change(i-1, j);
     change(i, j+1);
     change(i, j-1);
 }
 
 	public void change(int i, int j) {
     if (0 <= i && i < ROW && 0 <= j && j < COL) {	// check the button that has been clicked is in the game 
   	  if ("0".equals(l[i][j].getText())){	// if the text of the button is '0' then change the button into yellow
   		  l[i][j].setBackground(Color.yellow); 
   		  l[i][j].setForeground(Color.yellow);
   		  l[i][j].setText("1");	// change the text of the button into '1'
   	  }
         else if("1".equals(l[i][j].getText())){	// if the text of the button is '1' then change the button into white
       	  l[i][j].setBackground(Color.white);
       	  l[i][j].setForeground(Color.white);
       	  l[i][j].setText("0");	// change the text of the button into '0'
         }
     }
 	}

 	public boolean check() {
 		boolean check = false;
 		int right = 0;	// let right be '0'
 		for(int i = 0; i < 5; i++) {
 			for (int j = 0; j < 5; j++) {
 				if (l[i][j].getText().equals("0")) {	// if the text of l[i][j] is '0'
 					right++;	// right plus 1
 				}
 			}
 		}
 		if (right==25) {	// if there are 25 l[i][j] are equals to 0
 			check = true;	// check is true
 			JOptionPane.showMessageDialog(this, "GOOD JOB!!! YOU HAVE SOLVED THE GAME!!! It took you " + countMove + " moves.");	// show a message dialog with 'congratulation' and how many steps user used
 			leastmove = Math.min(leastmove, countMove);	// let least move equals to the least move
       	l[6][1].setText("Least Moves: " + leastmove);	// set the text of l[6][1]
       	add(l[6][1]);	// add l[6][1] into the window
       	countMove = 0;	// turn move to 0
 		}
		return check;
 	}
>>>>>>> 0207f38cd6a77b5702a64ef895b58c331a8a8c33
}