/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class GUIController {
    
    private Tile[][] map;
    private Player[] players;
    private Parent root;
    private Stage primaryStage;
    
    private Label savingsLabel;
    private Label incomeLabel;
    private Label wagesLabel;
    private Label balanceLabel;
    
    private BarChart<ImageView, Integer> propertiesBarChart;
    private Image playerImage;
    private Image computerImage;
    
    private ImageView pesantSelectorImageView;
    private ImageView castleSelectorImageView;
    private Image castleImage;
    
    private ImageView pesantImageView;
    private Image pesantImage;
    private ImageView spearmanImageView;
    private Image spearmanImage;
    private ImageView knightImageView;
    private Image knightImage;
    private ImageView baronImageView;
    private Image baronImage;
    
    @SuppressWarnings({ "unchecked" })
    public GUIController(Stage primaryStage, Tile[][] map, Player[] players) {
        
        this.map = map;
        
        for (int r = 0; r < map.length; r++) {
            
            for (int c = 0; c < map[0].length; c++) {
                
//                map[r][c].setStyle("-fx-background-color: #156136;");
                
            }
            
        }
        
        this.players = players;
        this.primaryStage = primaryStage;
        
        try {
            
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
            root.setStyle("-fx-background-color: #70BDFF;");
            root.lookup("#rightSideVBox").setStyle("-fx-background-color: #976729;");
            primaryStage.setTitle("Slay Game");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
        
        try {
            
            /*
             * Money Displaying Code
             */
            
            savingsLabel = (Label) root.lookup("#savingsLabel");
            incomeLabel = (Label) root.lookup("#incomeLabel");
            wagesLabel = (Label) root.lookup("#wagesLabel");
            balanceLabel = (Label) root.lookup("#balanceLabel");
            
            /*
             * Chart Code
             */
            
            playerImage = new Image(new FileInputStream("src/Player.png"));
            computerImage = new Image(new FileInputStream("src/Computer.png"));
            propertiesBarChart = (BarChart<ImageView, Integer>) root.lookup("#propertiesBarChart");
            propertiesBarChart.setTitle("Territory Summary");
            
            XYChart.Series<ImageView, Integer> series1 = new XYChart.Series<>();   
            for (int i = 0; i < players.length; i++) {
                
                if (players[i] instanceof HumanPlayer)
                    series1.getData().add(new XYChart.Data<ImageView, Integer>(new ImageView(playerImage), players[i].getNumTiles()));
                
                else if (players[i] instanceof AIPlayer)
                    series1.getData().add(new XYChart.Data<ImageView, Integer>(new ImageView(computerImage), players[i].getNumTiles()));
                
            }
            
//            propertiesBarChart.getData().add(series1);
            
            /*
             * Playable Objects Code
             */
                
            castleImage = new Image(new FileInputStream("src/Castle.png"));
            castleSelectorImageView = (ImageView) root.lookup("#castleSelectorImageView");
            castleSelectorImageView.setImage(castleImage);
            castleSelectorImageView.addEventHandler(MouseEvent.DRAG_DETECTED, mouseEvent -> {
                Dragboard db = castleSelectorImageView.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(castleSelectorImageView.getImage());
                db.setContent(content);
                mouseEvent.consume();
            });
            
            pesantImage = new Image(new FileInputStream("src/Pesant.png"));
            pesantSelectorImageView = (ImageView) root.lookup("#pesantSelectorImageView");
            pesantSelectorImageView.setImage(pesantImage);
            pesantSelectorImageView.addEventHandler(MouseEvent.DRAG_DETECTED, mouseEvent -> {
                Dragboard db = pesantSelectorImageView.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(pesantSelectorImageView.getImage());
                db.setContent(content);
                mouseEvent.consume();
            });
            
            /*
             * Number of Each Object Code
             */
            
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
    
    public void setSavings(int amt) {
        
        savingsLabel.setText("" + amt);
        
    }
    
    public void setIncome(int amt) {
        
        incomeLabel.setText("" + amt);
        
    }
    
    public void setWages(int amt) {
        
        wagesLabel.setText("" + amt);
        
    }
    
    public void setBalance(int amt) {
        
        balanceLabel.setText("" + amt);
        
    }

}