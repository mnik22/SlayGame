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
    
    private GUIController guiController;
    private static final int NUM_PLAYERS = 6;
    private static final int NUM_TILES = 60;
    private Tile[][] GUIMap;
    private Color[] colors;
    
    static Player currentPlayer;
    static Tile[][] map;
    static Player[] players;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        GUIMap = new Tile[(NUM_TILES * 2) + 1][(NUM_TILES * 2) + 1];
        colors = new Color[NUM_PLAYERS];
        
        // TODO: Actually initialize the colors
        for (int i = 0; i < colors.length; i++)
            colors[i] = Color.CYAN;
        
        players = new Player[NUM_PLAYERS];
        players[0] = new HumanPlayer(colors[0]);
        for (int i = 1; i < players.length; i++)
            players[i] = new AIPlayer(colors[i]);
        
        currentPlayer = players[0];
        
        int count = 0;
        int[] tilesRemaining = {10,10,10,10,10,10}; // Each player gets 1/6 of the total tiles.
        while (count <= NUM_TILES) {
            
            /* Width = Number of Columns
             * Height = Number of Rows
             */
            int rnd = (int)(Math.random()*6);
            if(tilesRemaining[rnd] > 0)
            {
                int randPosW = (int)(Math.random() * GUIMap[0].length);
                int randPosH = (int)(Math.random() * GUIMap.length);
                
                if (GUIMap[randPosH][randPosW] == null) {
                    
                    GUIMap[randPosH][randPosW] = new Tile(randPosW, randPosH);
                    GUIMap[randPosH][randPosW].setPlayer(players[rnd]);
                    GUIMap[randPosH][randPosW].getPoints().addAll(loadCoords(randPosW, randPosH));
                    System.out.println("Coords: " + GUIMap[randPosH][randPosW].getPoints().toString());
//                    if(GUIMap[randPosH][randPosW]) //need to finish this, it is supposed to find if there are any adjacent of same player and then add it to the therrirtory or create a new one.
//                    {
//                        
//                    }
                    count++;
                    tilesRemaining[rnd]--;
            }
            
            
            
                
//                Territory t = new Territory(players[playerIndex]);
//                players[playerIndex].addTerritory(t);
                
                
            }
            
        }
        
        makeMap();
        
        guiController = new GUIController(primaryStage, GUIMap, players);
        
    }

    private Double[] loadCoords(int x, int y) {

        double top;
        double middle;
        double bottom;

        double first;
        double second;
        double third;
        double fourth;

        if (y == 0) {
            top = Math.sqrt(75);
            middle = 10;
            bottom = 2 * Math.sqrt(75);
        } else if (y == 1) {
            top = 10;
            middle = 2 * Math.sqrt(75);
            bottom = 3 * Math.sqrt(75);
        } else {
            top = y * Math.sqrt(75);
            middle = (y + 1) * Math.sqrt(75);
            bottom = (y + 2) * Math.sqrt(75);
        }

        if (y % 2 == 0) {
            first = x * 30;
            second = first + 5;
            third = second + 10;
            fourth = third + 5;
        } else {
            first = (x * 30) + 15;
            second = first + 5;
            third = second + 10;
            fourth = third + 5;
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
        
        for (int r = 0; r < map.length; r++) {
            
            
            
            for (int c = 0; c < map[0].length; c++) {
                
                GUIMapCol = 0;
                // Handle an Even Row
                if (r % 2 == 0 || r == 0) {
                    if (c == map[0].length)
                        map[r][c] = null; 
                    else if (c % 2 == 0 || c == 0) {
                        map[r][c] = GUIMap[r][GUIMapCol]; //there might still be an out of bounds exception on this line.
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
                        GUIMapCol++;
                    }
                    
                }
                
            }
            
        }
        
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
            players[playerNum].playRound();
            if(playerNum < players.length - 1)
                playerNum++;
            else
                playerNum = 0;
            
            currentPlayer = players[playerNum];
        }
    }
    
    private boolean gameWon()
    {
        // TODO Auto-generated method stub
        return false;
    }


}