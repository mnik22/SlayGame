/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;
import java.util.ArrayList;


public abstract class Player
{
    private Color color;
    private ArrayList<Territory> territories = new ArrayList<Territory>();
    
    public Player(Color c)
    {
        color = c;
    }

    public boolean endTurn() 
    {
        for(Territory t: territories)
        {
            if(t.canPurchaseUnits() || t.canMoveUnit())
                return false;
        }
        return true;
    }

    public void addTerritory(Territory t)
    {
        territories.add(t);
    }
    
    public void removeTerritory(Territory t) //up for change
    {
        for(int i = 0; i < territories.size(); i++)
        {
            if(territories.get(i).equals(t))
            {
                territories.remove(i);
            }
        }
    }
    
    public ArrayList<Territory> getTerritories()
    {
        return territories;
    }
    
    public int getNumTerritories()
    {
        return territories.size();
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public boolean equals(Player p)
    {
        return color.equals(p.getColor());
    }
    
    public int getNumTiles()
    {
        int num = 0;
        for (Territory t: territories)
        {
            num += t.getNumTiles();
        }
        return num;
    }
    
    public abstract void playRound();


}
