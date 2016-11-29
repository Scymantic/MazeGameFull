import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;

import javax.swing.ImageIcon;

@SuppressWarnings({ "serial", "null" })
public class Maze extends Component
{
	private ImageIcon img;
	private Image image;
	
	private Image[] images;  //original
	private Image[] master; // copy of Original
	private Image[] unshuffled; // for solution
	
	
	//private String name = "default";
	private BufferedImage bImage;
	//************************************************
	
	
	/** This Constructor is used only internally really, it takes an image
	 * directly and therefore is more suited for use with FileIn class 
	 * which returns an image of the maze.
	 * 
	 * @param Image img
	 */
	
	public Maze(Image img)
	{
		editImage2(img);
	}
	
	
	
	//Used with FileIn 
	@SuppressWarnings("unqualified-field-access")
	public void editImage2(Image image)
	{
		
		
		//try getting img
		
		try
		{
			bImage = loadImage(image);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		//---------------------------------------------
		
		
		bImage = resizeImage(bImage); //resize img for 16x16 crop
		
		
		images = new Image[16];
		master = new Image[16];
		unshuffled = new Image[16];
		
		
		//Crop image and send crops to arrays
		int index = 0;
		for (int i = 0; i < 4; i++) 
		{
            for (int j = 0; j < 4; j++) 
            {
                image = createImage(new FilteredImageSource(bImage.getSource(),
                        new CropImageFilter(j * (bImage.getWidth() / 4), i * (bImage.getHeight() / 4),
                                (bImage.getWidth() / 4), (bImage.getHeight() / 4))));
                images[index] = image;
                unshuffled[index] = image;
                master[index] = image;
                index++;
            }
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	
	//Resize Image to fit GameBoard
	@SuppressWarnings("static-method")
	private BufferedImage resizeImage(BufferedImage original) 
	{
        BufferedImage resized = new BufferedImage(320, 320, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(original, 0, 0, 320, 320, null);
        g.dispose();
        return resized;
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
		
	/**
	 * 
	 * Takes an Image and returns that image as a BufferedImage
	 * 
	 * @param Image img
	 * @return
	 * @throws IOException
	 */
	
	@SuppressWarnings("static-method")
	private BufferedImage loadImage(Image img) throws IOException 
	{
		    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
		        BufferedImage.TYPE_INT_ARGB_PRE);

		    Graphics g = bimage.createGraphics();
		    g.drawImage(img, 0, 0, null);
		    g.dispose();
		    return bimage;
    }
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	//A bunch of gets for all the arrays
	
	
	@SuppressWarnings("unqualified-field-access")
	public ImageIcon getUnshuffled(int i)
	{
		img = new ImageIcon(unshuffled[i]);
		return img;
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	
	@SuppressWarnings("unqualified-field-access")
	public ImageIcon getImages(int i)
	{
		img = new ImageIcon(images[i]);
		return img;
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	
	@SuppressWarnings("unqualified-field-access")
	public ImageIcon getMaster(int i)
	{
		img = new ImageIcon(master[i]);
		return img;
	}
	//++++++++++++++++++++++++++++++++++++++++++
	
		
}
