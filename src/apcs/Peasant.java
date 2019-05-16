package apcs;

public class Peasant extends Unit{
	
	public Peasant(Tile t)
	{
		super.unitCost = 2;
		super.unitStrength = 0;
		canMove = true;
		super(t);
	}
	

}
