
package apcs;

import java.awt.Color;
import java.util.ArrayList;


public class Player
{
    private Color color;
    private ArrayList<Territory> territories = new ArrayList<Territory>();
    private boolean endTurn;
    
    public Player(Color c)
    {
        color = c;
        endTurn = false;
    }

    public boolean endTurn() 
    {
        if(endTurn)
            return endTurn;
        for (int i = 0; i < territories.size(); i++)
        {
            if(territories.get(i).canPurchaseUnits() || territories.get(i).canMoveUnit())
                return false;
            if(endTurn)
                return true;
        }
        return true;
//    	return false;
    }
    
    public void buttonEndTurn()
    {
        endTurn = true;
        endTurn();
    }
    
    public void endOfTurnSingle()
    {
        for(int i = 0; i < territories.size(); i++)
        {
        	territories.get(i).setMoney();
    		territories.get(i).resetMove();
        }
        
    }
    
    public void checkEndOfTurnAll()
    {
        for(int i = 0; i < territories.size(); i++)
        {
            if(territories.get(i).getMoney() < territories.get(i).getWages())
            {
                territories.get(i).destroyTheChild();
            }
        }
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
    
    public Territory containsTile(Tile t)
    {
    	for(int i = 0; i < territories.size(); i++)
    	{
    		if(territories.get(i).containsTile(t))
    		{
    			return territories.get(i);
    		}
    	}
    	return null;
    }
    
    public void playRound()
    {
        //this updates the endTurn variable and the canMove variable for every 
        //unit a player has must call super.playRound() at the beginning 
        //of every subClass of Player's playRound() method
        endTurn = false;
        for(Territory territory: territories)
        {
            ArrayList<Tile> tiles = territory.getTiles();
            int wages = 0;
            for (Tile tile : tiles)
            {
                if(tile.hasUnit() && (!(tile.getUnit() instanceof Capital )|| !(tile.getUnit() instanceof Castle)))
                {
                    tile.getUnit().move(true);
                }
                if (tile.hasUnit())
                {
                    wages += tile.getUnit().getCost();
                }
            }
            territory.setWages(wages);
        }
        endOfTurnSingle();
        for(int i = 0; i < Driver.players.length; i++) 
        {
            Driver.players[i].checkEndOfTurnAll();
        }
    }


}