
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings("null")
public class FileIn
{
	private static String name;
	private FileInputStream fileInputStream = null;
	private URL url;
	private File file = null;
	private int numOfChunks;
	private byte[] bFile;
	private byte[][] data;
	private Vector<Integer> lines;
	private Vector<Float> coordinates;
	private Vector<Integer> rotation;
	private boolean played;
	private String nameOfFile;
	
	@SuppressWarnings("rawtypes")
	private Vector<Integer> tiles;
	private JFrame frame;
	
	private int start;
	private int end;
	private int offsetx;
	private int offsety;
	private int counter;
	private long time;
	private Image image;
	private static boolean isImage = false;
	//****************************************************
	

	@SuppressWarnings("unqualified-field-access")
	public FileIn()
	{
		image = digitize();
	}
	//-----------------------------------------------------
	
	public FileIn(String path)
	{
		image = digitize(path);
	}
	//-----------------------------------------------------

	
	
	/**
	 * 
	 * LOADS ANY VERSION OF MAZE
	 * creates image from maze file
	 * 
	 * @param path
	 * @return
	 */
	public Image digitize(String path)
	{
		fileInputStream=null; 
        file = null;
        
        lines = new Vector<Integer>();
        tiles = new Vector<Integer>();
        coordinates = new Vector<Float>();
        rotation = new Vector<Integer>();
        
        frame = new JFrame();
        frame.setSize(400, 400); 
        frame.setUndecorated(true);
        
      //-----------------------------------------------------
        
        
        
        file = new File(path);
		nameOfFile = path;
		String extension = file.getName().substring(
    			file.getName().lastIndexOf(".") + 1, file.getName().length());
    	if(extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png"))
    	{
    		isImage  = true;
    		
    		try {
				return ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
		 bFile = new byte[(int)file.length()];
		//-----------------------------------------------------
		
		
		
        //Attempt Read File
        try 
        {
            //convert file into array of bytes
        	fileInputStream = new FileInputStream(file);
        	fileInputStream.read(bFile);
        	fileInputStream.close();
  
        	numOfChunks = (int)Math.ceil((double)bFile.length / 4);
        	data = new byte[numOfChunks][];
        
        	data = chunkArray(bFile,4);	
        }
        catch(Exception e)
        {
        	e.printStackTrace();

        }
        
      //-----------------------------------------------------
        
        
        played = false;
        byte[] test = {bFile[0], bFile[1], bFile[2], bFile[3]};
        StringBuilder sb = new StringBuilder();
        for (byte b : test) {
            sb.append(String.format("%02X", b));
        }
        if (sb.toString().equals("CAFEDEED"))
        	played = true;
        byte[] thyme = {bFile[8], bFile[9], bFile[10], bFile[11], 
        		bFile[12], bFile[13], bFile[14], bFile[15]};
        time = Converter.convertToLong(thyme);

      //-----------------------------------------------------
        
        
        //Read Data and Convert
        int count = 1;
        for(int i=4; i<data.length; i++)
        {
			if (data[i][0] == 0) 
			{
				if (count == 1) 
				{
					tiles.add(Converter.convertToInt(data[i]));
					count++;
				} 
				else if (count == 2) 
				{
					rotation.add(Converter.convertToInt(data[i]));
					count++;
				} 
				else if (count == 3) 
				{
					lines.add(Converter.convertToInt(data[i]));
					count = 1;
        		}        		
        	}
        	else if(data[i][0] != 0)
        	{
        		coordinates.add(Converter.convertToFloat(data[i]));
        	}
        }
        for(int i = 0; i < 32; i++) // this is only here because his file only contains 16 tiles instead of 32
        {
        	if(!tiles.contains(i))
        	{
        		tiles.add(i);
        		rotation.add(0);
        	}
        }
      //-----------------------------------------------------

      //-----------------------------------------------------
        
        //Set up image to draw maze on
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
      //-----------------------------------------------------
        
        
      
        //draw maze to image
        start = 0;
        end = lines.get(0)*4;
        offsetx = 99;
        offsety = 0;
        counter = 1;
        
      //Since first iteration is slightly different it requires a separate loop  
      for(int i=start; i<end; i+=4)
      {
    	  if((i+3)<=(int)lines.get(0)*4)
    	  {
    		  int x1 = Math.round(coordinates.get(i));
    		  int y1 = Math.round(coordinates.get(i+1));
    		  int x2 = Math.round(coordinates.get(i+2));
    		  int y2 = Math.round(coordinates.get(i+3));
    		  g2d.drawLine(x1, y1, x2, y2);
    		  
    	  }
      }
    //-----------------------------------------------------
      
      for(int i=1; i<lines.size(); i++)
      {
    	  start = end;
    	  end += (int)lines.get(i)*4;
    	  counter++;
    	  for(int j = start; j<end; j+=4)
    	  {
    		  if((j+3)<=end)
    		  {

    			  int x1 = offsetx+Math.round(coordinates.get(j));
        		  int y1 = offsety+Math.round(coordinates.get(j+1));
        		  int x2 = offsetx+Math.round(coordinates.get(j+2));
        		  int y2 = offsety+Math.round(coordinates.get(j+3));
        		  g2d.drawLine(x1, y1, x2, y2);
    		  }
    	  }
    	  
    	  offsetx += 99;
    	  if(counter%4 == 0)
    	  {
    		  offsety+=99;
    		  offsetx = 0;
    	  }
      }
    
     
      File temp = new File("Test.png");
      try {
		ImageIO.write(image, "png", temp);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return image;
      
    //-----------------------------------------------------
      
	}
	
	
	
	
	
	
	
	
	/**
	 * LOADS DEFAULT MAZE ONLY!!
	 * creates and returns an image of the maze generated from the datafile
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings({ "unqualified-field-access", "unchecked", "rawtypes", "nls", "boxing", "cast" })
	public Image digitize()
	{
		MiniGame.setIsDefault(true);
	     MiniGame.setState(false);
		fileInputStream=null; 
        file = null;
        
        lines = new Vector<Integer>();
        tiles = new Vector<Integer>();
        coordinates = new Vector<Float>();
        rotation = new Vector();
        
        frame = new JFrame();
        frame.setSize(400, 400); 
        frame.setUndecorated(true);
        
      //-----------------------------------------------------
        
        
        
        //Attempt Open File
        	while(file == null)
        	{
        		file = open();
        	}
        		String extension = file.getName().substring(
        			file.getName().lastIndexOf(".") + 1, file.getName().length());
        		if(extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png"))
        		{
        			isImage  = true;
        		
        			try {
        				return ImageIO.read(file);
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		}
		 bFile = new byte[(int)file.length()];
		//-----------------------------------------------------
		
		
		
        //Attempt Read File
        try 
        {
            //convert file into array of bytes
        	fileInputStream = new FileInputStream(file);
        	fileInputStream.read(bFile);
        	fileInputStream.close();
  
        	numOfChunks = (int)Math.ceil((double)bFile.length / 4);
        	data = new byte[numOfChunks][];
        
        	data = chunkArray(bFile,4);
        	
        }
        catch(Exception e)
        {
        	e.printStackTrace();

        }
        
        
      //-----------------------------------------------------
        
        played = false;
        byte[] test = {bFile[0], bFile[1], bFile[2], bFile[3]};
        StringBuilder sb = new StringBuilder();
        for (byte b : test) {
            sb.append(String.format("%02X", b));
        }
        if (sb.toString().equals("CAFEDEED"))
        	played = false;
        byte[] thyme = {bFile[8], bFile[9], bFile[10], bFile[11], bFile[12], bFile[13], bFile[14], bFile[15]};
        time = Converter.convertToLong(thyme);
      //-----------------------------------------------------
        
        //Read Data and Convert
        int count = 1;
        for(int i=4; i<data.length; i++)
        {
			if (data[i][0] == 0) 
			{
				if (count == 1) 
				{
					tiles.add(Converter.convertToInt(data[i]));
					count++;
				} 
				else if (count == 2) 
				{
					rotation.add(Converter.convertToInt(data[i]));
					count++;
				} 
				else if (count == 3) 
				{
					lines.add(Converter.convertToInt(data[i]));
					count = 1;
        		}        		
        	}
        	else if(data[i][0] != 0)
        	{
        		coordinates.add(Converter.convertToFloat(data[i]));
        	}
        }
        for(int i = 16; i < 32; i++) // this is only here because his file only contains 16 tiles instead of 32
        {
        	tiles.add(i);
        	rotation.add(0);
        }
        
      //-----------------------------------------------------

      //-----------------------------------------------------
        
        //Set up image to draw maze on
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
      //-----------------------------------------------------
      
       //draw maze to image
        start = 0;
        end = lines.get(0)*4;
        offsetx = 99;
        offsety = 0;
        counter = 1;
        
      //Since first iteration is slightly different it requires a separate loop  
      for(int i=start; i<end; i+=4)
      {
    	  if((i+3)<=(int)lines.get(0)*4)
    	  {
    		  int x1 = Math.round(coordinates.get(i));
    		  int y1 = Math.round(coordinates.get(i+1));
    		  int x2 = Math.round(coordinates.get(i+2));
    		  int y2 = Math.round(coordinates.get(i+3));
    		  g2d.drawLine(x1, y1, x2, y2);
    		  
    	  }
      }
    //-----------------------------------------------------
      
      
      for(int i=1; i<lines.size(); i++)
      {
    	  start = end;
    	  end += (int)lines.get(i)*4;
    	  counter++;
    	  for(int j = start; j<end; j+=4)
    	  {
    		  if((j+3)<=end)
    		  {

    			  int x1 = offsetx+Math.round(coordinates.get(j));
        		  int y1 = offsety+Math.round(coordinates.get(j+1));
        		  int x2 = offsetx+Math.round(coordinates.get(j+2));
        		  int y2 = offsety+Math.round(coordinates.get(j+3));
        		  g2d.drawLine(x1, y1, x2, y2);
    		  }
    	  }
    	  
    	  offsetx += 99;
    	  if(counter%4 == 0)
    	  {
    		  offsety+=99;
    		  offsetx = 0;
    	  }
      }
    
    //-----------------------------------------------------
     // print();
      return image;
      
    //-----------------------------------------------------
   
      
	}
	//-----------------------------------------------------
	
	
	
	
	//Stolen from https://gist.github.com/lesleh/7724554 but changed type to byte instead of int
	//Chunks out array into AxB arrays
	 public static byte[][] chunkArray(byte[] array, int chunkSize) 
	 {
	        int numOfChunks = (int)Math.ceil((double)array.length / chunkSize);
	        byte[][] output = new byte[numOfChunks][];

	        for(int i = 0; i < numOfChunks; ++i) {
	            int start = i * chunkSize;
	            int length = Math.min(array.length - start, chunkSize);

	            byte[] temp = new byte[length];
	            System.arraycopy(array, start, temp, 0, length);
	            output[i] = temp;
	        }

	        return output;
	    }
	//-----------------------------------------------------
	 
	 public File open()
	 {
		 File temp = null;
		 final JFileChooser fc = new JFileChooser();
	        fc.setDialogTitle("Open DEFAULT ONLY");
	        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("SAV,MZE,JPG,PNG", "sav", "mze", 
					"jpg", "png", "jpeg","All");
			File currentdir = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(currentdir);
			fc.setFileFilter(filter);
			
			 int returnVal = fc.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			       temp = fc.getSelectedFile();
			       String filename = temp.getName();
				   String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
				   if(!filename.toLowerCase().contains("default") && !filename.toLowerCase().contains("dflt"))
				   {
					   JOptionPane.showMessageDialog(null, "This is NOT a Default File");
					   return null;
				   }
				   if(!extension.equals("mze") && !extension.equals("sav") 
						   && !extension.equals("jpg") && !extension.equals("png") && !extension.equals("jpeg"))
				   {
					   JOptionPane.showMessageDialog(null, "Invalid Format!");
					   System.exit(0);
				   }
			    }
			
			    if(returnVal == JFileChooser.CANCEL_OPTION){System.exit(0);}
			    return temp;
	 }
	 
	 
	 
	 
	 public String getName()
	 {
		 return name;
	 }
	 
	 public static void setName()
	 {
		 int dflt = JOptionPane.showConfirmDialog(null, "Load default from Library?\n" + "If 'No' will be "
		 		+ "prompted for File Name:  Note that File Must be In Library");
		 if(dflt == 0){name  = "default";}
		 if(dflt == 1)
		 {
			 name = JOptionPane.showInputDialog("File Name?");
		 }
	 }
	//-----------------------------------------------------
	 
	 
	 //Print Contents of Vectors
	 //for test
	 @SuppressWarnings("unqualified-field-access")
	public void print()
	 {
		 byte[] test = {bFile[0], bFile[1], bFile[2], bFile[3]};
	        StringBuilder sb = new StringBuilder();
	        for (byte b : test) {
	            sb.append(String.format("%02X", b));
	        }
	        System.out.print(sb);
	        System.out.println();
		 System.out.print(coordinates);
	      System.out.println();
	      System.out.print(lines);
	      System.out.println();
	      System.out.print(tiles);
	      System.out.println();
	      System.out.print(rotation);
	      System.out.println();
	      System.out.print(lines.size());
	      System.out.println();
	      System.out.print(coordinates.size());
	      System.out.println();
	      System.out.print(tiles.size());
	      System.out.println();
	      
	      System.out.println();
	 }
	//-----------------------------------------------------
	 
	 
	 
	 // Get the maze image
	 @SuppressWarnings("unqualified-field-access")
	public Image getImage()
	 {
		 return image;
	 }
	 
	public long getTime()
	{
		return time;
	}
	 
	 public Vector getRotation()
	 {
		 return rotation;
	 }
	 
	 public Vector getTileNumber()
	 {
		 return tiles;
	 }
	 
	 public boolean getPlayed()
	 {
		 return played;
	 }
	 
	 public String getFilePath()
	 {
		 return nameOfFile;
	 }
	 
	 public static boolean getIsImage()
	 {
		 return isImage;
	 }

}
