package apcs;

import java.io.FileInputStream;

public class Baron extends Unit{

	public Baron(FileInputStream i)
	{
	    super(i);
		super.unitCost = 54;
		super.unitStrength = 3;
	}
}
