package apcs;

import java.util.ArrayList;

public class Territory {

    private Tile capital;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private int money;
    private boolean canPurchaseUnits;
    private boolean canMoveUnit;
    private Player player;
    
    
    public Territory(Player p)
    {
        player = p;
        int rnd = (int)(Math.random()*tiles.size());
        capital = tiles.get(rnd);                       //these three lines randomly sets the capital when the territory is initiated
        capital.setCapital(true);
        capital.setUnit(new Capital(capital));
    }
    
    public void addTile(Tile t)
    {
        tiles.add(t);
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
    
    public void moveUnit(Unit u, Tile t)
    {
        if(isAdjacent(t))
        {
            if(!t.getPlayer().equals(player))
            {
                if(u.getStrength() > t.getProtection())
                {
                    Tile old = u.getTile();
                    old.setUnit(null);
                    old.setProtection();
                    t.setUnit(u);
                    t.setPlayer(player);
                    tiles.add(t);
                    t.setProtection();
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
                    u.getTile().setUnit(null);
                    t.setUnit(u);
                    t.setProtection();
                    t.setAdjacentProtection();
                }
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
    
    //economy methods
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
    
    public void destroyTheChild()
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).getUnit() != null)
            {
                tiles.get(i).setUnit(null);
            }
        }
    }
    
    public int[] availbleUnits()
    {
        int[] temp = new int[2]; //the first slot is how many peasants you can buy and the second is how many castles you can buy.
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
            money -= 5; //peasant costs 5
        }
        else if(u instanceof Castle)
        {
            money -= 10; //castle costs 10
        }
        t.setUnit(u);
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
    
   
}