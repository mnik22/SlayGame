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
    
    /* GUI dimensions add one because the matrix will
     * ignore the last index otherwise.
     */
    private static final int GUI_WIDTH = 1026 + 1;
    private static final int GUI_HEIGHT = 800 + 1;
    
    static Tile[][] map;
    static Player[] players;
    
    @Override
    public void start(Stage primaryStage) {
        
        /* The map will be a matrix of Tiles and
         * each index is a coordinate on the GUI.
         */
        map = new Tile[GUI_HEIGHT][GUI_WIDTH];
        map[GUI_HEIGHT / 2][GUI_WIDTH / 2] = new Tile(GUI_WIDTH / 2, GUI_HEIGHT / 2); // Is this flip accounted for in Tile??
        
        int count = 0;
        while (count <= NUM_TILES) {
            
            int randPosW = (int)(Math.random() * GUI_WIDTH);
            int randPosH = (int)(Math.random() * GUI_HEIGHT);
            
            if (map[randPosH][randPosW] == null) {
                
                map[randPosH][randPosW] = new Tile(randPosW, randPosH);
                count++;
                
            }
            
        }
        
        players = new Player[NUM_PLAYERS];
        players[0] = new HumanPlayer(Color.green);
        for (int i = 1; i < players.length; i++)
            players[i] = new AIPlayer(Color.cyan);
        
        guiController = new GUIController(primaryStage, map, players);
        
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