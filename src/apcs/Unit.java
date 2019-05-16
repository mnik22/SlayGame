package apcs;

public abstract class Unit {

	protected int unitStrength;
	protected int unitCost;
	protected boolean canMove;
	protected Tile tile; //the tile that the unit is standing on.
	
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
