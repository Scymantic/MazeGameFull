import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

@SuppressWarnings("null")
public class Save extends JButton implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean hasBeenSaved = false;

	@Override
	public void actionPerformed(ActionEvent e) 
	{ 
		try 
		{
			save();
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	//-----------------------------------------------------

	
	
	
	@SuppressWarnings("nls")
	public void save() throws IOException
	{
		MiniGame.closeMenu();
		File file = null;
		String path = null;
		JFileChooser fc = new JFileChooser();
		File currentdir = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(currentdir);
		int returnval = fc.showSaveDialog(null);
		if(returnval == fc.APPROVE_OPTION)
		{
				path = fc.getSelectedFile().getAbsolutePath();
		}
		
		
		//-----------------------------------------------------

		
		if(path != null){
			file = new File(path + ".sav");
		}
		int dflt = 1;
		
		if(file == null){return;}
		
		while (file.exists() && dflt == 1){
			dflt = JOptionPane.showOptionDialog(null, file + " already exists. Overwrite?", 
					"File already exists", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, "yes");
			if (dflt == 1){
				String name = JOptionPane.showInputDialog("Save As?");
				path = path.substring(0,path.lastIndexOf(File.separator)+1);
				System.out.print(path);
				if(path != null && name != null)
					file = new File(path + name + ".sav");
				else {dflt = 0; break;};
			}
		}
		//-----------------------------------------------------

		
		
		@SuppressWarnings("resource")
		FileOutputStream saveFile = new FileOutputStream(file);
		@SuppressWarnings("resource")
		ObjectOutputStream save = new ObjectOutputStream(saveFile);
		MiniGame.getLeft().save(save, MiniGame.getRight(), MiniGame.getBoard());
		save.close();
		hasBeenSaved = true;
		
	}
	
	public static boolean getHasBeenSaved(){return hasBeenSaved;}

	
}