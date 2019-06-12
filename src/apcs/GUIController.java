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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class GUIController {
    
    private Tile[][] map;
    private Player[] players;
    private Parent root;

    private Label savingsLabel;
    private Label incomeLabel;
    private Label wagesLabel;
    private Label balanceLabel;
    
    private BarChart<String, Integer> propertiesBarChart;
    
    private ImageView pesantSelectorImageView;
    private Image pesantImage;
    private ImageView castleSelectorImageView;
    private Image castleImage;

    private Image spearmanImage;
    private Image knightImage;
    private Image baronImage;
    
    private Image capitalImage;

    private Territory selectedTerritory;

    @SuppressWarnings({ "unchecked" })
    GUIController(Stage primaryStage, Tile[][] map, Player[] players) {
        
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

            assert root != null;
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
            for (Player player : players) {

                if (player instanceof HumanPlayer)
                    series.getData().add(new XYChart.Data<>("H", player.getNumTerritories()));

                else if (player instanceof AIPlayer)
                    series.getData().add(new XYChart.Data<>("C" + cpuCount++, player.getNumTerritories()));

            }
            
            propertiesBarChart.getData().addAll(series);
            
            /*
             * Playable Objects Code
             */
                
            castleImage = new Image(new FileInputStream("src/Castle.png"));
            castleSelectorImageView = (ImageView) root.lookup("#castleSelectorImageView");
            castleSelectorImageView.setOnDragDetected(event -> {
                if (Integer.parseInt(balanceLabel.getText()) - 10 >= 0) {
                    Dragboard db = castleSelectorImageView.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(castleSelectorImageView.getImage());
                    content.putString("N_Castle");
                    db.setContent(content);
                    event.consume();
                }
            });
            castleSelectorImageView.setOnDragDone(event -> {
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
            });
            
            pesantImage = new Image(new FileInputStream("src/Pesant.png"));
            pesantSelectorImageView = (ImageView) root.lookup("#pesantSelectorImageView");
            pesantSelectorImageView.setOnDragDetected(event -> {
                if (Integer.parseInt(balanceLabel.getText()) - 5 >= 0) {
                    Dragboard db = pesantSelectorImageView.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(pesantSelectorImageView.getImage());
                    content.putString("N_Pesant");
                    db.setContent(content);
                    event.consume();
                }
            });
            pesantSelectorImageView.setOnDragDone(event -> {
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
            });
            
            /*
             * Number of Each Object Code
             */

            ImageView pesantImageView = (ImageView) root.lookup("#pesantImageView");
            pesantImageView.setImage(pesantImage);
            
            spearmanImage = new Image(new FileInputStream("src/Spearman.png"));
            ImageView spearmanImageView = (ImageView) root.lookup("#spearmanImageView");
            spearmanImageView.setImage(spearmanImage);
            
            knightImage = new Image(new FileInputStream("src/Knight.png"));
            ImageView knightImageView = (ImageView) root.lookup("#knightImageView");
            knightImageView.setImage(knightImage);
            
            baronImage = new Image(new FileInputStream("src/Baron.png"));
            ImageView baronImageView = (ImageView) root.lookup("#baronImageView");
            baronImageView.setImage(baronImage);

            Button finishTurnButton = (Button) root.lookup("#finishTurnButton");
            finishTurnButton.setOnAction(arg0 -> {

                if (Driver.currentPlayer instanceof HumanPlayer) {

                    Driver.currentPlayer.buttonEndTurn();

                }

            });
            
            capitalImage = new Image(new FileInputStream("src/Capital.png"));
            
            /*
             * Draw the Map
             */
            
            int tileCount = 0;
            Pane mapPane = (Pane) root.lookup("#mapPane");
            System.out.println(mapPane.getWidth() + ", " + mapPane.getHeight());

            for (Tile[] tiles : map) {

                for (int c = 0; c < map[0].length; c++) {

                    if (tiles[c] != null) {

                        final Tile tile = tiles[c];

                        Color playerColor = tile.getPlayer().getColor();
                        int red = playerColor.getRed();
                        int green = playerColor.getGreen();
                        int blue = playerColor.getBlue();
                        int alpha = playerColor.getAlpha();
                        double opacity = (alpha / 255.0) * 0.75;
                        javafx.scene.paint.Color fillColor = javafx.scene.paint.Color.rgb(red, green, blue, opacity);
                        javafx.scene.paint.Color strokeColor = javafx.scene.paint.Color.rgb(red, green, blue, 1);

                        if (tile.isCapital()) tile.setFill(new ImagePattern(capitalImage));
                        else tile.setFill(fillColor);
                        tile.setStroke(strokeColor);

                        tile.setOnDragOver(event -> {
                            if (event.getGestureSource() != tile && event.getDragboard().hasImage()) {
                                event.acceptTransferModes(TransferMode.MOVE);
                            }
                            event.consume();
                        });
                        tile.setOnDragEntered(event -> {
                            if (event.getGestureSource() != tile && event.getDragboard().hasImage() && tile.getUnit() == null) {
                                if (event.getGestureSource() instanceof ImageView && tile.getPlayer() instanceof HumanPlayer && selectedTerritory.getTiles().contains(tile) && (canBuyPesant(tile.getTerritory()) || canBuyCastle(tile.getTerritory()))) {
                                    tile.setFill(new ImagePattern(event.getDragboard().getImage()));
                                } else if (((Tile) event.getGestureSource()).canMoveUnit(tile)) {
                                    tile.setFill(new ImagePattern(event.getDragboard().getImage()));
                                }
                            }
                            event.consume();
                        });
                        tile.setOnDragExited(event -> {
                        	if(!tile.hasUnit())
                        		tile.setFill(fillColor);
                            event.consume();
                        });
                        tile.setOnDragDropped(event -> {
                            Dragboard db = event.getDragboard();
                            System.out.println(event.getDragboard().getString());
                            boolean success = false;
                            if (db.hasString()) {
                                switch (db.getString()) {

                                    case "N_Pesant":
                                        if (event.getGestureSource() instanceof ImageView && tile.getPlayer() instanceof HumanPlayer && selectedTerritory.getTiles().contains(tile) && canBuyPesant(tile.getTerritory())) {
                                            success = tile.setUnit(new Peasant(tile));
                                            if (success) {
                                                tile.setFill(new ImagePattern(tile.getUnit().getImage()));
                                                tile.getTerritory().buyPeasant();
                                                updateLabels(tile.getTerritory());
                                            }
                                        }
                                        break;

                                    case "N_Castle":
                                        if (event.getGestureSource() instanceof ImageView && tile.getPlayer() instanceof HumanPlayer && selectedTerritory.getTiles().contains(tile) && canBuyCastle(tile.getTerritory())) {
                                            success = tile.setUnit(new Castle(tile));
                                            if (success) {
                                                tile.setFill((new ImagePattern(tile.getUnit().getImage())));
                                                tile.getTerritory().buyCastle();
                                                updateLabels(tile.getTerritory());
                                            }
                                        }
                                        break;

                                }
                            } else {
                                if (((Tile) event.getGestureSource()).canMoveUnit(tile)) {
                                    success = tile.moveUnit(((Tile) event.getGestureSource()).getUnit());
                                    if (success) {
                                        tile.setFill(new ImagePattern(tile.getUnit().getImage()));
                                        updateGraph();
                                    }
                                }
                            }
                            event.setDropCompleted(success);
                            event.consume();
                        });
                        tile.setOnDragDetected(event -> {
                            if (tile.getPlayer() instanceof HumanPlayer && !tile.isCapital() && !(tile.getUnit() instanceof Castle)) {
                                Dragboard db = tile.startDragAndDrop(TransferMode.MOVE);
                                ClipboardContent content = new ClipboardContent();
                                if (tile.getUnit() != null) {
                                    content.putImage(tile.getUnit().getImage());
                                } else {
                                    System.out.println("There are no units to move on this tile.");
                                }
                                db.setContent(content);
                                event.consume();
                            } else {
                                System.out.println("This tile does not belong to you or the unit cannot be moved.");
                            }
                        });
                        tile.setOnDragDone(event -> {
                            if (event.getTransferMode() == TransferMode.MOVE) {
                                tile.setFill(fillColor);
                                tile.removeUnit();
                            }
                            event.consume();
                        });
                        tile.setOnMouseClicked(event -> {

                            selectedTerritory = tile.getTerritory();

                            for (Tile[] tempTiles : map) {
                                for (Tile t : tempTiles) {
                                    if (t != null) {
                                        Color color = t.getPlayer().getColor();
                                        int r = color.getRed();
                                        int g = color.getGreen();
                                        int b = color.getBlue();
                                        int a = color.getAlpha();
                                        double o = (a / 255.0) * 0.75;
                                        javafx.scene.paint.Color fill = javafx.scene.paint.Color.rgb(r, g, b, o);
                                        javafx.scene.paint.Color stroke = javafx.scene.paint.Color.rgb(r, g, b, 1);
                                        if (!t.hasUnit()) t.setFill(fill);
                                        t.setStroke(stroke);
                                    }
                                }
                            }

                            if (tile.getPlayer() instanceof HumanPlayer) {

                                for (Tile t : selectedTerritory.getTiles()) {
                                    Color color = t.getPlayer().getColor();
                                    int r = color.getRed();
                                    int g = color.getGreen();
                                    int b = color.getBlue();
                                    int a = color.getAlpha();
                                    double o = a / 255.0;
                                    javafx.scene.paint.Color fill = javafx.scene.paint.Color.rgb(r, g, b, o);
                                    javafx.scene.paint.Color stroke = javafx.scene.paint.Color.rgb(r, g, b, 1);
                                    if (!t.hasUnit()) t.setFill(fill);
                                    t.setStroke(stroke);
                                }

                                updateLabels(selectedTerritory);

                                if (canBuyCastle(selectedTerritory))
                                    castleSelectorImageView.setImage(castleImage);

                                if (canBuyPesant(selectedTerritory))
                                    pesantSelectorImageView.setImage(pesantImage);

                            }

                            if (!canBuyCastle(selectedTerritory) && castleSelectorImageView.getImage() != null)
                                castleSelectorImageView.setImage(null);

                            if (!canBuyPesant(selectedTerritory) && pesantSelectorImageView.getImage() != null)
                                pesantSelectorImageView.setImage(null);

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
    public void updateGraph() {

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        int cpuCount = 1;
        for (Player player : players) {

            if (player instanceof HumanPlayer)
                series.getData().add(new XYChart.Data<>("H", player.getNumTerritories()));

            else if (player instanceof AIPlayer)
                series.getData().add(new XYChart.Data<>("C" + cpuCount++, player.getNumTerritories()));

        }

        propertiesBarChart.getData().setAll(series);
        
    }

    public void updateMap() {

        for (Tile[] tiles : map) {
            for (Tile t : tiles) {
                if (t != null) {
                    Color color = t.getPlayer().getColor();
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int a = color.getAlpha();
                    double o = (a / 255.0) * 0.75;
                    t.setStroke(javafx.scene.paint.Color.rgb(r, g, b, 1));
                    if (t.isCapital()) {
                        t.setFill(new ImagePattern(capitalImage));
                    } else if (t.hasUnit()) {
                        t.setFill(new ImagePattern(t.getUnit().getImage()));
                    } else {
                        t.setFill(javafx.scene.paint.Color.rgb(r, g, b, o));
                    }
                }
            }
        }

    }

    public boolean moveUnit(Tile source, Tile target) {

        return false;

    }

    public boolean purchasePeasant (Tile target) {

        if (target.getTerritory().canPurchaseUnits()) {
            target.setUnit(new Peasant(target));
            target.setFill(new ImagePattern(pesantImage));
            return true;
        }

        return false;

    }

    private boolean canBuyCastle(Territory t) {

        boolean canBuyCastle = false;
        if (t.getMoney() - 10 >= 0)
            canBuyCastle = true;
        return canBuyCastle;

    }

    private boolean canBuyPesant(Territory t) {

        boolean canBuyPesant = false;
        if (t.getMoney() - 5 >= 0)
            canBuyPesant = true;
        return canBuyPesant;

    }

    private void updateLabels(Territory t) {

        setSavings(t.getMoney());
        setIncome(t.getNumTiles());
        setWages(t.getWages());
        setBalance((t.getMoney() + t.getNumTiles()) - t.getWages());

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
