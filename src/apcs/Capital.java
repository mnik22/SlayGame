/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;


public class Capital extends Unit
{
    public Capital(Tile t)
    {
        try {
            super.setImage(new Image(new FileInputStream(new File("src/Capital.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        unitCost = 0;
        unitStrength = 1;
        tile = t;
    }
    
    public void move(boolean b)
    {
        //dose not need code as a capital does not get up and move.
    }
    
    public void setTile(Tile t)
    {
        //this is supposed to not have anything.
    }
}
