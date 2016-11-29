
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;




@SuppressWarnings("null")
public class Tile extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private JButton tile;
	private int identifier; //used to check if the game has been won
	private boolean location = false;  //used to check if tile belongs to Tile Grid or Game Board
	private int rotationIdentifier;
	
	public Tile() 
	{
		this.tile = new JButton();
		this.rotationIdentifier = 0;
	}
	
	@SuppressWarnings("unqualified-field-access")
	public void setIdentifier(int ident) {identifier = ident;}
	
	@SuppressWarnings("unqualified-field-access")
	public int getIdentifier() {return identifier;}
	
	public void increaseRotation()
	{
		if (rotationIdentifier == 3)
			rotationIdentifier = 0;
		else
			rotationIdentifier++;
	}
	
	public int getRotationIdentifier()
	{
		return rotationIdentifier;
	}
	
	public void setRotationIdentifier(int ident)
	{
		rotationIdentifier = ident;
	}
	
	
	
	//Set to true ONLY IF Tile is IN the GameBoard otherwise False
	public void setLoc(boolean bool){this.location = bool;}
	public boolean isOnBoard(){return this.location;}
	//-----------------------------------------------------

	
	
	@SuppressWarnings("unqualified-field-access")
	public void save(ObjectOutputStream file) throws IOException
	{
		file.writeObject(identifier);
		file.writeObject(this.getIcon());
		file.writeObject(this.isOnBoard());
		file.writeObject(this.getRotationIdentifier());
	}
	//-----------------------------------------------------

	
	
	public void load(ObjectInputStream file) throws ClassNotFoundException, IOException
 	{
		setIdentifier((int) file.readObject());
		this.setIcon((ImageIcon) file.readObject());
		this.setLoc((boolean) file.readObject());
		this.setRotationIdentifier((int) file.readObject());
 	}
	//-----------------------------------------------------



	

}
