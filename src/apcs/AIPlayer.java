/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        super.playRound();
        while(!endTurn())
        {
           for(Territory territory: territories)
           {
               ArrayList<Tile> tiles = territory.getTiles();
               for(int i = 0; i < tiles.size(); i++)
               {
                   ArrayList<Tile> adjacents = territory.adjacentEnemyTiles();
                   if(tiles.get(i).hasMoveableUnit())
                   {
                       int tileCount = 0;
                       int rand = (int) (Math.random() * adjacents.size());
                       while(tileCount < adjacents.size() && adjacents.get(rand).getProtection() > 0)
                       {
                           rand = (int) (Math.random() * adjacents.size());
                           tileCount++;
                       }
                       if(adjacents.get(rand).getProtection() == 0)
                           territory.moveUnit(tiles.get(i).getUnit(), adjacents.get(rand));
                   }
               }
               while(territory.canPurchaseUnits())
               {
                   ArrayList<Tile> adjacents = territory.adjacentEnemyTiles();
                   if(territory.getMoney() > 20)
                   {
                       int randTile = (int) (Math.random() * tiles.size());
                       while(tiles.get(randTile).getProtection() > 0)
                           randTile = (int) (Math.random() * tiles.size());
                       if(adjacents.get(randTile).getProtection() == 0)
                       {
                           adjacents.get(randTile).setUnit(new Castle(null));
                           territory.buyCastle();
                       }
                   }
                   else
                   {
                       int randAdjacent = (int) (Math.random() * adjacents.size());
                       while(adjacents.get(randAdjacent).getProtection() > 0)
                           randAdjacent = (int) (Math.random() * adjacents.size());
                       if(adjacents.get(randAdjacent).getProtection() == 0)
                       {
                    	   adjacents.get(randAdjacent).setUnit(new Peasant(null));
                    	   territory.buyPeasant();
                       }
                   }
                   
               }
           }
        }
    }
    

}
