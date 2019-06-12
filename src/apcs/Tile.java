package apcs;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class Tile extends Polygon {
    
//    private ImageView imageView;
    
    private Tile topLeft;
    private Tile top;
    private Tile topRight;
    private Tile bottomRight;
    private Tile bottom;
    private Tile bottomLeft;
    
    private Tile[] adjacentTiles;
    
    
    private int horizontalPos;
    private int verticalPos;
    
    private int guiHorizontalPos;
    private int guiVerticalPos;
    
    private int protection;
    private Unit unit;
    
    private Player player;
    
    private boolean isCapital;
    private boolean hasTerritory;
    
    public Tile(int y, int x)
    {
//        imageView = new ImageView();
//        imageView.setFitWidth(10);
//        imageView.setFitHeight(10);
        guiHorizontalPos = x;
        guiVerticalPos = y;
    }
    
    public void setGUIPosition(int x, int y)
    {
    	guiHorizontalPos = x;
    	guiVerticalPos = y;
    }
    
    public void setPosition(int y, int x)
    {
        horizontalPos = x;
        verticalPos = y;
    }
    
    public boolean isAdjacent(Tile t)
    {
        if(t.equals(topLeft) || t.equals(top) || t.equals(bottomLeft) || t.equals(bottomRight) || t.equals(bottom) || t.equals(topRight))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean hasTerritory()
    {
    	return hasTerritory;
    }
    
    public void setHasTerritory()
    {
    	hasTerritory = true;
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
    	
        adjacentTiles = new Tile[6];
        
        
        //Driver.map[VERT][HOR]
        
        if(verticalPos < Driver.map.length && horizontalPos > 1) //topleft
        {
        	topLeft = Driver.map[verticalPos + 1][horizontalPos - 1];
        }
        else
        {
        	topLeft = null;
        }
        
        if(verticalPos < Driver.map.length - 2) //top
        {
        	top = Driver.map[verticalPos + 2][horizontalPos];
        }
        else
        {
        	top = null;
        }
        
        if(verticalPos < Driver.map.length && horizontalPos < Driver.map[0].length) //topright
        {
        	topRight = Driver.map[verticalPos + 1][horizontalPos + 1];
        }
        else
        {
        	topRight = null;
        }
        
        if(verticalPos > 1 && horizontalPos < Driver.map[0].length) //bottomright
        {
        	bottomRight = Driver.map[verticalPos - 1][horizontalPos + 1];
        }
        else
        {
        	bottomRight = null;
        }
        
        if(verticalPos > 2) //bottom
        {
        	bottom = Driver.map[verticalPos - 2][horizontalPos];
        }
        else
        {
        	bottom = null;
        }
        
        if(verticalPos > 1 && horizontalPos > 1) //botttomLeft
        {
        	bottomLeft = Driver.map[verticalPos - 1][horizontalPos - 1];
        }
        else
        {
        	bottomLeft = null;
        }
    	
        
        adjacentTiles[0] = topLeft;
        adjacentTiles[1] = top;
        adjacentTiles[2] = topRight;
        adjacentTiles[3] = bottomRight;
        adjacentTiles[4] = bottom;
        adjacentTiles[5] = bottomLeft;
        
        
//        System.out.print("main tile " + getHor() + ", " + getVert() +  " ");
//        for(int i = 0; i < adjacentTiles.length; i++)
//        {
//        	if( adjacentTiles[i] != null)
//        		System.out.print(adjacentTiles[i].getHor() + ", " + adjacentTiles[i].getVert() + " | ");
//        	else
//        		System.out.print("null ");
//        }
//        System.out.println();
        
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
    	if(t != null)
    	{
    		return(horizontalPos == t.getHor() && verticalPos == t.getVert());
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public int getHor()
    {
        return horizontalPos;
    }
    
    public int getVert()
    {
        return verticalPos;
    }
    
    public int getX()
    {
    	return guiHorizontalPos;
    }
    
    public int getY()
    {
    	return guiVerticalPos;
    }
    
    public Unit getUnit()
    {
        return unit;
    }
    
    public boolean moveUnit(Unit u)
    {
    	System.out.println("Calling move unit: " + u + " " + toString());
    	Territory t = u.getTile().getTerritory();
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
//        	System.out.println(Driver.currentPlayer.equals(player));
        	if(!(u instanceof Capital))
        	{
        		if(u.canMove())
        		{
		            if(Driver.currentPlayer.equals(player))
		            {
		                if(hasUnit())
		                {
		                    if(u instanceof Peasant)
		                    {
		                    	if(!(unit instanceof Capital) && !(unit instanceof Castle))
		                    	{
			                        int curProtect = unit.getStrength();
			                        switch(curProtect) //for upgrading with a peasant.
			                        {
			                            case 1:     newUnitTest(old, u);
			                                        unit = new Spearman(this);
			                                        unit.move(false);
			                                        setAdjacentProtection();
			                                        test = true;
			                                        break;
			                            case 2:     newUnitTest(old, u);
			                                        unit = new Knight(this);
			                                        unit.move(false);;
			                                        setAdjacentProtection();
			                                        test = true;
			                                        break;
			                            case 3:		newUnitTest(old, u);
			                                        unit = new Baron(this);
			                                        unit.move(false);
			                                        setAdjacentProtection();
			                                        test = true;
			                                        break;
			                            default:
			                                    	//possibly put a noise or alert here
			//                                		Alert a = new Alert(AlertType.WARNING);
			//                                		a.setTitle("Warning");     
			                                		test = false;
			                        }
		                    	}
		                    	else
		                    	{
		                    		//play a noise or something
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
			                test = true;
			                System.out.println("The unit should be set: " + unit.getClass());
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
			                test = true;
			     		}
			     		else
			     		{
			     			//maybe make a noise or something
			     		}
			        }
        		}
        		else
        		{
        			//maybe play a noise or something
        		}
        	}
        	else
        	{
        		unit = u;
                u.setTile(this);
                setAdjacentProtection();
                test = true;
                System.out.println("The unit should be set: " + unit.getClass());
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
    
    public boolean canMoveUnit(Tile t)
    {
    	if(unit != null)
    	{
    		if(unit.canMove())
    		{
    			if(getTerritory().isAdjacent(t))
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public boolean hasUnit()
    {
        return unit != null;
    }
    
    public boolean hasMoveableUnit()
    {
        if(unit instanceof Capital || unit instanceof Castle || unit == null)
            return false;
        return true;
    }
    
    public void setProtection(int p)
    {
        protection = p;
    }
//	public void setAdjacentProtection(int p) //sets this and adjacent tiles protection to p based on each tiles previous protection. //this also might be overlooked by the other setAdjacentProtection method as it does the samething but better.
//    {
//		//might be useless
//        if(protection < p)
//            protection = p;
//        for(int i = 0; i < adjacentTiles.length; i++)
//        {
//            if(player.equals(adjacentTiles[i].getPlayer()) && p > adjacentTiles[i].getProtection())
//            {
//                adjacentTiles[i].setProtection(p);
//            }
//        }
//    }
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
            	if(adjacentTiles[i] != null)
            	{
	                if(adjacentTiles[i].hasUnit() && adjacentTiles[i].getPlayer().equals(player))
	                {
	                    temp.add(adjacentTiles[i]);
	                }
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
        	if(adjacentTiles[i] != null)
        	{
	            if(adjacentTiles[i].getPlayer().equals(player))
	            {
	                adjacentTiles[i].setProtection();
	            }
        	}
        }
    }
    
    
    public void removeProtection() //you should remove the Unit on this tile before calling this method
    {
        for(int i = 0; i < adjacentTiles.length; i++)
        {
        	if(adjacentTiles[i] != null)
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
    		System.out.println("This probs works");
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
