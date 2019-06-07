package apcs;

import java.util.ArrayList;

import javafx.scene.paint.Paint;

public class Territory {

    private Tile capital;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private int money;
    private boolean canPurchaseUnits;
    private boolean canMoveUnit;
    private Player player;
    
    
    public Territory(Player p, ArrayList<Tile> startingTiles)
    {
        player = p;
        tiles.addAll(startingTiles); //might need a collection instead of an arraylist as the parameter.
        int rnd = (int)(Math.random()*tiles.size());
        capital = tiles.get(rnd);                       //these three lines randomly sets the capital when the territory is initiated
        capital.setCapital(true);
        capital.setUnit(new Capital(capital));
    }
    
    public void addTile(Tile t)
    {
        tiles.add(t);
    }
    
    public void addTiles(ArrayList<Tile> newTiles)
    {
        tiles.addAll(newTiles);
    }
    
    public Tile removeTile(Tile t)
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).equals(t))
            {
                return tiles.remove(i);
            }
        }
        return null;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public boolean isAdjacent(Tile t)
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).isAdjacent(t))
            {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Tile> adjacentEnemyTiles()
    {
        ArrayList<Tile> temp = new ArrayList<Tile>();
        
        
        for(int i = 0; i < tiles.size(); i++)
        {
            Tile[] evenMoreTemp = temp.get(i).getAdjacentTiles();
            for(int k = 0; k < evenMoreTemp.length; k++)
            {
                if(!evenMoreTemp[i].getPlayer().equals(player) && !temp.contains(evenMoreTemp[i]))
                {
                    temp.add(evenMoreTemp[i]);
                }
            }
        }
        return temp;
    }
    
    public void moveUnit(Unit u, Tile t) //this method is for moving a unit already in play.
    {
        Tile old = u.getTile();
        if(isAdjacent(t))
        {
            if(!t.getPlayer().equals(player))
            {
                if(u.getStrength() > t.getProtection())
                {
                    t.setUnit(old.removeUnit());
                    t.setPlayer(player);
                    tiles.add(t);
                    t.setAdjacentProtection();
                    u.move(false);
                }
                else
                {
                    //maybe play a noise or something
                }
            }
            else
            {
                if(!t.hasUnit())
                {
                    t.setUnit(old.removeUnit());
                    t.setAdjacentProtection();
                }
                else if(u instanceof Peasant)
                {
                	if(t.setUnit(u))
                	{
                		old.removeUnit();
                		t.setAdjacentProtection();
                		u.move(false);
                	}
                }
                else
                {
                	//maybe play noise or something
                }
            }
        }
        else
        {
        	// maybe play noise or something
        }
    }
    
    public void resetMove()
    {
    	for(int i = 0; i < tiles.size(); i++)
    	{
    		if(tiles.get(i).hasUnit())
    		{
    			tiles.get(i).getUnit().move(true);
    		}
    	}
    }

    public void updateCanMoveUnit()
    {
        canMoveUnit = false;
        for (int i = 0; i <tiles.size(); i++) {
            if(tiles.get(i).getUnit().canMove())
            {
                canMoveUnit = true;
                i = tiles.size();
            }
        }
    }

    public boolean canMoveUnit()
    {
        return canMoveUnit;
    }
    
    public void updateTiles()
    {
    	for(int i = tiles.size()-1; i >= 0; i--)
    	{
    		if(tiles.get(i).getPlayer() != player)
    		{
    			tiles.remove(i);
    		}
    	}
    }
    
    //ECONOMY METHODS
    
    public boolean maintenance()
    {
        int cost = 0;
        for(int i = 0; i < tiles.size(); i++)
        {
            money++;
            if(tiles.get(i).getUnit() != null)
            {
                cost += tiles.get(i).getUnit().getCost();
            }
        }
        money -= cost;
        if(money < 0)
        {
            destroyTheChild();
            money = 0;
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public int costThisTurn()
    {
        int temp = 0;
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).getUnit() != null)
            {
                temp += tiles.get(i).getUnit().getCost();
            }
        }
        return temp;
    }
    
    public void destroyTheChild()
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).getUnit() != null && (!(tiles.get(i).getUnit() instanceof Capital) || !(tiles.get(i).getUnit() instanceof Castle)))
            {
                tiles.get(i).setUnit(null);
            }
        }
    }
    
    public int[] availbleUnits()
    {
        int[] temp = new int[2]; //the first index is how many peasants you can buy and the second is how many castles you can buy.
        int cost = 0;
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).getUnit() != null)
            {
                cost += tiles.get(i).getUnit().getCost();
            }
        }
        if(money - cost > 0)
        {
            temp[0] = (money-cost)/5;
            temp[1] = (money-cost)/10;
            canPurchaseUnits = true;
        }
        else
        {
            canPurchaseUnits = false;
        }
        
        return temp;       
    }

    public boolean canPurchaseUnits()
    {
        return canPurchaseUnits;
    }
    
    public void buyUnit( Tile t, Unit u)
    {
        if(u instanceof Peasant)
        {
            if(money >= 5)
            {
                money -= 5; //peasant costs 5
                t.setUnit(u);
            }
            else
            {
                //play noise
            }
        }
        else if(u instanceof Castle)
        {
            if(money >= 10)
            {
                money -= 10; //castle costs 10
                t.setUnit(u);
            }
            else
            {
                //play noise
            }
        }
        availbleUnits();
    }
    
    public int getNumTiles()
    {
        return tiles.size();
    }
    
    public ArrayList<Tile> getTiles()
    {
        return tiles;
    }
    
    public int getMoney()
    {
        return money;
    }
    
   
}