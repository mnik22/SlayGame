/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIController {
    
    private Tile[][] map;
    private Parent root;
    private Stage primaryStage;
    
    private ImageView pesantImageView;
    private Image pesantImage;
    private ImageView spearmanImageView;
    private Image spearmanImage;
    private ImageView knightImageView;
    private Image knightImage;
    private ImageView baronImageView;
    private Image baronImage;
    
    public GUIController(Stage primaryStage, Tile[][] map) {
        
        this.map = map;
        this.primaryStage = primaryStage;
        
        try {
            
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            root.setStyle("-fx-background-color: #70BDFF;");
            root.lookup("#rightSideVBox").setStyle("-fx-background-color: #976729;");
            primaryStage.setTitle("Slay Game");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
        
        try {
            
            pesantImage = new Image(new FileInputStream("src/Pesant.png"));
            pesantImageView = (ImageView) root.lookup("#pesantImageView");
            pesantImageView.setImage(pesantImage);
            
            spearmanImage = new Image(new FileInputStream("src/Spearman.png"));
            spearmanImageView = (ImageView) root.lookup("#spearmanImageView");
            spearmanImageView.setImage(spearmanImage);
            
            knightImage = new Image(new FileInputStream("src/Knight.png"));
            knightImageView = (ImageView) root.lookup("#knightImageView");
            knightImageView.setImage(knightImage);
            
            baronImage = new Image(new FileInputStream("src/Baron.png"));
            baronImageView = (ImageView) root.lookup("#baronImageView");
            baronImageView.setImage(baronImage);
            
        } catch (IOException e) {
            
            e.printStackTrace();
            
        }
        
    }
    
    

}