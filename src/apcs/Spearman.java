package apcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

public class Spearman extends Unit{

	public Spearman()
	{
	    try {
            super.setImage(new Image(new FileInputStream(new File("src/Spearman.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
		super.unitCost = 6;
		super.unitStrength = 1;
	}
	
}
