package apcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Knight extends Unit{

	public Knight()
	{
	    try {
	        super.setImage(new Image(new FileInputStream(new File("src/Knight.png"))));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		super.unitCost = 18;
		super.unitStrength = 2;
	}
	
}
