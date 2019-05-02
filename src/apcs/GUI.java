/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI {

    private JFrame frame;
    private Point[] tiles;
    private BufferedImage background;
    
    private Tile[][] map;
    
    public GUI(Tile[][] map) {
        
        this.map = map;
        initDisplay();
        
    }
    
    public void displayGame() {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                
                frame.setVisible(true);
                
            }
            
        });
        
    }
    
    private void initDisplay() {
        
        frame = new JFrame("Slay Game");
        
        try {
            
            background = ImageIO.read(new File("background.png"));
            
        } catch (IOException e) {
            
            e.printStackTrace();
            
        }

        frame.setContentPane(new JLabel(new ImageIcon(background)));
        frame.setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        frame.pack();
        
    }

}