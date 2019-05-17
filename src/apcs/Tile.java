package apcs;

import java.util.ArrayList;

import javafx.scene.shape.Polygon;

public class Tile extends Polygon {
    
    private Tile topLeft;
    private Tile left;
    private Tile bottomLeft;
    private Tile topRight;
    private Tile right;
    private Tile bottomRight;
    private Tile[] adjacentTiles;
    
    private int horizontalPos;
    private int verticalPos;
    
    private int protection;
    private Unit unit;
    
    private Player player;
    
    private boolean isCapital;
    
    public Tile(int x, int y)
    {
        horizontalPos = x;
        verticalPos = y;
    }
    
    
    public boolean isAdjacent(Tile t)
    {
        if(t.equals(topLeft) || t.equals(left) || t.equals(bottomLeft) || t.equals(bottomRight) || t.equals(right) || t.equals(topRight))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public void setPlayer(Player p)
    {
        player = p;
    }
    
    public void setAdjacent()
    {
        //pre: should be run when the board is initiated to set the tile adjacents.
        // as of this edition this should be run with a board that has a border of null spaces around it.
        if(verticalPos%2 == 1)
        {
            if(horizontalPos == 1)
            {
                left = null;
                topLeft = null;
                bottomLeft = null;
            }
            else
            {
                left = Driver.map[horizontalPos - 2][verticalPos];
            }
            if(horizontalPos == Driver.map.length - 3)
            {
                right = null;
                topRight = null;
                bottomRight = null;
            }
            else
            {
                right = Driver.map[horizontalPos + 2][verticalPos];
            }
        }
        else
        {
            if(horizontalPos == 2)
            {
                left = null;
                topLeft = null;
                bottomLeft = null;
            }
            else
            {
                left = Driver.map[horizontalPos - 2][verticalPos];
            }
            if(horizontalPos == Driver.map.length - 2)
            {
                right = null;
                topRight = null;
                bottomRight = null;
            }
            else
            {
                right = Driver.map[horizontalPos + 2][verticalPos];
            }
        }
        if(verticalPos == 1)
        {
            topRight = null;
            topLeft = null;
        }
        else
        {
            topRight = Driver.map[horizontalPos + 2][verticalPos + 1];
            topLeft = Driver.map[horizontalPos - 2][verticalPos + 1];
        }
        if(verticalPos == Driver.map[0].length - 2)
        {
            bottomRight = null;
            bottomLeft = null;
        }
        else
        {
            bottomRight = Driver.map[horizontalPos + 2][verticalPos - 1];
            bottomLeft = Driver.map[horizontalPos - 2][verticalPos - 1];
        }
        adjacentTiles[0] = topRight;
        adjacentTiles[1] = right;
        adjacentTiles[2] = bottomRight;
        adjacentTiles[3] = bottomLeft;
        adjacentTiles[4] = left;
        adjacentTiles[5] = topLeft;
    }
    
    public boolean isCapital()
    {
        return isCapital;
    }
    
    public void setCapital(boolean b)
    {
        isCapital = b;
    }
    
    public boolean equals(Tile t)
    {
        return(horizontalPos == t.getHor() && verticalPos == t.getVert());
    }
    
    public int getHor()
    {
        return horizontalPos;
    }
    
    public int getVert()
    {
        return verticalPos;
    }
    
    public Unit getUnit()
    {
        return unit;
    }
    
    public void setUnit(Unit u) //this method assumes that you can pass a unit into this tile.
    {
        unit = u;
        
        if(unit != null)
        {
            for(int i = 0; i < adjacentTiles.length; i++)
            {
                //if(adjacentTiles[i]) //this line as well as method is not done.
                {
                    
                }
            }
        }
        else
        {   //for removing a person from a tile
            removeProtection();
            for(int i = 0; i < adjacentTiles.length; i++)
            {
                adjacentTiles[i].removeProtection();
            }
            
        }
    }
    
    public boolean hasUnit()
    {
        return unit != null;
    }
    
    public void setProtection(int p)
    {
        protection = p;
    }
    public int getProtection()
    {
        return protection;
    }
    
    public ArrayList<Tile> hasProtection()
    {
        ArrayList<Tile> temp = new ArrayList<Tile>();
        if(protection > 0)
        {
            for(int i = 0; i < adjacentTiles.length; i++)
            {
                if(adjacentTiles[i].hasUnit())
                {
                    temp.add(adjacentTiles[i]);
                }
            }
        }
        return temp;
    }
    
    public void removeProtection() //you should remove the Unit on this tile before calling this method
    {
        for(int i = 0; i < adjacentTiles.length; i++)
        {
            ArrayList<Tile> temp = adjacentTiles[i].hasProtection();
            if(temp.size() > 0)
            {
                Unit strongest = temp.get(0).getUnit();
                for(int k = 1; k < temp.size(); k++)
                {
                    if(temp.get(k).getUnit().getStrength() > strongest.getStrength())
                    {
                        strongest = temp.get(k).getUnit();
                    }
                }
                adjacentTiles[i].setProtection(strongest.getStrength());
            }
            else
            {
                adjacentTiles[i].setProtection(0);
            }
        }
    }
    
    
    
    
    
    
}
