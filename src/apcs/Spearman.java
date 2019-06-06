package apcs;

import java.io.FileInputStream;

public class Spearman extends Unit{

	public Spearman(FileInputStream i)
	{
	    super(i);
		super.unitCost = 6;
		super.unitStrength = 1;
	}
	
}
