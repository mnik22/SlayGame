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
    private static final int NUM_TILES = 50;
    private Tile[][] GUIMap;
    private Color[] colors;
    
    static Tile[][] map;
    static Player[] players;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        GUIMap = new Tile[(NUM_TILES * 2) + 1][(NUM_TILES * 2) + 1];
        colors = new Color[NUM_PLAYERS];
        
        int count = 0;
        while (count <= NUM_TILES) {
            
            /* Width = Number of Columns
             * Height = Number of Rows
             */
            
            int randPosW = (int)(Math.random() * GUIMap[0].length);
            int randPosH = (int)(Math.random() * GUIMap.length);
            
            if (GUIMap[randPosH][randPosW] == null) {
                
                GUIMap[randPosH][randPosW] = new Tile(randPosW, randPosH);
                count++;
                
            }
            
        }
        
        // TODO: Actually initialize the colors
        for (int i = 0; i < colors.length; i++)
            colors[i] = Color.CYAN;
        
        players = new Player[NUM_PLAYERS];
        players[0] = new HumanPlayer(colors[0]);
        for (int i = 1; i < players.length; i++)
            players[i] = new AIPlayer(colors[i]);
        
        makeMap();
        
        guiController = new GUIController(primaryStage, GUIMap, players);
        
    }
    
    private void makeMap() {
        
        // TODO: OutOfBoundsException in line marked with @
        
//        map = new Tile[GUIMap.length][(GUIMap[0].length * 2) + 1];
//        
//        int GUIMapCol = 0;
//        
//        for (int r = 0; r < map.length; r++) {
//            
//            GUIMapCol = 0;
//            
//            for (int c = 0; c < map[0].length; c++) {
//                
//                // Handle an Even Row
//                if (r % 2 == 0 || r == 0) {
//                    if (c == map[0].length)
//                        map[r][c] = null; 
//                    else if (c % 2 == 0 || c == 0) {
//@                       map[r][c] = GUIMap[r][GUIMapCol];
//                        GUIMapCol++;
//                    }
//                    else
//                        map[r][c] = null;
//                    
//                // Handle an Odd Row
//                } else {
//                    if (c == 0)
//                        map[r][c] = null;
//                    else if (c % 2 == 0)
//                        map[r][c] = null;
//                    else {
//                        map[r][c] = GUIMap[r][GUIMapCol];
//                        GUIMapCol++;
//                    }
//                    
//                }
//                
//            }
//            
//        }
        
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
        }
    }
    
    private boolean gameWon()
    {
        // TODO Auto-generated method stub
        return false;
    }


}