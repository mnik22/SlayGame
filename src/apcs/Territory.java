package apcs;

import java.util.ArrayList;

public class Territory {

    private Tile capital;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private int money;
    
    
    public Territory()
    {
        int rnd = (int)(Math.random()*tiles.size());
        capital = tiles.get(rnd);                       //these three lines randomly sets the capital when the territory is initiated
        capital.setCapital(true);
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
        }
        
        return temp;       
    }
    
    public void buyUnit(Unit u, Tile t)
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
    
    
   
}
