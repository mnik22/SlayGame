/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {
    
    private GUIController guiController;
    private static final int NUM_PLAYERS = 6;
    
    static Tile[][] map;
    static Player[] players;
    
    @Override
    public void start(Stage primaryStage) {
        
        map = new Tile[61][45];
        players = new Player[NUM_PLAYERS];
        players[0] = new HumanPlayer();
        for (int i = 1; i < players.length; i++) {
            
            players[i] = new AIPlayer();
        }
        
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