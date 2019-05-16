/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;


public class Capital extends Unit
{
    public Capital(Tile t)
    {
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
