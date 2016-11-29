import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JPanel;


@SuppressWarnings("null")
class GameBoard extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JPanel board;
	private static Tile tiles[];
	private GridBagLayout layout = new GridBagLayout();
	
	
	//****************************************************
	
	
	@SuppressWarnings("unqualified-field-access")
	public GameBoard()
	{
		board = new JPanel();
		super.paint(getGraphics());
		board.setOpaque(false);
		fillBoard();
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	@SuppressWarnings("unqualified-field-access")
	public void fillBoard()
    {
		tiles = new Tile[16];
		
		
    	for(int i = 0; i < tiles.length; i++)
    	{
    		tiles[i] = new Tile();
    		tiles[i].setPreferredSize(new Dimension(85,85));
    		
    	}
    }
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	@SuppressWarnings("unqualified-field-access")
	public Tile[] getTiles()
	{
		return tiles;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
	@SuppressWarnings({ "unqualified-field-access", "nls" })
	public boolean gameWon()  //Check for tile proper location
	{
		boolean won = true;
		for(int i = 0; i < tiles.length; i++)
		{
			System.out.print(tiles[i].getRotationIdentifier());
			if (tiles[i].getIdentifier() != i + 1 && 
					tiles[i].getRotationIdentifier() != 0)
				won = false;
		}
		return won;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	@SuppressWarnings("unqualified-field-access")
	public void print() //For Test Purposes
	{
		for(Tile tile : tiles)
		{
			System.out.print(tile.getIdentifier());
			System.out.println();
		}
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
	
}
