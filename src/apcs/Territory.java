package apcs;

import java.util.ArrayList;

import javafx.scene.paint.Paint;

public class Territory {

    private Tile capital;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    public int money;
    private int wages;
    private Player player;
    
    
    public Territory(Player p)
    {
        player = p;
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
    
    public Tile getCapital()
    {
    	return capital;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public boolean equals(Territory t)
    {
    	ArrayList<Tile> temp = t.getTiles();
    	if(temp.size() == tiles.size())
    	{
    		for(int i = 0; i < temp.size(); i++)
    		{
    			if(!temp.get(i).equals(tiles.get(i)))
    			{
    				return false;
    			}
    		}
    	}
    	else
    	{
    		return false;
    	}
    	return true;
    }
    
    public void setBeginningCapital()
    {
    	if(tiles.size() > 1)
    	{
    		int rnd = (int)(Math.random()*tiles.size());
	        capital = tiles.get(rnd);                       
	        capital.setCapital(true);
	        capital.setUnit(new Capital(capital));
	        money = 5;
//	        System.out.println("This is capital: " + capital + " and Unit " + capital.getUnit());
    	}
    }
    
    public void setCapital()
    {
    	//pre: there cannot be a capital already in this territory.
    	if(tiles.size() > 1)
    	{
	        int rnd = (int)(Math.random()*tiles.size());
	        capital = tiles.get(rnd);                       
	        capital.setCapital(true);
	        capital.setUnit(new Capital(capital));
    	}
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
            Tile[] evenMoreTemp = tiles.get(i).getAdjacentTiles();
            for(int k = 0; k < evenMoreTemp.length; k++)
            {
            	if(evenMoreTemp[k] != null)
            	{
	                if(!evenMoreTemp[k].getPlayer().equals(player) && !temp.contains(evenMoreTemp[k]))
	                {
	                    temp.add(evenMoreTemp[k]);
	                }
            	}
            }
        }
        return temp;
    }
    
    public boolean moveUnit(Unit u, Tile t) //this method is for moving a unit already in play.
    {
    	System.out.println("Call complete");
    	boolean test = false;
        Tile old = u.getTile();
        System.out.println(u.canMove());
        if(u.canMove())
        {
            System.out.println(isAdjacent(t));
	        if(isAdjacent(t))
	        {
	        	System.out.println(!(t.getPlayer().equals(player)));
	            if(!t.getPlayer().equals(player))
	            { //capturing
	            	System.out.println(u.getStrength() > t.getProtection());
	                if(u.getStrength() > t.getProtection())
	                {
	                	Territory ter = t.getTerritory();
	                	checkMerge(t);
	                	ter.removeTile(t);
	                	if(t.isCapital())
	                	{
	                		t.setCapital(false);
	                		ter.setCapital();
	                	}
	                    t.setUnit(old.removeUnit());
	                    t.setPlayer(player);
	                    tiles.add(t);
	                    t.setAdjacentProtection();
	                    u.move(false);
	                    test = true;
	                }
	                else
	                {
	                    //maybe play a noise or something
	                	test = false;
	                }
	            }
	            else
	            {
	                if(!t.hasUnit())
	                {
	                    t.setUnit(old.removeUnit());
	                    t.setAdjacentProtection();
	                    test = true;
	                }
	                else if(u instanceof Peasant)
	                {
	                	if(t.setUnit(u))
	                	{
	                		old.removeUnit();
	                		t.setAdjacentProtection();
	                		u.move(false);
	                		test = true;
	                	}
	                }
	                else
	                {
	                	//maybe play noise or something
	                	test = false;
	                }
	            }
	        }
	        else
	        {
	        	// maybe play noise or something
	        	test = false;
	
	        }
        }
        return test;
    }
    
    public void resetMove()
    {
    	for(int i = 0; i < tiles.size(); i++)
    	{
    		if(tiles.get(i).getUnit() != null)
    		{
    			tiles.get(i).getUnit().move(true);
    		}
    	}
    }

    public boolean canMoveUnit()
    {
        for (int i = 0; i <tiles.size(); i++) {
        	if(tiles.get(i).getUnit() != null)
        	{
	            if(tiles.get(i).getUnit().canMove())
	            {
	                return true;
	            }
        	}
        }
        return false;
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
    
    public void checkMerge(Tile t)//this should be called after a tile is captured.
    {
    	for(int i = 0; i < t.getAdjacentTiles().length; i++)
    	{
    		if(t.getAdjacentTiles()[i].getPlayer().equals(player))
    		{
    			Territory ter = t.getAdjacentTiles()[i].getTerritory();
    			if(!ter.equals(this))
    			{
    				if(ter.getTiles().size() > this.getTiles().size())
    				{
    				    if(this.getCapital() != null)
    				    {
        					Tile cap = this.getCapital();
        					cap.removeUnit();
    				    }
    					ter.addTiles(this.getTiles());
    					player.removeTerritory(this);
    				}
    				else
    				{
    				    if(ter.getCapital() != null)
    				    {
        					Tile cap = ter.getCapital();
        					cap.removeUnit();
    				    }
	    				this.addTiles(ter.getTiles());
	    				player.removeTerritory(ter);
    				}
    			}
    		}
    	}
    }
    
//    public int costThisTurn()
//    {
//        int temp = 0;
//        for(int i = 0; i < tiles.size(); i++)
//        {
//            if(tiles.get(i).getUnit() != null)
//            {
//                temp += tiles.get(i).getUnit().getCost();
//            }
//        }
//        return temp;
//    }
    
    public void destroyTheChild()
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).getUnit() != null && (!(tiles.get(i).getUnit() instanceof Capital) || !(tiles.get(i).getUnit() instanceof Castle)))
            {
                tiles.get(i).removeUnit();
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
        }
        return temp;       
    }

    public boolean canPurchaseUnits()
    {
        return money >= 5;
    }
    
    
    public boolean containsTile(Tile t)
    {
    	for(int i = 0; i < tiles.size(); i++)
    	{
    		if(tiles.get(i).equals(t))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public int getNumTiles()
    {
        return tiles.size();
    }
    
    public ArrayList<Tile> getTiles()
    {
        return tiles;
    }
    
    public void buyPeasant()
    {
        money -= 5;
    }
    
    public void buyCastle()
    {
        money -= 10;
    }
    
    public int getMoney()
    {
        return money;
    }

    public void setWages(int wages) {
        this.wages = wages;
    }

    public int getWages() {
        return wages;
    }
    
    public int getIncome()
    {
    	return tiles.size();
    }
   
    public void setMoney()
    {
    	money += getIncome();
    }
}