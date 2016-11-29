import java.awt.Button;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;


@SuppressWarnings("null")
public class Quit extends JButton implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Button bttn;
	
	
	@SuppressWarnings("unqualified-field-access")
	public Quit()
	{
		bttn = new Button();
		bttn.addActionListener(this);
		bttn.setEnabled(true);
	}
	
	
	@SuppressWarnings("nls")
	public  void actionPerformed(ActionEvent e)
	{
		//if game state has changed ask for save first
		if(MiniGame.getStateChange() && !MiniGame.getWon() && !Save.getHasBeenSaved())
		{
			int choice = JOptionPane.showConfirmDialog(null, 
					"Save First?", null, JOptionPane.YES_NO_OPTION);
			if(choice == 0)
			{
				Save save  = new Save();
				try {
					save.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
			else
			{
				System.exit(0);
			}
		}
		//if something happens which is none of the above rather than stall the game just exit without prejudice
		System.exit(0);
	}
}