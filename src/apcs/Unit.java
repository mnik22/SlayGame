package apcs;

import javafx.scene.image.Image;

public abstract class Unit {

    protected Image image;
    
    protected int unitStrength;
	protected int unitCost;
	protected boolean canMove;
	protected Tile tile; //the tile that the unit is standing on.
	
	void setImage(Image image) {
	    this.image = image;
	}
	
	public Image getImage() {
	    return image;
	}
	
	public int getCost()
	{
		return unitCost;
	}
	
	public int getStrength()
	{
		return unitStrength;
	}
	
	public boolean canMove()
	{
	    return canMove;
	}
	
	public void move(boolean b)
	{
	    canMove = b;
	}
	
	public void setTile(Tile t)
	{
	    tile = t;
	}
	
	public Tile getTile()
	{
	    return tile;
	}
}
