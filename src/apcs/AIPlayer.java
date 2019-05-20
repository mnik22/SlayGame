/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;
import java.util.ArrayList;


public class AIPlayer extends Player
{
 
    private ArrayList<Territory> territories;
    
    public AIPlayer(Color c)
    {
        super(c);
        territories = super.getTerritories();
    }

    public void playRound()
    {
        while(!endTurn())
        {
           for(Territory territory: territories)
           {
               ArrayList<Tile> tiles = territory.getTiles();
               for (Tile tile : tiles)
               {
                   if(tile.hasUnit())
                   {
                       int rand = (int) (Math.random() * tiles.size());
                       while(tiles.get(rand).getProtection() > 0)
                           rand = (int) (Math.random() * tiles.size());
                       territory.moveUnit(tile.getUnit(), tiles.get(rand));
                   }
               }
           }
        }
        
    }
    

}
