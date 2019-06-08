package apcs;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class Tile extends Polygon {
    
//    private ImageView imageView;
    
    private Tile topLeft;
    private Tile left;
    private Tile bottomLeft;
    private Tile topRight;
    private Tile right;
    private Tile bottomRight;
    
    private Tile[] adjacentTiles;
    
    
    private int horizontalPos;
    private int verticalPos;
    
    private int guiHorizontalPos;
    private int guiVerticalPos;
    
    private int protection;
    private Unit unit;
    
    private Player player;
    
    private boolean isCapital;
    
    public Tile(int x, int y)
    {
//        imageView = new ImageView();
//        imageView.setFitWidth(10);
//        imageView.setFitHeight(10);
        guiHorizontalPos = x;
        guiVerticalPos = y;
    }
    
    public void setPosition(int x, int y)
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
    
    public Tile[] getAdjacentTiles()
    {
        return adjacentTiles;
    }
    
    public void setPlayer(Player p)
    {
        player = p;
    }
    
    public void setAdjacent()
    {
        //pre: should be run when the board is initiated to set the tile adjacents.
        // as of this edition this should be run with a board that has a border of null spaces around it.
        
        
        int checkTopLeft = 0;
        int checkTopRight = 0;
        int checkBottomLeft = 0;
        int checkBottomRight = 0;
        
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
                topRight = null; checkTopRight = 1;
                bottomRight = null; checkBottomRight = 1;
            }
            else if(horizontalPos < Driver.map.length - 2)
            {
                right = Driver.map[horizontalPos + 2][verticalPos];
            }
        }
        else
        {
            if(horizontalPos == 2)
            {
                left = null;
                topLeft = null; checkTopLeft = 1;
                bottomLeft = null; checkBottomLeft = 1;
            }
            else
            {
                left = Driver.map[horizontalPos - 2][verticalPos];
            }
            if(horizontalPos == Driver.map.length - 2)
            {
                right = null;
                topRight = null; checkTopRight = 1;
                bottomRight = null; checkBottomRight = 1;
            }
            else if(horizontalPos < Driver.map.length - 2)
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
            if(horizontalPos < Driver.map.length - 2 && verticalPos < Driver.map[0].length - 1)
            {
                if(checkTopRight != 1 )
                    topRight = Driver.map[horizontalPos + 2][verticalPos + 1]; //some cases this messes up
            }
            if(horizontalPos > 1 && verticalPos < Driver.map[0].length - 1)
                if(checkTopLeft != 1)
                    topLeft = Driver.map[horizontalPos - 2][verticalPos + 1];
            
        }
        if(verticalPos == Driver.map[0].length - 2)
        {
            bottomRight = null;
            bottomLeft = null;
        }
        else
        {
            if(horizontalPos < Driver.map.length - 2 && verticalPos < 0)
                if(checkBottomRight != 1)
                    bottomRight = Driver.map[horizontalPos + 2][verticalPos - 1];
            if(horizontalPos < 1 && verticalPos < 0)
                if(checkBottomLeft != 1)
                    bottomLeft = Driver.map[horizontalPos - 2][verticalPos - 1];
        }
        
        adjacentTiles = new Tile[6];

        adjacentTiles[0] = topRight;
        adjacentTiles[1] = right;
        adjacentTiles[2] = bottomRight;
        adjacentTiles[3] = bottomLeft;
        adjacentTiles[4] = left;
        adjacentTiles[5] = topLeft;
        
//        for(int i = 0; i < adjacentTiles.length; i++)
//        {
//            System.out.println(adjacentTiles[i]);
//        }
        
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
    
    public boolean moveUnit(Unit u)
    {
    	Territory t = player.containsTile(this);
    	return(t.moveUnit(u, this));
    }
    
    //pre: a non null unit.
    //post: returns true if it could set the unit false otherwise.
	public boolean setUnit(Unit u) //this method is for creating new units or called from territories move unit method
    {
    	Tile old = u.getTile();
        boolean test = false;
        if(u != null) //this line is mostly useless now but i'm keeping it.
        {
            if(Driver.currentPlayer.equals(player))
            {
                if(hasUnit())
                {
                    if(u instanceof Peasant)
                    {
                        int curProtect = unit.getStrength();
                        switch(curProtect) //for upgrading with a peasant.
                        {
                            case 1:     newUnitTest(old, u);
                                        unit = new Spearman(this);
                                        setAdjacentProtection();
                                        super.setFill(new ImagePattern(unit.getImage()));
                                        test = true;
                                        break;
                            case 2:     newUnitTest(old, u);
                                        unit = new Knight(this);
                                        setAdjacentProtection();
                                        super.setFill(new ImagePattern(unit.getImage()));
                                        test = true;
                                        break;
                            case 3:		newUnitTest(old, u);
                                        unit = new Baron(this);
                                        setAdjacentProtection();
                                        super.setFill(new ImagePattern(unit.getImage()));
                                        test = true;
                                        break;
                            default:
                                    	//possibly put a noise or alert here
//                                		Alert a = new Alert(AlertType.WARNING);
//                                		a.setTitle("Warning");     
                                		return false;
                        }
	                }
	                else
	                {
	                	//possibly put a noise or sumting.
	                }
	            }
	            else
	            {
	            	newUnitTest(old, u);
	                unit = u;
	                u.setTile(this);
	                setAdjacentProtection();
	                super.setFill(new ImagePattern(unit.getImage()));
	                test = true;
	            }
            }
	        else
	        {
	        	//As of now the only time that this case (setting unit to enemy tile) is used is when called by territories move unit method.
	     		//This means that when you build a new unit (in this case a peasant) you have to put them on your territory before moving them to another players tile.
	     		if(old != null)
	     		{
	     			unit = u;
	     			u.setTile(this);
	                setAdjacentProtection();
	                super.setFill(new ImagePattern(unit.getImage()));
	                test = true;
	     		}
	     		else
	     		{
	     			//maybe make a noise or something
	     		}
	        }
        }     
	
	    return test;
}
    
    public Unit removeUnit()
    {
    	Unit u = unit;
    	super.setFill(super.getStroke());
    	unit = null;
    	removeProtection();
    	return u;
    }
    
//    public void unitFill(ImagePattern i)
//    {
//        
//        try
//        {
//            if(i.getImage().getHeight() == ((new Image(new FileInputStream("src/Pesant.png")))).getHeight() && i.getImage().getWidth() == (new ImagePattern(new Image(new FileInputStream("src/Pesant.png")))).getWidth())
//            {
//                if(setUnit(new Peasant(this)))
//                    super.setFill(i);
//            }
//            else if(i.getImage().getHeight() == ((new Image(new FileInputStream("src/Castle.png")))).getHeight() && i.getImage().getWidth() == (new ImagePattern(new Image(new FileInputStream("src/Castle.png")))).getWidth())
//            {
//                if(setUnit(new Castle(this)))
//                    super.setFill(i);
//            }
//            else
//            {
//                System.out.println("Something went wrong with the image pattern thing");
//            }
//                
//        } catch (FileNotFoundException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    
    public boolean hasUnit()
    {
        return unit != null;
    }
    
    public void setProtection(int p)
    {
        protection = p;
    }
	public void setAdjacentProtection(int p) //sets this and adjacent tiles protection to p based on each tiles previous protection. //this also might be overlooked by the other setAdjacentProtection method as it does the samething but better.
    {
		//might be useless
        if(protection < p)
            protection = p;
        for(int i = 0; i < adjacentTiles.length; i++)
        {
            if(player.equals(adjacentTiles[i].getPlayer()) && p > adjacentTiles[i].getProtection())
            {
                adjacentTiles[i].setProtection(p);
            }
        }
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
                if(adjacentTiles[i].hasUnit() && adjacentTiles[i].getPlayer().equals(player))
                {
                    temp.add(adjacentTiles[i]);
                }
            }
        }
        return temp;
    }
    
    public void setProtection() //looks at this tile and all adjacent tiles to see which one has the greatest strength then it sets this tiles protection to that.
    {
        ArrayList<Tile> temp = hasProtection();
        int strongest = 0;
        if(unit != null)
        {
            strongest = unit.getStrength();
        }
        for(int i = 0; i < temp.size(); i++)
        {
            if(temp.get(i).getProtection() > strongest)
            {
                strongest = temp.get(i).getProtection();
            }
        }
        setProtection(strongest);
    }
    
    public void setAdjacentProtection() //updates this and adjacent tiles protection based on nearby units
    {
    	setProtection();
        for(int i = 0; i < adjacentTiles.length; i++)
        {
            if(adjacentTiles[i].getPlayer().equals(player))
            {
                adjacentTiles[i].setProtection();
            }
        }
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
    
    public boolean chargeTerritory(Unit u)  //this method charges the territory that this tile is in for a unit bought
    {
    	//u has to be a peasant or a castle
    	Territory t = getTerritory();
    	if(u instanceof Peasant)
    	{
    		if(t.money > 5)
    		{
    			t.money -= 5;
//    			t.availbleUnits();
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    		
    	}
    	else if(u instanceof Castle)
    	{
    		if(t.money > 10)
    		{
    			t.money -= 10;
//   			t.availbleUnits();
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    	}
    	else
    	{
    		System.out.println("Something went wrong with charging for a unit, the unit was a  " + u );
    	}
    	return false;
    	
    }
    
    public boolean newUnitTest(Tile old, Unit u) //this method sees if this unit has just been bought and then charges it if it should.
    {
    	//post: calls chargeTerritory if this is a new unit.
    	if(old == null)
    	{
    		return chargeTerritory(u);
    	}
    	return false;
    }
    
    public Territory getTerritory()
    {
    	return player.containsTile(this);
    }
    
    //Image View Stuff   --   not in use anymore.
//    public void setImageCoords(double x,double y)
//    {
//        imageView.setX(x);
//        imageView.setY(y);
//    }
//    
//    public void setImage(Image i)
//    {
//        imageView.setImage(i);
//    }
//    
    
    
}
