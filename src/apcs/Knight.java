package apcs;

import java.io.FileInputStream;

public class Knight extends Unit{

	public Knight(FileInputStream i)
	{
	    super(i);
		super.unitCost = 18;
		super.unitStrength = 2;
	}
	
}
