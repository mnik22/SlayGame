/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class GUIController {
    
    private Tile[][] map;
    private Player[] players;
    private Parent root;
    private Pane mapPane;
    
    private Label savingsLabel;
    private Label incomeLabel;
    private Label wagesLabel;
    private Label balanceLabel;
    
    private BarChart<String, Integer> propertiesBarChart;
//    private Image playerImage;
//    private Image computerImage;
    
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
    
    private Button finishTurnButton;
    
    @SuppressWarnings({ "unchecked" })
    public GUIController(Stage primaryStage, Tile[][] map, Player[] players) {
        
        this.map = map;
        this.players = players;
        
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
            
//            playerImage = new Image(new FileInputStream("src/Player.png"));
//            computerImage = new Image(new FileInputStream("src/Computer.png"));
            propertiesBarChart = (BarChart<String, Integer>) root.lookup("#propertiesBarChart");
            propertiesBarChart.setTitle("Territory Summary");
            
            XYChart.Series<String, Integer> series = new XYChart.Series<>(); 
            int cpuCount = 1;
            for (int i = 0; i < players.length; i++) {
                
                if (players[i] instanceof HumanPlayer)
                    series.getData().add(new XYChart.Data<>("H", players[i].getNumTerritories()));
                
                else if (players[i] instanceof AIPlayer)
                    series.getData().add(new XYChart.Data<>("C" + cpuCount, players[i].getNumTerritories()));
                
            }
            
            propertiesBarChart.getData().add(series);
            
            /*
             * Playable Objects Code
             */
                
            castleImage = new Image(new FileInputStream("src/Castle.png"));
            castleSelectorImageView = (ImageView) root.lookup("#castleSelectorImageView");
            castleSelectorImageView.setImage(castleImage);
            castleSelectorImageView.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Dragboard db = castleSelectorImageView.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(castleSelectorImageView.getImage());
                    content.putString("Castle");
                    db.setContent(content);
                    event.consume();
                }
            });
            castleSelectorImageView.setOnDragDone(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        if (Driver.currentPlayer instanceof HumanPlayer) {
                            boolean canBuyAnother = false;
                            for (Territory t : Driver.currentPlayer.getTerritories()) {
                                if (t.availbleUnits()[1] > 0)
                                    canBuyAnother = true;
                            }
                            if (!canBuyAnother)
                                castleSelectorImageView.setImage(null);
                        }
                    }
                    event.consume();
                }
            });
            
            pesantImage = new Image(new FileInputStream("src/Pesant.png"));
            pesantSelectorImageView = (ImageView) root.lookup("#pesantSelectorImageView");
            pesantSelectorImageView.setImage(pesantImage);
            pesantSelectorImageView.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Dragboard db = pesantSelectorImageView.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(pesantSelectorImageView.getImage());
                    content.putString("Pesant");
                    db.setContent(content);
                    event.consume();
                }
            });
            pesantSelectorImageView.setOnDragDone(new EventHandler <DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        if (Driver.currentPlayer instanceof HumanPlayer) {
                            boolean canBuyAnother = false;
                            for (Territory t : Driver.currentPlayer.getTerritories()) {
                                if (t.availbleUnits()[1] > 0)
                                    canBuyAnother = true;
                            }
                            if (!canBuyAnother)
                                pesantSelectorImageView.setImage(null);
                        }
                    }
                    event.consume();
                }
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
            
            finishTurnButton = (Button) root.lookup("#finishTurnButton");
            finishTurnButton.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override
                public void handle(ActionEvent arg0) {

                    if (Driver.currentPlayer instanceof HumanPlayer) {
                        
                        Driver.currentPlayer.buttonEndTurn();
                        
                    }
                    
                }
                
            });
            
            /*
             * Draw the Map
             */
            
            int tileCount = 0;
            mapPane = (Pane) root.lookup("#mapPane");
            System.out.println(mapPane.getWidth() + ", " + mapPane.getHeight());
            
            for (int r = 0; r < map.length; r++) {
                
                for (int c = 0; c < map[0].length; c++) {
                    
                    if (map[r][c] != null) {
                        
                        final Tile tile = map[r][c];
                        
                        Color playerColor = map[r][c].getPlayer().getColor() ;
                        int red = playerColor.getRed();
                        int green = playerColor.getGreen();
                        int blue = playerColor.getBlue();
                        int alpha = playerColor.getAlpha();
                        double opacity = alpha / 255.0 ;
                        javafx.scene.paint.Color fillColor = javafx.scene.paint.Color.rgb(red, green, blue, opacity);
                        
                        tile.setFill(fillColor);
                        tile.setStroke(fillColor);
                        
                        tile.setOnDragOver(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                if (event.getGestureSource() != tile &&
                                        event.getDragboard().hasImage()) {
                                    event.acceptTransferModes(TransferMode.MOVE);
                                }
                                event.consume();
                            }
                        });
                        tile.setOnDragEntered(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                 if (event.getGestureSource() != tile &&
                                         event.getDragboard().hasImage()) {
                                     tile.setFill(new ImagePattern(event.getDragboard().getImage()));
                                 }
                                 event.consume();
                            }
                        });
                        tile.setOnDragExited(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                tile.setFill(fillColor);
                                event.consume();
                            }
                        });
                        tile.setOnDragDropped(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                Dragboard db = event.getDragboard();
                                boolean success = false;
                                if (db.hasString()) {
                                	if (db.getString().equals("Castle"))
                                			success = tile.setUnit(new Castle(tile));
                                	else if (db.getString().equals("Pesant"))
                                			success = tile.setUnit(new Peasant(tile));
                                	else {
                                		System.out.println("Cannot place unit.");
                                	}
                                } else {
                                	System.out.println("No String found in the dragboard.");
                                }
                                event.setDropCompleted(success);
                                event.consume();
                            }
                        });
                        
                        mapPane.getChildren().add(tile);
                        tileCount++;
                    }
                    
                }
                
            }
            
            System.out.println("Tile Count: " + tileCount);
            
        } catch (IOException e) {
            
            e.printStackTrace();
            
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public void updateGraph(XYChart.Series<String, Integer> series) {
        
        propertiesBarChart.getData().setAll(series);
        
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
