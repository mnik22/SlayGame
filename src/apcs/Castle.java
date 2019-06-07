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

public class Castle extends Unit
{
    public Castle(Tile t)
    {
        try {
            super.setImage(new Image(new FileInputStream(new File("src/Castle.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tile = t;
        unitCost = 0;
        unitStrength = 2;
        canMove = false;
    }
    
    public void move(boolean b)
    {
        //dose not need code as a castle does not get up and move.
    }
    
    public void setTile(Tile t)
    {
        //this is supposed to not have anything.
    }
}
