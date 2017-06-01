import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

class AlienButton extends JButton{

	private boolean containsAlien;
	private boolean activated;

	int col;
	int row;


	public AlienButton(int col, int row){

		containsAlien = false;
		activated = false;

		this.col = col;
		this.row = row;

	}

	public void containAlien(){

		containsAlien = true;

	}

	public boolean isTarget(){
		return containsAlien;
	}

	public boolean isActivatedTarget(){

		return containsAlien && activated;

	}

	public void setIcon(ImageIcon e){
		super.setIcon(e);
		activated = true;
	}

	public void deactivate(){
		activated = false;
		setIcon(null);
	}

}

/**
 * GridClicker is a basic class to show how to set up a 2D array of buttons
 * with a GridLayout and determine the location of the button being clicked.
 **/
public class GridGame extends JPanel implements ActionListener
{
	private static final int TOTAL_ALIENS = 10;
	private static int capturedAliens = 0;

	// a 2D array to hold the button instances
	private AlienButton[][] buttons;
	private ArrayList<AlienButton> targets;
	private int targetNum;

	private AlienButton currentTarget;

	ImageIcon alienIcon;

	/** 
	 * Constructor creates a new JButtonWithImage object, which is a JPanel
	 * containing a numRows x numCols grid of JButton instances.
	 **/
	public GridGame( int numRows, int numCols ) {

		try {
			alienIcon = new ImageIcon(
					new ImageIcon(
							new URL("https://opengameart.org/sites/default/files/1ST%20FRAME_3.PNG"))
							.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
		} catch (MalformedURLException e){
			e.printStackTrace();
		}

		targetNum = 0;

		int x,y;
		// use grid layout for this JPanel
		setLayout( new GridLayout( numRows, numCols ) );

		// allocate a 2D array for the buttons
		buttons = new AlienButton[numRows][numCols];

		targets = new ArrayList<>();

		// a variable to hold the current button number
		int buttonNum = 0;

		// for each row of the array
		for ( int row = 0; row < buttons.length; row++ )
		{
			// for each column in that row
			for ( int col = 0; col < buttons[row].length; col++ )
			{
				// create a button instance and store it in the array
				buttons[row][col] = new AlienButton(col,row);

				// set its background color
				buttons[row][col].setBackground( Color.GREEN );

				buttons[row][col].addActionListener(this);

				// increment the current button number
				buttonNum++;

				// add the button to this JPanel 
				add( buttons[row][col] );
			}
		}

		while (targetNum < 10){

			for ( int row = 0; row < buttons.length; row++ ) {
				// for each column in that row
				for (int col = 0; col < buttons[row].length; col++) {
					determineTarget(buttons[row][col]);
				}
			}
		}

		activateTarget(targets.get(0));

	}

	/**
	 * Special method required by the ActionListener "interface" -- when a button 
	 * is clicked, this method will be invoked. The parameter e packs information
	 * about the click event.
	 **/

	private void determineTarget(AlienButton button){

		if (!button.isTarget()) {
			if (ThreadLocalRandom.current().nextInt(1, 3) == 1 && targetNum < 10) {
				button.containAlien();
				targets.add(button);
				targetNum++;
			}
		}

	}

	private void activateTarget(AlienButton button){
		button.setIcon(alienIcon);
		currentTarget = button;
	}

	private void activateNextTarget(AlienButton button){

		try {
			activateTarget(
					targets.get(targets.indexOf(button) + 1));
		} catch (IndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(null,
					"GAME OVER!\nYour caught " + capturedAliens + " aliens.\n Your accuracy is "
					+ (((float)capturedAliens/(float)TOTAL_ALIENS) * 100) + "%.","GAME OVER!",JOptionPane.INFORMATION_MESSAGE);
			System.exit(100);
		}

	}

	public void actionPerformed( ActionEvent e )

	{
		if (e.getSource() == currentTarget) {

			System.out.println("That's the right target.");
			capturedAliens++;
			currentTarget.deactivate();
			activateNextTarget(currentTarget);

		} else {

			System.out.println("Wrong target!");
			currentTarget.deactivate();
			activateNextTarget(currentTarget);
			
		}

	}

	/**
	 * Special main function to start the program. This creates an instance
	 * of the JButtonWithImage GUI.
	 **/
	
	final static byte R=10;
	final static byte C=10;
	
	public static void main( String args[] ) {
	
		byte board[][] = new byte [R][C];
		double startTime;
		double endTime;
		startTime = System.nanoTime();
		board=fillBoard();
		endTime = System.nanoTime();
		System.out.println("It took " +(endTime-startTime));
		printboard(board);
		
		
	{
		// create a new JFrame to hold the GUI
		JFrame gridFrame = new JFrame( "Grid Clicker" );

		// create a new instance of the JButtonWithImage with 4 rows and 6 columns
		// and add it to the frame
		gridFrame.add( new GridGame( 4, 6) );

		// set the size of the frame (window)
		gridFrame.setSize( 800, 900 );

		// ask it to exit the program when the window is closed
		gridFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// ask it to appear at the center of the screen
		gridFrame.setLocationRelativeTo(null);

		// show the window!
		gridFrame.setVisible( true );
	}
}

	private static byte[][] fillBoard() {
		byte x, y;
		byte board[][]=new byte[R][C];
		for (byte r=0;r<R;r++){
			for (byte c=0;c<C;c++){
				board[r][c]=0;
			}
		}

		x=(byte)(Math.round(Math.random()*(R-1)));
		y=(byte)(Math.round(Math.random()*(C-1)));
		board[x][y]=1;
		return board;
	}
	public static void printboard(byte board[][]){
		for (byte r=0;r<R;r++){
			for (byte c=0;c<C;c++){
				System.out.println();
			}
		}
		
		
		
	}
}