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
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

class GUIController {
    
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

    private Image capitalImage;

    private Territory selectedTerritory;

    @SuppressWarnings({ "unchecked" })
    GUIController(Stage primaryStage) {
        
        map = Driver.GUIMap;
        players = Driver.players;
        
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

            propertiesBarChart = (BarChart<String, Integer>) root.lookup("#propertiesBarChart");
            propertiesBarChart.setTitle("Territory Summary");

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            int cpuCount = 1;
            for (Player player : players) {

                if (player instanceof HumanPlayer)
                    series.getData().add(new XYChart.Data<>("H", player.getNumTiles()));

                else if (player instanceof AIPlayer)
                    series.getData().add(new XYChart.Data<>("C" + cpuCount++, player.getNumTiles()));

            }

            propertiesBarChart.getData().addAll(series);

//            for (int i = 0; i < players.length; i++) {
//                Node n = propertiesBarChart.lookup(".data" + i + ".chart-bar");
//                n.setStyle("-fx-bar-fill: " + convertToHexColor(players[i].getColor()));
//            }
            
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
            
            /*
             * Number of Each Object Code
             */

            ImageView pesantImageView = (ImageView) root.lookup("#pesantImageView");
            pesantImageView.setImage(pesantImage);

            Image spearmanImage = new Image(new FileInputStream("src/Spearman.png"));
            ImageView spearmanImageView = (ImageView) root.lookup("#spearmanImageView");
            spearmanImageView.setImage(spearmanImage);

            Image knightImage = new Image(new FileInputStream("src/Knight.png"));
            ImageView knightImageView = (ImageView) root.lookup("#knightImageView");
            knightImageView.setImage(knightImage);

            Image baronImage = new Image(new FileInputStream("src/Baron.png"));
            ImageView baronImageView = (ImageView) root.lookup("#baronImageView");
            baronImageView.setImage(baronImage);

            Button finishTurnButton = (Button) root.lookup("#finishTurnButton");
            finishTurnButton.setOnAction(arg0 -> {

                if (Driver.currentPlayer instanceof HumanPlayer) {

                    boolean hasMovesLeft = false;
                    for (Territory t: Driver.currentPlayer.getTerritories())
                        if (t.canMoveUnit() || t.canPurchaseUnits()) {
                            hasMovesLeft = true;
                            highlightTerritory(t);
                        }

                    if (hasMovesLeft) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("You can still make moves, are you sure you want to end your turn?");
                        Optional<ButtonType> option = alert.showAndWait();
                        //noinspection OptionalGetWithoutIsPresent
                        if (option.get() == ButtonType.OK) {
                            Driver.currentPlayer.buttonEndTurn();
                        } else {
                            alert.close();
                        }
                    }

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

                        tile.setStyle("-fx-stroke-width: " + 3);

                        Color playerColor = tile.getPlayer().getColor();
                        javafx.scene.paint.Color fillColor = convertToHexColor(playerColor, 0.6);
                        javafx.scene.paint.Color strokeColor = convertToHexColor(playerColor, 1);

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
                        	if(!tile.hasUnit()) {
                                if (selectedTerritory.getTiles().contains(tile)) {
                                    highlightTile(tile);
                                } else {
                                    unhighlightTile(tile);
                                }
                            }
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
                                                if (!canBuyCastle(selectedTerritory) && castleSelectorImageView.getImage() != null)
                                                    castleSelectorImageView.setImage(null);

                                                if (!canBuyPesant(selectedTerritory) && pesantSelectorImageView.getImage() != null)
                                                    pesantSelectorImageView.setImage(null);
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
                                                if (!canBuyCastle(selectedTerritory) && castleSelectorImageView.getImage() != null)
                                                    castleSelectorImageView.setImage(null);

                                                if (!canBuyPesant(selectedTerritory) && pesantSelectorImageView.getImage() != null)
                                                    pesantSelectorImageView.setImage(null);
                                            }
                                        }
                                        break;

                                }
                            } else {
                                if (((Tile) event.getGestureSource()).canMoveUnit(tile)) {
                                    success = tile.moveUnit(((Tile) event.getGestureSource()).getUnit());
                                    if (success) {
                                        tile.setFill(new ImagePattern(tile.getUnit().getImage()));
                                        tile.setStroke(convertToHexColor(((Tile) event.getGestureSource()).getPlayer().getColor(), 1));
                                        highlightTerritory(selectedTerritory);
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
                                highlightTile(tile);
                                tile.removeUnit();
                                updateGraph();
                            }
                            event.consume();
                        });
                        tile.setOnMouseClicked(event -> {

                            System.out.println("Tile owned by: " + tile.getPlayer());
                            if (tile.hasUnit()) {
                                System.out.println("Unit on this tile: " + tile.getUnit());
                                System.out.println("This tile's unit can move: " + tile.getUnit().canMove());
                            } else {
                                System.out.println("This tile has no unit on it.");
                            }

                            selectedTerritory = tile.getTerritory();

                            for (Tile[] tempTiles : map) {
                                for (Tile t : tempTiles) {
                                    if (t != null) {
                                        unhighlightTile(t);
                                    }
                                }
                            }

                            if (tile.getPlayer() instanceof HumanPlayer) {

                                highlightTerritory(selectedTerritory);

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
    private void updateGraph() {

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        int cpuCount = 1;
        for (Player player : players) {

            if (player instanceof HumanPlayer)
                series.getData().add(new XYChart.Data<>("H", player.getNumTiles()));

            else if (player instanceof AIPlayer)
                series.getData().add(new XYChart.Data<>("C" + cpuCount++, player.getNumTiles()));

        }

        propertiesBarChart.getData().setAll(series);
        
    }

    void updateMap() {

        for (Tile[] tiles : map) {
            for (Tile t : tiles) {
                if (t != null) {
                    Color color = t.getPlayer().getColor();
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    t.setStroke(javafx.scene.paint.Color.rgb(r, g, b, 1));
                    if (t.isCapital()) {
                        t.setFill(new ImagePattern(capitalImage));
                    } else if (t.hasUnit()) {
                        t.setFill(new ImagePattern(t.getUnit().getImage()));
                    } else {
                        t.setFill(javafx.scene.paint.Color.rgb(r, g, b, 0.6));
                    }
                }
            }
        }

    }

    private void highlightTile(Tile t) {

        if (!t.hasUnit())
            t.setFill(convertToHexColor(t.getPlayer().getColor(), 1));

    }

    private void highlightTerritory(Territory t) {

        for (Tile tile : t.getTiles())
            highlightTile(tile);

    }

    private void unhighlightTile(Tile t) {

        if (!t.hasUnit())
            t.setFill(convertToHexColor(t.getPlayer().getColor(), 0.6));

    }

    private javafx.scene.paint.Color convertToHexColor(Color c, double opacity) {

        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        return javafx.scene.paint.Color.rgb(red, green, blue, opacity);

    }

    private String convertToHexColor(Color c) {

        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());

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

    private void setSavings(int amt) {
        
        savingsLabel.setText("" + amt);
        
    }
    
    private void setIncome(int amt) {
        
        incomeLabel.setText("" + amt);
        
    }
    
    private void setWages(int amt) {
        
        wagesLabel.setText("" + amt);
        
    }
    
    private void setBalance(int amt) {
        
        balanceLabel.setText("" + amt);
        
    }

}
