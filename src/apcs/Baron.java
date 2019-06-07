package apcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Baron extends Unit {

	public Baron(Tile t)
	{
	    try {
	        super.setImage(new Image(new FileInputStream(new File("src/Baron.png"))));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		super.unitCost = 54;
		super.unitStrength = 3;
		tile = t;
	}
	
}
