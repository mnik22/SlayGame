package apcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Peasant extends Unit{
	
	public Peasant(Tile t)
	{
	    try {
	        super.setImage(new Image(new FileInputStream(new File("src/Pesant.png"))));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    tile = t;
		super.unitCost = 2;
		super.unitStrength = 1;
		canMove = true;
	}
	

}
