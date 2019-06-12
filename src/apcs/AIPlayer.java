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
            System.out.println("stuck here 6");
           for(int i = 0; i < territories.size(); i++)
           {
               ArrayList<Tile> tiles = territories.get(i).getTiles();
               for(int j = 0; j < tiles.size(); j++)
               {
                   ArrayList<Tile> adjacents = territories.get(j).adjacentEnemyTiles();
                   if(tiles.get(j).hasMoveableUnit())
                   {
                       int tileCount = 0;
                       int rand = (int) (Math.random() * adjacents.size());
                       while(tileCount < adjacents.size() && adjacents.get(rand).getProtection() > 0)
                       {
                           System.out.println("stuck here 7");
                           rand = (int) (Math.random() * adjacents.size());
                           tileCount++;
                       }
                       if(adjacents.get(rand) != null && adjacents.get(rand).getProtection() == 0) {
                           System.out.println("stuck here 8");
                           territories.get(i).moveUnit(tiles.get(j).getUnit(), adjacents.get(rand));
                       }
                       System.out.println("got here");
                   }
                   System.out.println("got here 2");
//                   try
//                   {
//                        Thread.sleep(10);
//                   } catch (InterruptedException e)
//                   {
//                        e.printStackTrace();
//                   }
               }
               System.out.println("got here 3");
               while(territories.get(i).canPurchaseUnits())
               {
                   System.out.println("stuck here 1");
                   if(territories.get(i).getMoney() > 20)
                   {
                       int randTile = (int) (Math.random() * tiles.size());
                       int count = 0;
                       while(tiles.get(randTile) != null && tiles.get(randTile).getProtection() > 0 && count < tiles.size())
                           randTile = (int) (Math.random() * tiles.size());System.out.println("stuck here 2");
                       if(tiles.get(randTile) != null && tiles.get(randTile).getProtection() == 0)
                       {
                           tiles.get(randTile).setUnit(new Castle(tiles.get(randTile)));
                           territories.get(i).buyCastle();
                       }
                   }
                   else
                   {
                       int randTile = (int) (Math.random() * tiles.size());
                       int count = 0;
                       while(tiles.get(randTile) != null && tiles.get(randTile).getProtection() > 0 && count < tiles.size())
                           randTile = (int) (Math.random() * tiles.size()); System.out.println("stuck here 3");
                       if(tiles.get(randTile) != null && tiles.get(randTile).getProtection() == 0)
                       {
                    	   tiles.get(randTile).setUnit(new Peasant(tiles.get(randTile)));
                    	   System.out.println("stuck here 4");
                    	   territories.get(i).buyPeasant();
                    	   System.out.println("stuck here 5");
                       }
                   }
               }
//               try
//               {
//                    Thread.sleep(10);
//               } catch (InterruptedException e)
//               {
//                    e.printStackTrace();
//               }
           }
        }
    }
}
