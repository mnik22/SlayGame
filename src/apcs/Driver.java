/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {
    
    private GUIController guiController;
    
    static Tile[][] map;
    static AI cpu1;
    
    @Override
    public void start(Stage primaryStage) {
        
        guiController = new GUIController(primaryStage, map);
        
    }
    
    public static void main(String[] args) {
        
        map = new Tile[61][45];
        cpu1 = new AI();
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
    

}