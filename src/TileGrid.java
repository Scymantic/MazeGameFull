import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * has several fill() methods to "fill" the tile grids
 * two work with mze files and 2 work with images only
 * one of each of those two requires a path
 * 
 * also responsible for rotating the images, implementation of save and load,
 * opening i.e. creating a new FileIn, reset functionality, etc.
 * 
 * @author AdrianoBarberis
 *
 */
@SuppressWarnings("null")
public class TileGrid extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private GridLayout layout = new GridLayout(8,1);
	private JPanel grid;
	private String name;
	private String nameOfFile;
	private FileIn file;
	private Maze img;
	private long time;
	
	private ImageIcon[][] rotatedImages;
	private static Tile[] tiles;


	private Tile[] left;


	private Tile[] right;


	private Tile[] rightReset;


	private Tile[] leftReset;


	private Tile[] solution;


	private static Tile[] saved;
	
	
	private Color vanilla ;
	private Dimension size;
	//*****************************
	
	
	
	@SuppressWarnings("unqualified-field-access")
	public TileGrid(){ grid = new JPanel(layout); }
	//++++++++++++++++++++++++++++++++ 
	
	
	/**
	 * Fill for Images only does same thing as all other fills do
	 * no path required for this version
	 */
	
	public void fillImg()
	{
		instantiate();
		open();
		//-----------------------------------------------------

		
		
		//Fill array
		for(int i = 0; i<tiles.length; i++)
		{
			tiles[i] = new Tile();
    		tiles[i].setPreferredSize(size);
    		tiles[i].setForeground(Color.BLUE);
    		
    		
    		tiles[i].setIcon(img.getImages(i));
    		tiles[i].setIdentifier(i + 1);
		}
		
		//Set Up Solution array
		setSolve();
		//-----------------------------------------------------

		
		
		
		//Shuffle Array and Split it into 2
		List<Tile> temp; 
		temp = Arrays.asList(tiles);
		Collections.shuffle(temp);
		tiles = (Tile[])temp.toArray();
		

		System.arraycopy(tiles, 0, left, 0, left.length);
		System.arraycopy(tiles, right.length, right, 0, right.length);
		//-----------------------------------------------------

		
		
		for(int  i = 0; i<left.length; i++)
		{
    		//set up for masters
    		leftReset[i] = new Tile();
    		leftReset[i].setPreferredSize(size);
    		leftReset[i].setForeground(new Color(243,229,171));
    		
    		
    		leftReset[i].setIcon(left[i].getIcon());
    		leftReset[i].setIdentifier(left[i].getIdentifier());
    		
    		rightReset[i] = new Tile();
    		rightReset[i].setPreferredSize(size);
    		rightReset[i].setForeground(Color.BLUE);
    		
    		
    		rightReset[i].setIcon(right[i].getIcon());
    		rightReset[i].setIdentifier(right[i].getIdentifier());
    		
		}
		//-----------------------------------------------------

		fillRotatedImages();
		
		//-----------------------------------------------------
		rotateL();
		rotateR();

	}
	//-----------------------------------------------------
	
	
	/**
	 * Fill for Images only does same thing as all other fills do
	 * @param path
	 */
	public void fillImg(String path)
	{
		instantiate();
		open(path);
		//-----------------------------------------------------

		
		
		//Fill array
		for(int i = 0; i<tiles.length; i++)
		{
			tiles[i] = new Tile();
    		tiles[i].setPreferredSize(size);
    		tiles[i].setForeground(Color.BLUE);
    		
    		
    		tiles[i].setIcon(img.getImages(i));
    		tiles[i].setIdentifier(i + 1);
		}
		
		//Set Up Solution array
		setSolve();
		//-----------------------------------------------------

		
		
		
		//Shuffle Array and Split it into 2
		List<Tile> temp; 
		temp = Arrays.asList(tiles);
		Collections.shuffle(temp);
		tiles = (Tile[])temp.toArray();
		

		System.arraycopy(tiles, 0, left, 0, left.length);
		System.arraycopy(tiles, right.length, right, 0, right.length);
		//-----------------------------------------------------

		
		
		for(int  i = 0; i<left.length; i++)
		{
    		//set up for masters
    		leftReset[i] = new Tile();
    		leftReset[i].setPreferredSize(size);
    		leftReset[i].setForeground(new Color(243,229,171));
    		
    		
    		leftReset[i].setIcon(left[i].getIcon());
    		leftReset[i].setIdentifier(left[i].getIdentifier());
    		
    		rightReset[i] = new Tile();
    		rightReset[i].setPreferredSize(size);
    		rightReset[i].setForeground(Color.BLUE);
    		
    		
    		rightReset[i].setIcon(right[i].getIcon());
    		rightReset[i].setIdentifier(right[i].getIdentifier());
    		
		}
		//-----------------------------------------------------

		fillRotatedImages();
		
		//-----------------------------------------------------
		rotateL();
		rotateR();

	}
	//-----------------------------------------------------
	
	
	
	
	@SuppressWarnings("unqualified-field-access")
	/**
	 * THIS FILL() IS FOR DEFAULT MAZE ONLY
	 */
	public void fill()
	{
		instantiate();
		open();
		//-----------------------------------------------------

		
		
		//Fill array
		for(int i = 0; i<tiles.length; i++)
		{
			tiles[i] = new Tile();
    		tiles[i].setPreferredSize(size);
    		tiles[i].setForeground(Color.BLUE);
    		
    		
    		tiles[i].setIcon(img.getImages(i));
    		tiles[i].setIdentifier(i + 1);
		}
		
		//Set Up Solution array
		setSolve();
		//-----------------------------------------------------

		
		
		
		//Shuffle Array and Split it into 2
		List<Tile> temp; 
		temp = Arrays.asList(tiles);
		Collections.shuffle(temp);
		tiles = (Tile[])temp.toArray();
		

		System.arraycopy(tiles, 0, left, 0, left.length);
		System.arraycopy(tiles, right.length, right, 0, right.length);
		//-----------------------------------------------------

		
		
		for(int  i = 0; i<left.length; i++)
		{
    		//set up for masters
    		leftReset[i] = new Tile();
    		leftReset[i].setPreferredSize(size);
    		leftReset[i].setForeground(new Color(243,229,171));
    		
    		
    		leftReset[i].setIcon(left[i].getIcon());
    		leftReset[i].setIdentifier(left[i].getIdentifier());
    		
    		rightReset[i] = new Tile();
    		rightReset[i].setPreferredSize(size);
    		rightReset[i].setForeground(Color.BLUE);
    		
    		
    		rightReset[i].setIcon(right[i].getIcon());
    		rightReset[i].setIdentifier(right[i].getIdentifier());
    		
		}
		//-----------------------------------------------------

		fillRotatedImages();
		
		//-----------------------------------------------------

		if(FileIn.getIsImage()==false)
		{
			for (int i = 0; i < saved.length; i++)
			{
				int tileNum = (int) file.getTileNumber().elementAt(i);
				int rotation =  (int) file.getRotation().elementAt(i);
				saved[tileNum] = new Tile();
				saved[tileNum].setPreferredSize(size);
				saved[tileNum].setForeground(Color.BLUE);
				saved[tileNum].setIdentifier(i + 1);
				if (i < 16)
				{
					saved[tileNum].setIcon(rotatedImages[rotation][i]);
					saved[tileNum].setRotationIdentifier(rotation);
				}
    			
			}
		}
		//-----------------------------------------------------

		
		
		
		//Rotate the images in the left and right tile arrays
		rotateL();
		rotateR();
		
		
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * This fill is for use with any maze file
	 * @param filename
	 */
	
	public void fill(String filename)
	{
	
		instantiate();
		open(filename);
		//-----------------------------------------------------

		
		
		//Fill array
		for(int i = 0; i<tiles.length; i++)
		{
			tiles[i] = new Tile();
    		tiles[i].setPreferredSize(size);
    		tiles[i].setForeground(Color.BLUE);
    		
    		
    		tiles[i].setIcon(img.getImages(i));
    		tiles[i].setIdentifier(i + 1);
    		//System.out.print(tiles);
		}
		
		//Set Up Solution array
		setSolve();
		//-----------------------------------------------------

		
		
		//Shuffle Array and Split it into 2
		List<Tile> temp; 
		temp = Arrays.asList(tiles);
		Collections.shuffle(temp);
		tiles = (Tile[])temp.toArray();
		

		System.arraycopy(tiles, 0, left, 0, left.length);
		System.arraycopy(tiles, right.length, right, 0, right.length);
		//-----------------------------------------------------

		
		
		
		for(int  i = 0; i<left.length; i++)
		{
    		//set up for masters
    		leftReset[i] = new Tile();
    		leftReset[i].setPreferredSize(size);
    		leftReset[i].setForeground(new Color(243,229,171));
    		
    		
    		leftReset[i].setIcon(left[i].getIcon());
    		leftReset[i].setIdentifier(left[i].getIdentifier());
    		
    		rightReset[i] = new Tile();
    		rightReset[i].setPreferredSize(size);
    		rightReset[i].setForeground(Color.BLUE);
    		
    		
    		rightReset[i].setIcon(right[i].getIcon());
    		rightReset[i].setIdentifier(right[i].getIdentifier());
    		
		}
		//-----------------------------------------------------

		
		fillRotatedImages();
		//System.out.println();
		//-----------------------------------------------------

		//if(FileIn.getIsImage() == false)
		{
			for (int i = 0; i < saved.length; i++)
			{
				int tileNum = (int) file.getTileNumber().elementAt(i);
				int rotation =  (int) file.getRotation().elementAt(i);
				saved[tileNum] = new Tile();
				saved[tileNum].setPreferredSize(size);
				saved[tileNum].setForeground(Color.BLUE);
				saved[tileNum].setIdentifier(i + 1);
    		//System.out.print(saved);
				if (i < 16)
				{
					saved[tileNum].setIcon(rotatedImages[rotation][i]);
					saved[tileNum].setRotationIdentifier(rotation);
				}
    			
			}
		}
		//-----------------------------------------------------

		
		
		//Rotate the images in the left and right tile arrays
		rotateL();
		rotateR();
		
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public void reset()
	{
		for(int i = 0; i<left.length; i++)
		{
			//reset but maintain old location
			left[i] = leftReset[i];
    		right[i] = rightReset[i];
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	/**
	 * This ensures that only one "master" version of each array exists.
	 * Part of the problem of previous iterations was that because the majority of 
	 * this was done through the constructor you would end up with 8 differently shuffled arrays riddled
	 * with possible duplicate images, or missing images.  this way however the program can still instantiate the
	 * TileGrid array but i allows it the freedom of only actually filling one version the TileGrid i.e. the master grid
	 * This way all subsequent grids get their data from the master grid and there are no issues.  This is also why
	 * the constructor does not actually call the fill() method.
	 */
	@SuppressWarnings("unqualified-field-access")
	public void instantiate()
	{
		saved = new Tile[32];
		rotatedImages = new ImageIcon[4][16];
		left = new Tile[8];
		right = new Tile[8];
		tiles = new Tile[16];
		solution = new Tile[16];
		leftReset = new Tile[8];
		rightReset = new Tile[8];
		vanilla = new Color(243,229,171);
		size = new Dimension(87,87);
		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++
	
	@SuppressWarnings("unqualified-field-access")
	public void rotateL()
	{
		int i = 0;
		
		//modifier to rotate 90/180/270/360 degrees 
		int rotation =  ThreadLocalRandom.current().nextInt(1, 4 + 1); 
		
		while(i<left.length)
		{
			int width = left[i].getIcon().getIconWidth();
			int height = left[i].getIcon().getIconHeight();
			{
				BufferedImage bimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
				Graphics g = bimage.createGraphics();
				// paint the Icon to the BufferedImage.
				left[i].getIcon().paintIcon(null, g, 0,0);
				g.dispose();

				AffineTransform rotate = new AffineTransform();
				rotate.rotate(Math.PI / 2, width / 2, height / 2);
				AffineTransformOp scaleOp = new AffineTransformOp(
				        rotate, AffineTransformOp.TYPE_BILINEAR);
				
				bimage = scaleOp.filter(bimage, null);
	            ImageIcon icon = new ImageIcon(bimage);
	            left[i].setIcon(icon);
	            left[i].setRotationIdentifier(rotation-1);
	            leftReset[i].setIcon(icon);
				
			}
		
			rotation =  ThreadLocalRandom.current().nextInt(1, 4 + 1); 
			i++;
			
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	
	@SuppressWarnings("unqualified-field-access")
	public void rotateR()
	{
		int i = 0;
		
		//modifier to rotate 90/180/270/360 degrees 
		int rotation =  ThreadLocalRandom.current().nextInt(1, 4 + 1); 
		
		
		while(i<right.length)
		{
			int width = right[i].getIcon().getIconWidth();
			int height = right[i].getIcon().getIconHeight();
			
			{
				BufferedImage bimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
				Graphics g = bimage.createGraphics();
				// paint the Icon to the BufferedImage.
				right[i].getIcon().paintIcon(null, g, 0,0);
				g.dispose();

				AffineTransform rotate = new AffineTransform();
				rotate.rotate((Math.PI / 2)*rotation, width / 2, height / 2);
				AffineTransformOp scaleOp = new AffineTransformOp(
				        rotate, AffineTransformOp.TYPE_BILINEAR);
				
				bimage = scaleOp.filter(bimage, null);
	            ImageIcon icon = new ImageIcon(bimage);
	            right[i].setIcon(icon);
	            right[i].setRotationIdentifier(rotation-1);
	            rightReset[i].setIcon(icon);
				
			}
			
			rotation =  ThreadLocalRandom.current().nextInt(1, 4 + 1); 
			i++;
			
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	
	@SuppressWarnings({ "unqualified-field-access", "nls" })
	public void setSolve() 
	{
		//SetUp for Solution:
    	
    	for(int i = 0; i<solution.length; i++)
    	{
    		solution[i] = new Tile();
    		solution[i].setPreferredSize(new Dimension(80,80));
    		solution[i].setIcon(img.getUnshuffled(i));
    		solution[i].setIdentifier(i + 1); 
    
    	}
    
    }
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public void open(String path)
	{
		file = new FileIn(path);
		img = new Maze(file.getImage());
	}
	//++++++++++++++++++++++++++++++++++++++++++

	
	public void open()
	{
		file = new FileIn();
		img = new Maze(file.getImage());
	}
	//++++++++++++++++++++++++++++++++++++++++++

	
	public void fillRotatedImages()
	{
		int width = getSol()[0].getIcon().getIconWidth();
		int height = getSol()[0].getIcon().getIconHeight();
		for (int c = 0; c < 16; c++)
		{
			BufferedImage bimage = new BufferedImage(width,height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = bimage.createGraphics();
			// paint the Icon to the BufferedImage.
			getSol()[c].getIcon().paintIcon(null, g, 0,0);
			g.dispose();
			rotatedImages[0][c] = (ImageIcon) getSol()[c].getIcon();
			for (int r = 1; r < 4; r++)
			{
				AffineTransform rotate = new AffineTransform();
				rotate.rotate(Math.PI / 2, width / 2, height / 2);
				AffineTransformOp scaleOp = new AffineTransformOp(rotate, AffineTransformOp.TYPE_BILINEAR);
			
				bimage = scaleOp.filter(bimage, null);
				ImageIcon icon = new ImageIcon(bimage);
				rotatedImages[r][c] = icon;
			}
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++

	
	public boolean played()
	{
		return file.getPlayed();
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	public String getFilePath()
	{
		return file.getFilePath();
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	@SuppressWarnings({ "resource", "unqualified-field-access", "hiding" })
	public void load(File file, TileGrid tgr, GameBoard g) throws ClassNotFoundException, IOException
	{
		FileInputStream loadFile = new FileInputStream(file);
		ObjectInputStream load = new ObjectInputStream(loadFile);
		for(int i = 0; i < left.length; i++)
		{
			left[i].load(load);
		}
		for(int i = 0; i < tgr.getRight().length; i++)
		{
			tgr.getRight()[i].load(load);
		}
		for(int i = 0; i < g.getTiles().length; i++)
		{
			g.getTiles()[i].load(load);
		}
		load.close();
		
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	public void save(ObjectOutputStream save, TileGrid tgr, GameBoard g) throws IOException
	{
		for(int i = 0; i < left.length; i++)
		{
			left[i].save(save);
		}
		for(int i = 0; i < tgr.getRight().length; i++)
		{
			tgr.getRight()[i].save(save);
		}
		for(int i = 0; i < g.getTiles().length; i++)
		{
			g.getTiles()[i].save(save);
		}
		
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	//Bunch of get Methods plus reset method
	
	//ResetR is random reset
	public void resetR() { fill(); } //For Clarification Purposes I know its redundant
	
	public static boolean isInitialized()
	{
		if(tiles[1] == null || saved[1] == null){return false;}
		else{return true;}
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Tile[] getLeft() { return left; }
	@SuppressWarnings("unqualified-field-access")
	public Tile[] getRight() { return right; }
	@SuppressWarnings("unqualified-field-access")
	public Tile[] getSol() { return solution; }
	public ImageIcon[][] getRotatedImages() { return rotatedImages; }
	public Tile[] getSaved() { return saved; }
	public long getTime(){ return file.getTime(); }
	
	
	
	
}
