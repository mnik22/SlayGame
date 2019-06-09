/*Name: 
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {
    
    public static GUIController guiController;
    private final int MAP_PANE_WIDTH = 1000;
    private final int MAP_PANE_HEIGHT = 800;
    private static final int NUM_PLAYERS = 6;
    private static final int NUM_TILES = 360;
    private Tile[][] GUIMap;
    private Color[] colors;
    private static final int CENTER_TILE_INDEX_W = 7;
    private static final int CENTER_TILE_INDEX_H = 20;
    private static final int CENTER_TILE_GUI_INDEX_ROW = 21;
    private static final int CENTER_TILE_GUI_INDEX_COLUMN = 22;
    
    private Thread gameThread;
    
    static Player currentPlayer;
    static Tile[][] map;
    public static Player[] players;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        GUIMap = new Tile[42][44];
        colors = new Color[NUM_PLAYERS];
        
        colors[0] = Color.BLUE;
        colors[1] = Color.GREEN;
        colors[2] = Color.MAGENTA;
        colors[3] = Color.RED;
        colors[4] = Color.YELLOW;
        colors[5] = Color.PINK;
        
        players = new Player[NUM_PLAYERS];
        players[0] = new HumanPlayer(colors[0]);
        for (int i = 1; i < players.length; i++) 
            players[i] = new AIPlayer(colors[i]); 
        
        currentPlayer = players[0];
        
        int count = 0;
        int numTilesCreated = 0;
        int[] tilesRemaining = { NUM_TILES / NUM_PLAYERS, NUM_TILES / NUM_PLAYERS, NUM_TILES / NUM_PLAYERS, NUM_TILES / NUM_PLAYERS, NUM_TILES / NUM_PLAYERS, NUM_TILES / NUM_PLAYERS}; // Each player gets 1/6 of the total tiles.
        
        //sets center tile
        int randomPlayer = (int) (Math.random() * players.length);
        System.out.println("randomPlayer: " + randomPlayer);
        Tile t = new Tile(CENTER_TILE_INDEX_W, CENTER_TILE_INDEX_H);
        t.setPlayer(players[randomPlayer]);
        tilesRemaining[randomPlayer]--;
        count++;
        t.getPoints().addAll(loadCoords(CENTER_TILE_INDEX_W, CENTER_TILE_INDEX_H));
        System.out.println("Coords: " + t.getPoints().toString());
        GUIMap[CENTER_TILE_GUI_INDEX_ROW][CENTER_TILE_GUI_INDEX_COLUMN] = t;
        
        
        while(count < NUM_TILES)
        {
            int rndPlayer = (int) (Math.random() * players.length);
            if(tilesRemaining[rndPlayer] > 0)
            {
                Tile temp = new Tile(CENTER_TILE_INDEX_W, CENTER_TILE_INDEX_H);
                temp.setPlayer(players[rndPlayer]);
                placeTile(temp, CENTER_TILE_INDEX_W, CENTER_TILE_INDEX_H, CENTER_TILE_GUI_INDEX_ROW, CENTER_TILE_GUI_INDEX_COLUMN);
                count++;
                tilesRemaining[rndPlayer]--;
            }
        }
            
        makeMap();
        
        for (int j = 0; j < players.length; j++)
        {
            System.out.println("num tiles for p" + j + ": " + players[j].getNumTiles());
        }
        
        guiController = new GUIController(primaryStage, GUIMap, players);
        
        gameThread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("started thread");
                newGame();
                playGame();
                System.out.println("running thread");
            }
                    
        });
        
        gameThread.start();
    }
    
    private void placeTile(Tile t, int tileIndexW, int tileIndexH, int tileGuiIndexRow, int tileGuiIndexColumn)
    {
        int rndPos = (int) (Math.random()*6) + 1; //6 sides of a hexagon
        
        switch(rndPos) {
        
            case 1: //top
                if(tileGuiIndexRow > 2 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length - 2 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow+2][tileGuiIndexColumn] == null) {
                    t.getPoints().addAll(loadCoords(tileIndexW, tileIndexH + 2));
                    GUIMap[tileGuiIndexRow+2][tileGuiIndexColumn] = t;
                }
                else if (tileGuiIndexRow > 2 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -2 && tileGuiIndexColumn < GUIMap[0].length -1)
                    placeTile(t, tileIndexW, tileIndexH +2, tileGuiIndexRow+2, tileGuiIndexColumn);
                else
                    return;
                break;
            case 2: // top right
                if(tileGuiIndexRow %2 == 0) {
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow+1][tileGuiIndexColumn +1] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW+1, tileIndexH+1));
                        GUIMap[tileGuiIndexRow+1][tileGuiIndexColumn +1] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW + 1, tileIndexH+1, tileGuiIndexRow+1, tileGuiIndexColumn +1 );
                    else
                        return;
                }
                else {
                    
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow+1][tileGuiIndexColumn] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW, tileIndexH+1));
                        GUIMap[tileGuiIndexRow+1][tileGuiIndexColumn] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW, tileIndexH+1, tileGuiIndexRow+1, tileGuiIndexColumn);
                    else
                        return;
                }
                break;
            case 3: //bottom right
                if(tileGuiIndexRow %2 == 0) {
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow -1][tileGuiIndexColumn +1] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW +1, tileIndexH -1));
                        GUIMap[tileGuiIndexRow-1][tileGuiIndexColumn +1] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW +1, tileIndexH-1, tileGuiIndexRow-1, tileGuiIndexColumn +1);
                    else
                        return;
                }
                else {
                    
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow -1][tileGuiIndexColumn] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW, tileIndexH -1));
                        GUIMap[tileGuiIndexRow-1][tileGuiIndexColumn] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW, tileIndexH-1, tileGuiIndexRow-1, tileGuiIndexColumn);
                    else
                        return;
                }
                break;
            case 4: //bottom
                if(tileGuiIndexRow > 2 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -2 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow - 2][tileGuiIndexColumn] == null) {
                    t.getPoints().addAll(loadCoords(tileIndexW, tileIndexH -2));
                    GUIMap[tileGuiIndexRow - 2][tileGuiIndexColumn] = t;
                }
                else if(tileGuiIndexRow > 2 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -2 && tileGuiIndexColumn < GUIMap[0].length -1)
                    placeTile(t, tileIndexW, tileIndexH - 2, tileGuiIndexRow - 2, tileGuiIndexColumn);
                else 
                    return;
                break;
            case 5: //bottom left
                if(tileGuiIndexRow %2 == 0) {
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow-1][tileGuiIndexColumn ] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW , tileIndexH-1));
                        GUIMap[tileGuiIndexRow-1][tileGuiIndexColumn] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW, tileIndexH-1, tileGuiIndexRow-1, tileGuiIndexColumn);
                    else
                        return;
                }
                else {
                    
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow-1][tileGuiIndexColumn - 1] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW - 1, tileIndexH-1));
                        GUIMap[tileGuiIndexRow-1][tileGuiIndexColumn - 1] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW -1, tileIndexH-1, tileGuiIndexRow-1, tileGuiIndexColumn - 1);
                    else
                        return;
                }
                break;
            case 6: //top left
                if(tileGuiIndexRow %2 == 0) {
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow + 1][tileGuiIndexColumn] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW, tileIndexH + 1));
                        GUIMap[tileGuiIndexRow + 1][tileGuiIndexColumn] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW, tileIndexH+1, tileGuiIndexRow+1, tileGuiIndexColumn);
                    else
                        return;
                }
                else {
                    
                    if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1 && GUIMap[tileGuiIndexRow + 1][tileGuiIndexColumn -1] == null) {
                        t.getPoints().addAll(loadCoords(tileIndexW -1, tileIndexH + 1));
                        GUIMap[tileGuiIndexRow + 1][tileGuiIndexColumn -1] = t;
                    }
                    else if(tileGuiIndexRow > 1 && tileGuiIndexColumn > 1 && tileGuiIndexRow < GUIMap.length -1 && tileGuiIndexColumn < GUIMap[0].length -1)
                        placeTile(t, tileIndexW -1, tileIndexH+1, tileGuiIndexRow+1, tileGuiIndexColumn-1);
                    else
                        return;
                }
                break;
        }
    }
    
            
    private Double[] loadCoords(int x, int y) {

        double top;
        double middle;
        double bottom;

        double first;
        double second;
        double third;
        double fourth;

        if ((y + 4) * Math.sqrt(300) <= MAP_PANE_HEIGHT && (x * 60) + 83 <= MAP_PANE_WIDTH && (x*60) >= 0) {
           
            top = (y + 2) * Math.sqrt(300);
            middle = (y + 3) * Math.sqrt(300);
            bottom = (y + 4) * Math.sqrt(300);

            if (y % 2 == 0) {
                first = x * 60;
                second = first + 10;
                third = second + 20;
                fourth = third + 10;
            } else {
                first = (x * 60) + 30;
                second = first + 10;
                third = second + 20;
                fourth = third + 10;
            }
            
            first += 13;
            second += 13;
            third += 13;
            fourth += 13;
            
        } else {
            
            top = -1;
            middle = -1;
            bottom = -1;
            first = -1;
            second = -1;
            third = -1;
            fourth = -1;
            
        }

        // For a linked hexagon array.
        Double[] coords = { second, top,
                third, top,
                fourth, middle,
                third, bottom,
                second, bottom,
                first, middle };

        return coords;

    }
    
    private void makeMap() {
        
        map = new Tile[GUIMap.length][(GUIMap[0].length * 2) + 1];
        
        int GUIMapCol = 0;
        
        for (int r = 0; r < map.length - 1; r++) {
            GUIMapCol = 0;
            for (int c = 0; c < map[0].length - 1; c++) {
                
                
                // Handle an Even Row
                if (r % 2 == 0) {
                    if (c == map[0].length)
                        map[r][c] = null; 
                    else if (c % 2 == 0) {
                        map[r][c] = GUIMap[r][GUIMapCol]; //there might still be an out of bounds exception on this line.
                        if(map[r][c] != null)                        
                            map[r][c].setPosition(r, c);
                        GUIMapCol++;
                    }
                    else
                        map[r][c] = null;
                    
                // Handle an Odd Row
                } else {
                    if (c == 0)
                        map[r][c] = null;
                    else if (c % 2 == 0)
                        map[r][c] = null;
                    else {
                        map[r][c] = GUIMap[r][GUIMapCol];
                        if(map[r][c] != null)                        
                            map[r][c].setPosition(r, c);
                        GUIMapCol++;
                    }
                    
                }
//               System.out.println("row: " + r + ", col: " + c);
            }
            
        }
        
        
        //TODO this code works, the setAdjacent method it calls though has out of bounds exceptions
        for (int r = 1; r < map.length; r++)
        {
            for (int c = 1; c < map[0].length; c++)
            {
                if(map[r][c] != null)
                    map[r][c].setAdjacent();              
            }
        }
        
        for(int r = 1; r < map.length; r++)
        {
        	for (int c = 1; c < map[0].length; c++)
        	{
        		if(map[r][c] != null)
        		{
        			setTerritories(map[r][c]);
        		}
        	}
        }
        
        for(int p = 0; p < players.length; p++)
        {
        	for(int t = 0; t < players[p].getTerritories().size(); t++)
        	{
        		players[p].getTerritories().get(t).setBeginningCapital();
        	}
        }
        
        
        
//        System.out.println("mapMade");
        
    }
    
    public static void main(String[] args) {
        
        launch(args);
//        GUI gui = new GUI(map);
//        gui.displayGame();
        
    }
    
    public void newGame()
    {
        for(int i = 1; i < map.length - 1; i++)
        {
            switch(i%2)
            {
            
            case 0:     for(int j = 2; j < map[0].length - 1; j += 2)
                        {
                             map[i][j] = new Tile(i,j);
                        }
                        break;
            case 1:     for(int k = 1; k < map[0].length - 1; k += 2)
                        {
                             map[i][k] = new Tile(i,k);
                        }
                        break;
            }
        }
        
    }
    
    public void playGame()
    {
        int playerNum = 0;
        while(playerNum < players.length && !gameWon())
        {
        	//System.out.println(currentPlayer.getColor());
        	if(playerNum == 0)
        	{
        		HumanPlayer temp = (HumanPlayer)players[playerNum];
        		temp.playRound();
        	}
        	else
        	{
        		AIPlayer temp = (AIPlayer)players[playerNum];
        		temp.playRound();
        	}
            if(playerNum < players.length - 1)
                playerNum++;
            else
                playerNum = 0;
            
            currentPlayer = players[playerNum];
        }
    }
    
    private boolean gameWon()
    {
        for (int i = 0; i < players.length; i++)
        {
            if(players[i].getNumTiles() == NUM_TILES)
                return true;
        }
        return false;
    }

    
    public void setTerritories(Tile t)
    {
    	boolean check = true;
    	for(int i = 0; i < t.getAdjacentTiles().length; i++)
    	{
    		if(t.getAdjacentTiles()[i] != null)
    		{
	    		if(t.getAdjacentTiles()[i].getPlayer().equals(t.getPlayer()) && t.getAdjacentTiles()[i].hasTerritory())
	    		{
	    			check = false;
					Territory ter = t.getAdjacentTiles()[i].getTerritory();
	    			if(!t.hasTerritory())
	    			{
		    			ter.addTile(t);
		    			t.setHasTerritory();
	    			}
	    			else if(!ter.equals(t.getTerritory()))
	    			{
	    				Tile temp = t.getAdjacentTiles()[i];
	    				ArrayList<Tile> meme = ter.getTiles();
	    				ter.getPlayer().removeTerritory(ter);
	    				t.getTerritory().addTiles(meme);    				
	    			}
	    		}
    		}
    	}
    	if(check)
    	{
	    	Territory ter = new Territory(t.getPlayer());
			t.getPlayer().addTerritory(ter);
			ter.addTile(t);
			t.setHasTerritory();
    	}
    }

}