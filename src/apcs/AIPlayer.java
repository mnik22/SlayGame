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
//        while(!endTurn())
//        {
        for(int i = 0; i < territories.size(); i++)
        {
            ArrayList<Tile> tiles = territories.get(i).getTiles();
            for(int j = 0; j < tiles.size(); j++)
            {
                if(territories.get(i).canPurchaseUnits() && !tiles.get(j).hasUnit() && tiles.size() > 2)
                {
                    if(territories.get(i).getMoney() > 20)
                    {
                        int index = 0;
                        while(index < tiles.size() && !(tiles.get(j).setUnit(new Castle(tiles.get(j)))))
                        {
                            index++;
                        }
                        territories.get(i).buyCastle();
                    }
                    else
                    {
                        int index = 0;
                        while(index < tiles.size() && !(tiles.get(j).setUnit(new Peasant(tiles.get(j)))))
                        {
                            index++;
                        }
                        territories.get(i).buyPeasant();
                    }
                }
            }
        } 
        for(int i = 0; i < territories.size(); i++)
        {
           ArrayList<Tile> tiles = territories.get(i).getTiles();
           for(int j = 0; j < tiles.size(); j++)
           {
               ArrayList<Tile> adjacents = territories.get(i).adjacentEnemyTiles();
               if(tiles.get(j).hasMoveableUnit() && tiles.get(j).getUnit().canMove && tiles.size() > 2)
               {
                   int index = 0;
                   while(index < adjacents.size() && !(territories.get(i).moveUnit(tiles.get(j).getUnit(), adjacents.get(index))))
                   {
                       index++;
                   }
               }
           }
       }
           
               
               
               
               
               
               
//               System.out.println("got here 3");
//               System.out.println(territories.get(i).canPurchaseUnits());
//               int maxNumTries = 0;
//               while(territories.get(i).canPurchaseUnits())
//               {
//                   System.out.println("stuck here 1");
//                   System.out.println("territories.get(i).getMoney() > 20 " + (territories.get(i).getMoney() > 20));
//                   if(territories.get(i).getMoney() > 20)
//                   {
//                       int randTile = (int) (Math.random() * tiles.size());
//                       int count = 0;
//                       while(tiles.get(randTile) != null && tiles.get(randTile).hasUnit() && count < tiles.size())
//                           randTile = (int) (Math.random() * tiles.size());System.out.println("stuck here 2");
//                       if(tiles.get(randTile) != null && !tiles.get(randTile).hasUnit())
//                       {
//                           tiles.get(randTile).setUnit(new Castle(tiles.get(randTile)));
//                           territories.get(i).buyCastle();
//                       }
//                   }
//                   else
//                   {
//                       int randTile = (int) (Math.random() * tiles.size());
//                       int count = 0;
//                       System.out.println(tiles.get(randTile));
//                       System.out.println(tiles.get(randTile).hasUnit());
//                       System.out.println(count < tiles.size());
//                       
//                       while(tiles.get(randTile) != null && tiles.get(randTile).hasUnit() && count < tiles.size()) {
//                           randTile = (int) (Math.random() * tiles.size()); 
//                           count++; 
//                           System.out.println("stuck here 3"); 
//                       }
//                       System.out.println("testing");
//                       System.out.println((tiles.get(randTile) != null) + " " + (!tiles.get(randTile).hasUnit()));
//                       if(tiles.get(randTile) != null && !tiles.get(randTile).hasUnit())
//                       {
//                           System.out.println("stuck here 4");
//                    	   tiles.get(randTile).setUnit(new Peasant(tiles.get(randTile)));
//                    	   System.out.println("stuck here 5");
//                    	   territories.get(i).buyPeasant();
//                       }
//                   }
//               }
//               try
//               {
//                    Thread.sleep(10);
//               } catch (InterruptedException e)
//               {
//                    e.printStackTrace();
//               }
//        }
    }
}
