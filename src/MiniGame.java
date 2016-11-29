import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;





/**
 * The work horse of the program ties all classes together 
 *  initialises all required objects 
 * handles all mouse/action events
 * gives the load class a medium through which it can make contact with the TileGrid class
 * swaps tiles
 * and so on
 * 
 * 
	 * In Short it Initialises and governs the Game.
	 * 
	 */



@SuppressWarnings("null")
public class MiniGame extends Frame implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private static GameBoard gb; // Where the Tiles will end up
	private static TileGrid tgridL; // Where the tiles start
	private static TileGrid tgridR;
	private static TileGrid solution; // For solution
	private static TileGrid master; // For Reset and to ensure no duplicates

	private Load load; // Buttons
	private Quit quit;
	private Save save;
	private JButton file;
	private JButton reset;
	private JButton check;
	private JButton solve;
	private JButton selector;
	private JButton menuButton; // Main menu
	private JButton newBgrnd;
	private JPanel bgrid; // Where the buttons Are
	private static JDialog menu;

	private static JFrame window; // Main Window
	private Tile tilesToSwap[]; // For Swap
	private static Color border; // Color for Borders
	private static Timer timer; // Time How long it takes to solve the puzzle
	private JPanel contentPane; // Display Area

	private Dimension buttonsize; // Button Dimensions
	private Dimension buttongridDim; // Button Grid Size
	private Dimension tilegridDim; // Tile Grid Size
	private Dimension gameboardDim; // Game Board Size

	private int windowL; // window length
	private int windowH; // window height

	private static boolean stateChange = false;
	private static boolean isDefault = true;
	private static boolean isWon = false;
	// ****************************************************


	@SuppressWarnings({ "unqualified-field-access", "nls" })
	
	
	public MiniGame() {
		

		border = new Color(150, 150, 150);
		tilesToSwap = new Tile[2];
		timer = new Timer();

		// Dimension Set Up
		buttonsize = new Dimension(125, 50);
		buttongridDim = new Dimension(0, 55);
		tilegridDim = new Dimension(175, 0);
		gameboardDim = new Dimension(340, 340);
		windowL = 1000;
		windowH = 1000;
		// windowH = 650;

		// -----------------------------------------------------

		// File Button Set Up

		file = new JButton();
		file.setPreferredSize(buttonsize);
		file.setText("File");
		file.addActionListener(this);
		file.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		file.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Save Button Set-Up TEMP

		save = new Save();
		save.setPreferredSize(buttonsize);
		save.setText("Save");
		save.addActionListener(save);
		save.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		save.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Load Button SetUp

		load = new Load();
		load.setPreferredSize(buttonsize);
		load.setText("Load");
		load.addActionListener(load);
		load.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		load.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Quit Button SetUp

		quit = new Quit();
		quit.setPreferredSize(buttonsize);
		quit.setText("Quit");
		quit.addActionListener(quit);
		quit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		quit.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Reset Button SetUp

		reset = new JButton();
		reset.setPreferredSize(buttonsize);
		reset.setText("Reset");
		reset.addActionListener(this);
		reset.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		reset.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Solve Button SetUp

		solve = new JButton();
		solve.setPreferredSize(buttonsize);
		solve.setText("Solve");
		solve.addActionListener(this);
		solve.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		solve.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Check Button SetUp

		check = new JButton();
		check.setPreferredSize(buttonsize);
		check.setText("Check");
		check.addActionListener(this);
		check.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		check.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// ImgSelect Button SetUp

		selector = new JButton();
		selector.setPreferredSize(buttonsize);
		selector.setText("New Image - UC");
		selector.addActionListener(this);
		selector.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		selector.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Menu Button SetUp

		menuButton = new JButton();
		menuButton.setPreferredSize(buttonsize);
		menuButton.setText("Menu");
		menuButton.addActionListener(this);
		menuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		menuButton.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// BackGround Changer Button SetUp

		newBgrnd = new JButton();
		newBgrnd.setPreferredSize(buttonsize);
		newBgrnd.setText("New Background");
		newBgrnd.addActionListener(this);
		newBgrnd.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		newBgrnd.setForeground(Color.BLACK);
		// -----------------------------------------------------

		// Button Grid SetUp

		bgrid = new JPanel();
		bgrid.setLayout(new GridBagLayout());
		bgrid.setPreferredSize(buttongridDim);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 15, 5, 0);
		bgrid.add(file, c);
		c.gridx = 1;
		bgrid.add(quit, c);
		c.gridx = 2;
		bgrid.add(reset, c);
		bgrid.setOpaque(false);
		// -----------------------------------------------------

		// Create Display Area
		contentPane = new JPanel(new GridBagLayout());

		// Main Window Set Up
		window = new JFrame();
		window.setSize(windowL, windowH);
		window.add(contentPane);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0001;
		c.weighty = 0.0001;
		c.ipady = 0;
		c.ipadx = 0;
		c.insets = new Insets(50, 0, 0, 0);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		contentPane.add(bgrid, c);

		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.setVisible(true);
		insertGameTiles();
		windowSetUp(contentPane);
		window.setVisible(true);
		//printBol();

	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	/**
	 * swaps t1 with t2 
	 * swaps icon identifiers as well as icons
	 * 
	 * 
	 * @param Tile t1
	 * @param  Tile t2
	 */

	@SuppressWarnings("static-method")
	private void swapTiles(Tile t1, Tile t2) {
		Color warning = new Color(215, 48, 48);

		if (t1 != t2) // If tile to be swapped is same cancel swap
		{
			
			
			/**
			 * Warn user of illegal move if attempting to place tile on top of
			 * tile.
			 * 
			 * 
			 **/
			 
			if (t2.getIcon() != null) {
				t1.setBorder(BorderFactory.createLineBorder(warning, 8));
				return;
			}
			//-----------------------------------------------------
			
			

			// Swap Tile Identifiers
			if (t1.getIdentifier() != 0) {
				int x;
				x = t1.getIdentifier();
				t1.setIdentifier(t2.getIdentifier());
				t2.setIdentifier(x);
				x = t1.getRotationIdentifier();
				t1.setRotationIdentifier(t2.getRotationIdentifier());
				t2.setRotationIdentifier(x);
			}
			// -----------------------------------------------------
			
			

			ImageIcon y = new ImageIcon(); // Swap tile Icons
			y = (ImageIcon) t1.getIcon();
			t1.setIcon(t2.getIcon());
			t2.setIcon(y);
			t1.setBorder(BorderFactory.createLineBorder(border, 2));
			//-----------------------------------------------------
			
			

			// Remove tile border if SwapTo location is on Game Board
			if (t2.isOnBoard()) {
				t2.setBorder(BorderFactory.createEmptyBorder());
			}
			//-----------------------------------------------------
			
			

			// Add Tile Border if SwapTo Location is on Tile Grid
			if (!t2.isOnBoard()) {
				t2.setBorder(BorderFactory.createLineBorder(border, 2));
			}
			// -----------------------------------------------------
			stateChange = true;

		}

		else {
				if (t1 == t2 && !t1.isOnBoard()) 
				{
					t1.setBorder(BorderFactory.createLineBorder(border, 2));
				} 
				else if (t1 == t2 && t1.isOnBoard()) 
				{
					t1.setBorder(BorderFactory.createEmptyBorder());
				}

			}

	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	

	@SuppressWarnings({ "unqualified-field-access", "nls" })
	public void actionPerformed(ActionEvent e) 
	{

		//Reset actions
		if (e.getActionCommand() == "Reset") {
			{
				timer.restartTimer();
				if(isDefault){reset();}
				else
				{
					Load.lastLoad();
				}
			}
		}
		// -------------------------------------------------------

		//Open File Menu
		else if (e.getActionCommand() == "File") 
		{

			menu = new JDialog();
			menu.setSize(325, 375);
			JPanel menuPane = new JPanel(new GridBagLayout());

			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(0, 0, 0, 0);
			c.gridx = 2;
			c.gridy = 1;
			menuPane.add(load, c);

			c.insets = new Insets(15, 0, 0, 0);
			c.gridx = 2;
			c.gridy = 2;
			menuPane.add(save, c);
			
			c.insets = new Insets(15,0,0,0);
			c.gridx = 2;
			c.gridy = 3;
			menuPane.add(newBgrnd,c);

			menu.getRootPane().setOpaque(false);
			menu.setLocationRelativeTo(window);
			menu.add(menuPane);
			menu.setVisible(true);
		}
		// --------------------------------------------------------------------------------------
		
		
		//Set Up Background
		else if(e.getActionCommand() == "New Background")
		{
			contentPane = new JPanel(new GridBagLayout()) //Set Display Area Background Image
					{
						public void paintComponent(Graphics g)
						{
							Image img = null;
							File bckgrnd = null;
							final JFileChooser fc = new JFileChooser();
							FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG", "jpg",
									"png","jpeg","All");
							fc.setFileFilter(filter);
							fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
							 int returnVal = fc.showOpenDialog(null);
							    if(returnVal == JFileChooser.APPROVE_OPTION) 
							    {
							       bckgrnd = fc.getSelectedFile();
							    }
							    
							    
							 if(returnVal == JFileChooser.CANCEL_OPTION){return;}
							 
							 
							 
							 try {
								img = ImageIO.read(bckgrnd);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 
							g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),this);
									
						}
					};
					
					
					window.add(contentPane);
					gb.repaint();
					gb.setVisible(true);
					window.setVisible(true);
					
		}
		//Set Up Tile swapping
		else if (e.getSource() instanceof Tile) // You are working with Tiles so
												// start swapping
		{
			if (tilesToSwap[0] == null) 
			{
				Border select = new LineBorder(Color.BLUE, 3); // Highlight Tile
																// to be swapped
				tilesToSwap[0] = (Tile) e.getSource();
				tilesToSwap[0].setBorder(select);
			}
			//-----------------------------------------------------
			

			else 
			{
				tilesToSwap[1] = (Tile) e.getSource();
				swapTiles(tilesToSwap[0], tilesToSwap[1]);
				tilesToSwap[0] = null;
				tilesToSwap[1] = null;

			}
			//-----------------------------------------------------
			

			// Automatic Win Check

			boolean isNull = true; // Be sure that the game board tiles actually
									// have icons before doing win check

			for (int i = 0; i < gb.getTiles().length; i++) {

				// If game board tile Icon is null break the loop and DO NOT
				// check for win
				if (gb.getTiles()[i].getIcon() == null || 
						gb.getTiles()[i].getRotationIdentifier() != 0) {
					isNull = true;
					break;
				} else {
					// If game board tile Icon is NOT null set to false but
					// continue loop to check all Tiles
					isNull = false;
				}
			}

			if (isNull == false) {
				// if all tiles have icons check if game has been won
				gameWon();
			}
		}

	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	//Handle Tile Rotation
	@Override
	public void mouseClicked(MouseEvent e) {

		// Rotate Image on mouse Right Click
			//-----------------------------------------------------
			if(((Tile) e.getSource()).getIcon() == null){ return; }
		stateChange = true;
			if (SwingUtilities.isRightMouseButton(e)) 
			{
				
				if (((Tile) e.getSource()).getIcon() != null) 
				{
					int width = ((Tile) e.getSource()).getIcon().getIconWidth();
					int height = ((Tile) e.getSource()).getIcon().getIconHeight();
				}
				
				
				((Tile) e.getSource()).increaseRotation();
				int rotation = ((Tile) e.getSource()).getRotationIdentifier();
				int tile = ((Tile) e.getSource()).getIdentifier();
				((Tile) e.getSource()).setIcon(master.getRotatedImages()[rotation][tile - 1]);
				
				
				
				// Automatic Win Check

				boolean isNull = true; // Be sure that the game board tiles actually
										// have icons before doing win check

				for (int i = 0; i < gb.getTiles().length; i++) {

					// If game board tile Icon is null break the loop and DO NOT
					// check for win
					if (gb.getTiles()[i].getIcon() == null || 
							gb.getTiles()[i].getRotationIdentifier() != 0) {
						isNull = true;
						break;
					} else {
						// If game board tile Icon is NOT null set to false but
						// continue loop to check all Tiles
						isNull = false;
					}
				}

				if (isNull == false) {
					// if all tiles have icons check if game has been won
					gameWon();
				}
			}
			//-----------------------------------------------------
			

	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	
	// check if game has been won
	@SuppressWarnings({ "static-method", "nls" })
	private void gameWon() {
		//gb.print();
		if (gb.gameWon()) {
			stateChange = false;
			isWon = true;
			JOptionPane.showMessageDialog(null, "You Win!" + "\n" + "Your Time Was: " + "\n" + timer.getTime());
		}

	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	
	//Instantiate and set-Up objects
	@SuppressWarnings("unqualified-field-access")
	private void insertGameTiles() {
		tgridL = new TileGrid();
		tgridL.instantiate();
		tgridL.setPreferredSize(tilegridDim);
		tgridL.setOpaque(false);
		// ------------------------------------------------------

		tgridR = new TileGrid();
		tgridR.instantiate();
		tgridR.setPreferredSize(tilegridDim);
		tgridR.setOpaque(false);
		// ------------------------------------------------------

		solution = new TileGrid();
		solution.instantiate();
		// ------------------------------------------------------

		master = new TileGrid();
		master.fill();
		//master.fillImg();
		// ------------------------------------------------------

		gb = new GameBoard();
		gb.setPreferredSize(gameboardDim);
		gb.setLayout(new GridBagLayout());
		gb.setBorder(BorderFactory.createLineBorder(border, 2));
		gb.setOpaque(false);
		// ------------------------------------------------------

		// Tgrid0_7 setUp
		if (master.played()) {
			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridL.getLeft()[i] = master.getSaved()[i];
				tgridL.getLeft()[i].addActionListener(this);
				tgridL.getLeft()[i].addMouseListener(this);
				tgridL.getLeft()[i].setLoc(true);
				tgridL.add(tgridL.getLeft()[i]);
			}
			// ------------------------------------------------------
			// Tgrid8_16 set Up

			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridR.getRight()[i] = master.getSaved()[i + 8];
				tgridR.getRight()[i].addActionListener(this);
				tgridR.getRight()[i].addMouseListener(this);
				tgridR.getRight()[i].setLoc(true);
				tgridR.add(tgridR.getRight()[i]);
			}
			// ------------------------------------------------------
		} else {
			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridL.getLeft()[i] = master.getLeft()[i];
				tgridL.getLeft()[i].addActionListener(this);
				tgridL.getLeft()[i].addMouseListener(this);
				tgridL.getLeft()[i].setLoc(false);
				tgridL.add(tgridL.getLeft()[i]);
			}

			// ------------------------------------------------------

			// Tgrid8_16 set Up

			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridR.getRight()[i] = master.getRight()[i];
				tgridR.getRight()[i].addActionListener(this);
				tgridR.getRight()[i].addMouseListener(this);
				tgridR.getRight()[i].setLoc(false);
				tgridR.add(tgridR.getRight()[i]);
			}
		}
		// ------------------------------------------------------

		// Solution Set-Up

		for (int i = 0; i < 16; i++) // add tiles to grid
		{
			solution.getSol()[i] = master.getSol()[i];
		}
		// ------------------------------------------------------

		// Game Board SetUp

		GameBoardSetUp();

		// ------------------------------------------------------

		// Set Border For Tiles

		for (int i = 0; i < tgridL.getLeft().length; i++) {
			tgridL.getLeft()[i].setBorder(BorderFactory.createLineBorder(border, 2));
			tgridR.getRight()[i].setBorder(BorderFactory.createLineBorder(border, 2));
		}

	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	//Set Layout for Window and add things to it
	@SuppressWarnings("unqualified-field-access")
	private void windowSetUp(JComponent component) {
		//printRot();
		GridBagConstraints c = new GridBagConstraints();
		// ------------------------------------------------------

		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(0, 295, 10, 0);
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		component.add(gb, c);
		// ------------------------------------------------------

		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0001;
		c.weighty = 0.0001;
		c.ipady = 0;
		c.ipadx = 0;
		c.insets = new Insets(50, 0, 0, 0);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		contentPane.add(bgrid, c);
		// ------------------------------------------------------

		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;

		// c.insets = new Insets(135,10,0,0); //For 1000 x 650 window size

		c.insets = new Insets(10, 10, 0, 0);
		c.weightx = 0.0001;
		c.weighty = 0.0001;
		component.add(tgridL, c);
		// ------------------------------------------------------

		c.anchor = GridBagConstraints.LINE_END;
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 3;

		// c.insets = new Insets(135,10,0,0); //For 1000 x 650 window size

		c.insets = new Insets(10, 0, 0, 10);
		c.weightx = 0.0001;
		c.weighty = 0.0001;
		component.add(tgridR, c);
		// ------------------------------------------------------

	}

	
	//Set-Up game board layout
	private void GameBoardSetUp() {
		gb.setOpaque(false);
		int x = 5;
		int y = 5;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(x, y, 0, 0);
		c.ipady = 0;
		c.ipadx = 0;
		c.weightx = 0.0001;
		c.weighty = 0.0001;
		c.gridx = 0;
		c.gridy = 0;
		// ------------------------------------------------------

		if (master.played()) 
		{
			for (int i = 0; i < gb.getTiles().length; i++) 
			{
				gb.getTiles()[i] = master.getSaved()[i + 16];
				gb.getTiles()[i].addActionListener(this);
				gb.getTiles()[i].addMouseListener(this);
				gb.getTiles()[i].setLoc(true);
				gb.add(gb.getTiles()[i]);
				if (gb.getTiles()[i].getIdentifier() == 0)
					gb.getTiles()[i].setBorder(BorderFactory.createEmptyBorder());
			}
			
		}
		//-----------------------------------------------------
		
		
		else {
			for (int i = 0; i < gb.getTiles().length; i++) // add tiles to board
			{
				gb.getTiles()[i].addActionListener(this);
				gb.getTiles()[i].addMouseListener(this);
				gb.getTiles()[i].setBorder(BorderFactory.createEmptyBorder());
				gb.getTiles()[i].setLoc(true);
			}
		}
		// ------------------------------------------------------


		int i = 0;
		while (i >= 0 && i <= 3) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 85;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 4 && i <= 7) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 165;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 8 && i <= 11) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 245;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 12 && i <= 15) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}
		// ------------------------------------------------------

	}

	
	//Definition for reset
	public static void reset() {
		stateChange = false;
		isWon = false;
		master.reset();
		timer = null;
		timer = new Timer();
		// -----------------------------------------------------

		{
			for (int i = 0; i < tgridL.getLeft().length; i++) {
				tgridL.getLeft()[i].setIcon(master.getLeft()[i].getIcon());
				tgridR.getRight()[i].setIcon(master.getRight()[i].getIcon());
				tgridL.getLeft()[i].setLoc(false);
				tgridR.getRight()[i].setLoc(false);

				// Reset Borders on tiles in case any disappeared
				tgridL.getLeft()[i].setBorder(BorderFactory.createLineBorder(border, 2));
				tgridR.getRight()[i].setBorder(BorderFactory.createLineBorder(border, 2));

			}

		}
		// --------------------------------------------------------------------------------------

		for (int i = 0; i < gb.getTiles().length; i++) // set board icons to
														// null for reset
		{
			gb.getTiles()[i].setIcon(null);
			gb.getTiles()[i].setIdentifier(0);
			gb.getTiles()[i].setBorder(BorderFactory.createEmptyBorder());
			gb.getTiles()[i].setLoc(true);
		}
		// -----------------------------------------------------

		return;
	}
	//***********************************************************

	
	//Check the various boolean flags and set if need be
	public static boolean checkType() 
	{
		
		boolean boardIsDefault = true;
		boolean gridIsDefault = true;
		// -----------------------------------------------------
		
		for (int i = 0; i < tgridL.getLeft().length; i++) {
			if (tgridL.getLeft()[i].getIcon() == null || tgridR.getRight()[i].getIcon() == null) {
				gridIsDefault = false;
			}
		}
		// -----------------------------------------------------
		
		
		for (int i = 0; i < gb.getTiles().length; i++) {
			if (gb.getTiles()[i].getIcon() != null) {
				boardIsDefault = false;
			}
		}
		// -----------------------------------------------------
		
		
		if (boardIsDefault == false && gridIsDefault == false) {
			isDefault = false;
			stateChange = true;
		} else {
			isDefault = true;
			stateChange = false;
		}
		// -----------------------------------------------------
		
		
		return isDefault && stateChange;
	}
	//***********************************************************

	
    //If file to be loaded is a default file do a proper substitution
	public static void newDefault() 
	{
		
		master.fill();
		// -----------------------------------------------------
		
		
		for (int i = 0; i < tgridL.getLeft().length; i++) {
			tgridL.getLeft()[i].setIcon(master.getLeft()[i].getIcon());
			tgridL.getLeft()[i].setIdentifier(master.getLeft()[i].getIdentifier());
			tgridL.getLeft()[i].setLoc(false);

			tgridR.getRight()[i].setIcon(master.getRight()[i].getIcon());
			tgridR.getRight()[i].setIdentifier(master.getRight()[i].getIdentifier());
			tgridR.getRight()[i].setLoc(false);
		}
		// -----------------------------------------------------
		
		
		for (int i = 0; i < gb.getTiles().length; i++) {
			gb.getTiles()[i].setIcon(null);
			gb.getTiles()[i].setIdentifier(0);
			gb.getTiles()[i].setBorder(BorderFactory.createEmptyBorder());
			gb.getTiles()[i].setLoc(true);
			gb.setOpaque(false);
		}
		// -----------------------------------------------------
		
		
		stateChange = false;
		window.setVisible(true);

	}
	//***********************************************************
	
	
	//Load "puzzle" files that are not mze or sav but rather good old-fashioned imgs
	public static void loadImage(String filename)
	{
		timer.restartTimer();
		master.fillImg(filename);
		// -----------------------------------------------------
		
		
		if (master.played()) 
		{
			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridL.getLeft()[i].setIcon(master.getSaved()[i].getIcon());
				tgridL.getLeft()[i].setIdentifier(master.getSaved()[i].getIdentifier());
				tgridL.getLeft()[i].setRotationIdentifier(master.getSaved()[i].getRotationIdentifier());
				tgridL.getLeft()[i].setLoc(master.getSaved()[i].isOnBoard());
				tgridL.add(tgridL.getLeft()[i]);
			}
			
			// ------------------------------------------------------
			// Tgrid8_16 set Up

			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridR.getRight()[i].setIcon(master.getSaved()[i + 8].getIcon());
				tgridR.getRight()[i].setIdentifier(master.getSaved()[i + 8].getIdentifier());
				tgridR.getRight()[i].setRotationIdentifier(master.getSaved()[i + 8].getRotationIdentifier());
				tgridR.getRight()[i].setLoc(master.getSaved()[i + 8].isOnBoard());
				tgridR.add(tgridR.getRight()[i]);
			}
			// ------------------------------------------------------
		} 
		
		else {
			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridL.getLeft()[i].setIcon(master.getLeft()[i].getIcon());
				tgridL.getLeft()[i].setIdentifier(master.getLeft()[i].getIdentifier());
				tgridL.getLeft()[i].setRotationIdentifier(master.getLeft()[i].getRotationIdentifier());
				tgridL.getLeft()[i].setLoc(master.getLeft()[i].isOnBoard());
				tgridL.add(tgridL.getLeft()[i]);
			}
			// ------------------------------------------------------
			// Tgrid8_16 set Up

			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridR.getRight()[i].setIcon(master.getRight()[i].getIcon());
				tgridR.getRight()[i].setIdentifier(master.getRight()[i].getIdentifier());
				tgridR.getRight()[i].setRotationIdentifier(master.getRight()[i].getRotationIdentifier());
				tgridR.getRight()[i].setLoc(master.getRight()[i].isOnBoard());
				tgridR.add(tgridR.getRight()[i]);
			}
		}
		if (master.played()) {
			for (int i = 0; i < gb.getTiles().length; i++) {
				
				gb.getTiles()[i].setIcon(master.getSaved()[i+16].getIcon());
				gb.getTiles()[i].setIdentifier(master.getSaved()[i+16].getIdentifier());
				gb.getTiles()[i].setRotationIdentifier(master.getSaved()[i+16].getRotationIdentifier());
				gb.getTiles()[i].setLoc(master.getSaved()[i+16].isOnBoard());
				gb.add(gb.getTiles()[i]);
				
			}
		} else {
			for (int i = 0; i < gb.getTiles().length; i++) // add tiles to board
			{
				gb.getTiles()[i].setBorder(BorderFactory.createEmptyBorder());
				gb.getTiles()[i].setLoc(true);
				
			}
			gb.setOpaque(false);
		}
		
		// -----------------------------------------------------
		
		int x = 5;
		int y = 5;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(x, y, 0, 0);
		c.ipady = 0;
		c.ipadx = 0;
		c.weightx = 0.0001;
		c.weighty = 0.0001;
		c.gridx = 0;
		c.gridy = 0;
		int i = 0;
		while (i >= 0 && i <= 3) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 85;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 4 && i <= 7) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 165;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 8 && i <= 11) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 245;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 12 && i <= 15) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}
		stateChange = false;
	}
	
	
	//Load Professor Buckner's file type i.e. the one which is all bytes
	public static void loadByteFile(String filename) 
	{
		timer.restartTimer();
		master = new TileGrid();
		master.fill(filename);
		// -----------------------------------------------------
		
		
		if (master.played()) 
		{
			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridL.getLeft()[i].setIcon(master.getSaved()[i].getIcon());
				tgridL.getLeft()[i].setIdentifier(master.getSaved()[i].getIdentifier());
				tgridL.getLeft()[i].setRotationIdentifier(master.getSaved()[i].getRotationIdentifier());
				tgridL.getLeft()[i].setLoc(master.getSaved()[i].isOnBoard());
				tgridL.add(tgridL.getLeft()[i]);
			}
			
			// ------------------------------------------------------
			// Tgrid8_16 set Up

			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridR.getRight()[i].setIcon(master.getSaved()[i + 8].getIcon());
				tgridR.getRight()[i].setIdentifier(master.getSaved()[i + 8].getIdentifier());
				tgridR.getRight()[i].setRotationIdentifier(master.getSaved()[i + 8].getRotationIdentifier());
				tgridR.getRight()[i].setLoc(master.getSaved()[i + 8].isOnBoard());
				tgridR.add(tgridR.getRight()[i]);
			}
			// ------------------------------------------------------
		} 
		
		else {
			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridL.getLeft()[i].setIcon(master.getLeft()[i].getIcon());
				tgridL.getLeft()[i].setIdentifier(master.getLeft()[i].getIdentifier());
				tgridL.getLeft()[i].setRotationIdentifier(master.getLeft()[i].getRotationIdentifier());
				tgridL.getLeft()[i].setLoc(master.getLeft()[i].isOnBoard());
				tgridL.add(tgridL.getLeft()[i]);
			}
			// ------------------------------------------------------
			// Tgrid8_16 set Up

			for (int i = 0; i < 8; i++) // add tiles to grid
			{
				tgridR.getRight()[i].setIcon(master.getRight()[i].getIcon());
				tgridR.getRight()[i].setIdentifier(master.getRight()[i].getIdentifier());
				tgridR.getRight()[i].setRotationIdentifier(master.getRight()[i].getRotationIdentifier());
				tgridR.getRight()[i].setLoc(master.getRight()[i].isOnBoard());
				tgridR.add(tgridR.getRight()[i]);
			}
		}
		if (master.played()) {
			for (int i = 0; i < gb.getTiles().length; i++) {
				
				gb.getTiles()[i].setIcon(master.getSaved()[i+16].getIcon());
				gb.getTiles()[i].setIdentifier(master.getSaved()[i+16].getIdentifier());
				gb.getTiles()[i].setRotationIdentifier(master.getSaved()[i+16].getRotationIdentifier());
				gb.getTiles()[i].setLoc(master.getSaved()[i+16].isOnBoard());
				gb.add(gb.getTiles()[i]);
			}
		} else {
			for (int i = 0; i < gb.getTiles().length; i++) // add tiles to board
			{
				gb.getTiles()[i].setBorder(BorderFactory.createEmptyBorder());
				gb.getTiles()[i].setLoc(true);
				
			}
			gb.setOpaque(false);
		}
		
		// -----------------------------------------------------
		
		int x = 5;
		int y = 5;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(x, y, 0, 0);
		c.ipady = 0;
		c.ipadx = 0;
		c.weightx = 0.0001;
		c.weighty = 0.0001;
		c.gridx = 0;
		c.gridy = 0;
		int i = 0;
		while (i >= 0 && i <= 3) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 85;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 4 && i <= 7) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 165;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 8 && i <= 11) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}

		x = 245;
		y = 5;
		c.insets = new Insets(x, y, 0, 0);
		while (i >= 12 && i <= 15) {
			gb.add(gb.getTiles()[i], c);
			y += 80;
			c.insets = new Insets(x, y, 0, 0);
			i++;
		}
		stateChange = false;
	}

	// -----------------------------------------------------
	
	//Dispose of the menu dialog that pops up when "File" button is pressed
	//Static becasue it is called by Load class not MiniGame but the dialog is created in this class
	public static void closeMenu(){ menu.dispose(); }
	
	
	// Return info about the game
	public static boolean getStateChange() {
		return stateChange;
	}

	public static void setState(boolean val) {
		stateChange = val;
	}

	public static boolean getWon() {
		return isWon;
	}

	public static void setIsDefault(boolean val)
	{
		isDefault = val;
	}
	
	public static TileGrid getLeft() {
		return tgridL;
	}

	public static TileGrid getRight() {
		return tgridR;
	}

	public static GameBoard getBoard() {
		return gb;
	}
	
	public static TileGrid getMaster() {
		return master;
	}
	
	public static void printBol()
	{
		System.out.print("Default: " + isDefault );
		System.out.println();
		System.out.print("State change: " + stateChange);
		System.out.println();
		System.out.print("Won: "+ isWon);
		System.out.println();
	}

	public void printRot()
	{
		for(int i=0; i<8; i++)
		{
			System.out.print(tgridL.getLeft()[i].getRotationIdentifier());
			System.out.println();
			System.out.print(tgridR.getRight()[i].getRotationIdentifier());
			System.out.println();
		}
	}
	// These are only here due to implementation of MouseListener
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
