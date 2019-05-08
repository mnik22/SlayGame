package apcs;

public abstract class Unit {

	protected int unitStrength;
	protected int unitCost;
	protected boolean canMove;
	
	
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
}
