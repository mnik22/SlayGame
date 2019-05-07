/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Image;
import java.io.FileInputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIController {
    
    private Tile[][] map;
    
//    private Image pesant = new Image(new FileInputStream("C:\\Users\\user\\Desktop\\x.jpg"));
    
    public GUIController(Stage primaryStage, Tile[][] map) {
        
        this.map = map;
        
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            primaryStage.setTitle("Slay Game");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        } catch(Exception e) {
            
            e.printStackTrace();
            
        }
        
    }
    
    

}