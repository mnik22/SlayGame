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
    
    public void setAdjacent(Tile[][] board)
    {
        
    }
    
    public boolean isCapital()
    {
        return isCapital;
    }
    
    public void setCapital(boolean b)
    {
        isCapital = b;
    }
    
    
    
}
