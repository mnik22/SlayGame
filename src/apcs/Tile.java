package apcs;

public class Tile {
    
    private Tile topLeft;
    private Tile left;
    private Tile bottomLeft;
    private Tile topRight;
    private Tile right;
    private Tile bottomRight;
    
    private int horizontalPos;
    private int verticalPos;
    
    private int protection;
    private Unit unit;
    
    private Team team;
    
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
    
    public Team getTeam()
    {
        return team;
    }
    
    public void setTeam(Team t)
    {
        team = t;
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
    
    
    
}
