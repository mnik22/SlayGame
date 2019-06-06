package apcs;

import java.io.FileInputStream;

public class Peasant extends Unit{
	
	public Peasant(Tile t, FileInputStream i)
	{
	    super(i);
	    tile = t;
		super.unitCost = 2;
		super.unitStrength = 0;
		canMove = true;
	}
	

}
