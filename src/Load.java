import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("null")
public class Load extends JButton implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static File file = null;
	File old = null;
	
	
	
	private static String lastLoad = null;
	//-----------------------------------------------------
	


	public static String getLastLoad()
	{
		return lastLoad;
	}
	//-----------------------------------------------------
	
	
	/**
	 * 
	 * New Load opens a file explorer and ask user to select a file to be loaded into the game
	 * accepted file types are JPG, PNG, MZE, SAV
	 * 
	 * @throws FileNotFoundException
	 */
	
	public void newload() throws FileNotFoundException
	{
		
		MiniGame.closeMenu(); //Close File Menu after selection
		
		
		//If the game's state has been changed ask for save before loading new file
		//----------------------------------------------------------------
		if(MiniGame.getStateChange() && !MiniGame.getWon() && !Save.getHasBeenSaved())
		{
			int choice = JOptionPane.showConfirmDialog(null, "Save First?", 
					null, JOptionPane.YES_NO_OPTION);
			if(choice == 0)
			{
				Save save = new Save();
				try {
					save.save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{}
		}
		//----------------------------------------------------------------
		
		
		
		//File chooser Set-Up
		//----------------------------------------------------------------
		
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SAV, JPG, PNG, MZE", "sav", "jpg",
				"png","jpeg", "mze","All");
		File currentdir = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(currentdir);
		fc.setFileFilter(filter);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		 int returnVal = fc.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) 
		    {
		       file = fc.getSelectedFile();
		    }
		
		    if(returnVal == JFileChooser.CANCEL_OPTION){return;}
		    
		String filename = file.getName();
		String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		//----------------------------------------------------------------
		
		
		//Based on extension use proper load implementation
		if(extension.equals("mze"))
		{
			byte [] bFile = new byte[(int)file.length()];
			FileInputStream finps = new FileInputStream(file);
			try
			{
				finps.read(bFile);
				finps.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			byte[] test = {bFile[0],bFile[1],bFile[2],bFile[3]};
			StringBuilder sb = new StringBuilder();
			for(byte b : test)
			{
				sb.append(String.format("%02X", b));
			}
			if(!sb.toString().equals("CAFEDEED") && !sb.toString().equals("CAFEBEEF"))
			{
				JOptionPane.showMessageDialog(null, "Invalid file type!");
				for(int i =0; i<8; i++)
				{
					MiniGame.getLeft().getLeft()[i].setIcon(null);
					MiniGame.getRight().getRight()[i].setIcon(null);
				}
				for(int i = 0; i <16; i++)
				{
					MiniGame.getBoard().getTiles()[i].setIcon(null);
				}
				return;
			}
			String location = file.getAbsolutePath();
			MiniGame.loadByteFile(location);
			MiniGame.setIsDefault(false);
			MiniGame.setState(false);
			
			//If loaded file is a default file clear game board on load
			if(filename.contains("default") || filename.contains("dflt"))
			{
				for(int i = 0; i <16; i++)
				{
					MiniGame.getBoard().getTiles()[i].setIcon(null);
				}
			}
			
		}
		//----------------------------------------------------------------
		
		
		else if(extension.equals("sav"))
		{
			try {
				MiniGame.setIsDefault(false);
				MiniGame.setState(false);
				MiniGame.getLeft().load(file, MiniGame.getRight(), MiniGame.getBoard());
			
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(null, "Invalid Format!");
				return;
			}
		}
		//----------------------------------------------------------------
		
		
		else if(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg"))
		{
			String location = file.getAbsolutePath();
			MiniGame.loadImage(location);
			MiniGame.setIsDefault(false);
			MiniGame.setState(false);
			{
				for(int i = 0; i <16; i++)
				{
					MiniGame.getBoard().getTiles()[i].setIcon(null);
				}
			}
		}
		//----------------------------------------------------------------
		
		
		else
		{
			JOptionPane.showMessageDialog(null, "Invalid File!");
			return;
		}
			
	}

	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{ 
		try {
				newload();
				
			} 
			catch (FileNotFoundException e1) 
			{
				JOptionPane.showMessageDialog(null, "File Not Found!");
				return;
			} 		
	}
	
	

	/**
	 * Assuming that the previously loaded file was good this takes that same file and loads it again
	 * Mainly used for "Reset" functionality.
	 * 
	 * Load method is different based on extension i.e. file type
	 * 
	 */
	public static void lastLoad()
	{
		MiniGame.closeMenu();
		File input  = new File(file.getAbsolutePath());
		String filename = input.getName();
		String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		//-----------------------------------------------------
		
		
		if(input == null){ return; }//Handle Cancel Button Press
		//-----------------------------------------------------
		
		if (extension.equals("mze"))
		{
			MiniGame.loadByteFile(input.getAbsolutePath());
			MiniGame.setIsDefault(false);
			MiniGame.setState(false);
		}
		else if(extension.equals("sav"))
		{
			try {
				MiniGame.setIsDefault(false);
				MiniGame.setState(false);
				MiniGame.getLeft().load(input, MiniGame.getRight(),
						MiniGame.getBoard());
				
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(null, "Invalid Format!");
				return;
			}
		}
		//-----------------------------------------------------
		
		else if(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg"))
		{
			String location = file.getAbsolutePath();
			MiniGame.loadImage(location);
			MiniGame.setIsDefault(false);
			MiniGame.setState(false);
			if(filename.contains("default") || filename.contains("dflt"))
			{
				for(int i = 0; i <16; i++)
				{
					MiniGame.getBoard().getTiles()[i].setIcon(null);
				}
			}
		}
		
		
	}
	
	public static String getPath(){ return file.getAbsolutePath(); }
	
	
}
