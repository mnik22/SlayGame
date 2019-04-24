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
    
    private Team team;
    
    public Tile(int x, int y)
    {
        horizontalPos = x;
        verticalPos = y;
    }
    
    
    public boolean isAdjacent(Tile t)
    {
        return true;
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
    
    
}
