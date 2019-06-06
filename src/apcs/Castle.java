/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.io.FileInputStream;

public class Castle extends Unit
{
    public Castle(Tile t, FileInputStream i)
    {
        super(i);
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
