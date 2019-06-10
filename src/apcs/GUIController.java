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
//    private Image playerImage;
//    private Image computerImage;
    
    private ImageView pesantSelectorImageView;
    private ImageView castleSelectorImageView;
    private Image castleImage;

    private Image pesantImage;
    private Image spearmanImage;
    private Image knightImage;
    private Image baronImage;
    
    private Image capitalImage;

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
            
            propertiesBarChart.getData().add(series);
            
            /*
             * Playable Objects Code
             */
                
            castleImage = new Image(new FileInputStream("src/Castle.png"));
            castleSelectorImageView = (ImageView) root.lookup("#castleSelectorImageView");
            castleSelectorImageView.setImage(castleImage);
            castleSelectorImageView.setOnDragDetected(event -> {
                Dragboard db = castleSelectorImageView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(castleSelectorImageView.getImage());
                content.putString("N_Castle");
                db.setContent(content);
                event.consume();
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
            pesantSelectorImageView.setImage(pesantImage);
            pesantSelectorImageView.setOnDragDetected(event -> {
                Dragboard db = pesantSelectorImageView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(pesantSelectorImageView.getImage());
                content.putString("N_Pesant");
                db.setContent(content);
                event.consume();
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

                        Color playerColor = tiles[c].getPlayer().getColor();
                        int red = playerColor.getRed();
                        int green = playerColor.getGreen();
                        int blue = playerColor.getBlue();
                        int alpha = playerColor.getAlpha();
                        double opacity = alpha / 255.0;
                        javafx.scene.paint.Color fillColor = javafx.scene.paint.Color.rgb(red, green, blue, opacity);

                        tile.setFill(fillColor);
                        tile.setStroke(fillColor);

                        tile.setOnDragOver(event -> {
                            if (event.getGestureSource() != tile &&
                                    event.getDragboard().hasImage()) {
                                event.acceptTransferModes(TransferMode.MOVE);
                            }
                            event.consume();
                        });
                        tile.setOnDragEntered(event -> {
                            if (event.getGestureSource() != tile &&
                                    event.getDragboard().hasImage() &&
                                    tile.getUnit() == null) {
                                tile.setFill(new ImagePattern(event.getDragboard().getImage()));
                            }
                            event.consume();
                        });
                        tile.setOnDragExited(event -> {
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
                                        success = tile.setUnit(new Peasant(null));
                                        break;

                                    case "N_Castle":
                                        success = tile.setUnit(new Castle(null));
                                        break;

                                    default:
                                        int x = Integer.parseInt(db.getString().substring(0, db.getString().indexOf(',')));
                                        int y = Integer.parseInt(db.getString().substring(db.getString().indexOf(' ') + 1));
                                        Tile previousTile = map[y][x];
                                        if (previousTile.getUnit() != null)
                                        {
                                            success = tile.moveUnit(previousTile.getUnit());
                                        }
                                        else
                                            System.out.println("There is no unit here to move.");

                                }
                            } else {
                                System.out.println("No String found in the dragboard.");
                            }
                            event.setDropCompleted(success);
                            event.consume();
                        });
                        tile.setOnDragDetected(event -> {
                            Dragboard db = tile.startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent content = new ClipboardContent();
                            if (tile.getUnit() != null) {
                                if (tile.getUnit() instanceof Peasant) {
                                    content.putImage(pesantImage);
                                } else if (tile.getUnit() instanceof Castle) {
                                    content.putImage(castleImage);
                                } else if (tile.getUnit() instanceof Baron) {
                                    content.putImage(baronImage);
                                } else if (tile.getUnit() instanceof Capital) {
                                    content.putImage(capitalImage);
                                } else if (tile.getUnit() instanceof Knight) {
                                    content.putImage(knightImage);
                                } else if (tile.getUnit() instanceof Spearman) {
                                    content.putImage(spearmanImage);
                                } else {
                                    System.out.println("Unknown unit type.");
                                }
                            } else {
                                System.out.println("There are no units to move on this tile.");
                            }
                            content.putString(tile.getX() + ", " + tile.getY());
                            db.setContent(content);
                            event.consume();
                        });
                        tile.setOnDragDone(event -> {
                            if (event.getTransferMode() == TransferMode.MOVE) {
                                tile.setFill(fillColor);
                                //noinspection ConstantConditions
                                tile.setUnit(null);
                            }
                            event.consume();
                        });
                        tile.setOnMouseClicked(event -> {
                            Territory t = tile.getTerritory();
                            setSavings(t.getMoney());
                            setIncome(t.getNumTiles());
                            setWages(t.getWages());
                            setBalance((t.getMoney() + t.getNumTiles()) - t.getWages());
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
    
    public void setTileFill(Tile t) {

        int x = t.getX();
        int y = t.getY();

        if (t.getUnit() != null) {
            if (t.getUnit() instanceof Peasant) {
                t.setFill(new ImagePattern(pesantImage));
            } else if (t.getUnit() instanceof Castle) {
                t.setFill(new ImagePattern(castleImage));
            } else if (t.getUnit() instanceof Baron) {
                t.setFill(new ImagePattern(baronImage));
            } else if (t.getUnit() instanceof Capital) {
                t.setFill(new ImagePattern(capitalImage));
            } else if (t.getUnit() instanceof Knight) {
                t.setFill(new ImagePattern(knightImage));
            } else if (t.getUnit() instanceof Spearman) {
                t.setFill(new ImagePattern(spearmanImage));
            } else {
                Color c = t.getPlayer().getColor();
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int alpha = c.getAlpha();
                double opacity = alpha / 255.0 ;
                javafx.scene.paint.Color fillColor = javafx.scene.paint.Color.rgb(red, green, blue, opacity);
                t.setFill(fillColor);
            }

            map[y][x] = t;

        }

    }
    
    public void setTileFill(Tile t, Color c) {
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        int alpha = c.getAlpha();
        double opacity = alpha / 255.0 ;
        javafx.scene.paint.Color fillColor = javafx.scene.paint.Color.rgb(red, green, blue, opacity);
        t.setFill(fillColor);
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
