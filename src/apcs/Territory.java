package apcs;

import java.util.ArrayList;

public class Territory {

    private Tile capital;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    
    public Territory()
    {
        //radomizes the capital
    }
    
    public void addTile(Tile t)
    {
        tiles.add(t);
    }
    
    public Tile removeTile(Tile t)
    {
        return null;
    }
   
}
