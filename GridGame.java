import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * GridClicker is a basic class to show how to set up a 2D array of buttons
 * with a GridLayout and determine the location of the button being clicked.
 **/
public class GridGame extends JPanel implements ActionListener
{
	// a 2D array to hold the button instances
	private JButton[][] buttons;

	/** 
	 * Constructor creates a new JButtonWithImage object, which is a JPanel
	 * containing a numRows x numCols grid of JButton instances.
	 **/
	public GridGame( int numRows, int numCols ) {
		Icon alienIcon = new ImageIcon("Download:\\Alien.png");
		int x,y;
		// use grid layout for this JPanel
		setLayout( new GridLayout( numRows, numCols ) );

		// allocate a 2D array for the buttons
		buttons = new JButton[numRows][numCols];

		// a variable to hold the current button number
		int buttonNum = 0;

		// for each row of the array
		for ( int row = 0; row < buttons.length; row++ )
		{
			// for each column in that row
			for ( int col = 0; col < buttons[row].length; col++ )
			{
				// create a button instance and store it in the array
				buttons[row][col] = new JButton( Integer.toString( buttonNum ) );

				// set its background color
				buttons[row][col].setBackground( Color.GREEN );

				// increment the current button number
				buttonNum++;

				// add the button to this JPanel 
				add( buttons[row][col] );

				// ask the button to register this object for notification of clicks on the button
				buttons[row][col].addActionListener( this );
			}
		}
		x=(int)Math.round(Math.random()*3);
		y=(int)Math.round(Math.random()*5);
		buttons[x][y].setIcon(alienIcon);
	}

	/**
	 * Special method required by the ActionListener "interface" -- when a button 
	 * is clicked, this method will be invoked. The parameter e packs information
	 * about the click event.
	 **/
	public void actionPerformed( ActionEvent e )

	{
		// for each row of the array
		
		for ( int row = 0; row < buttons.length; row++ )
		{
			// for each col in that row
			for ( int col = 0; col < buttons[row].length; col++ )
			{
				// IF the source of this event (i.e., the button that was clicked)
				//    is the same as the current button in the array
				if ( e.getSource() == buttons[row][col] )
				{
					// print out the row and column
					System.out.println( "you clicked the button at " +
							row + ", " + col );

					
					
				}
			}
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