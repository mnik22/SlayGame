/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener {

    /** Height of the game frame. */
    private static final int DEFAULT_HEIGHT = 302;
    /** Width of the game frame. */
    private static final int DEFAULT_WIDTH = 800;
    
    private JPanel panel;
    private Point[] tiles;
    
    private Tile[][] map;
    
    public GUI(Tile[][] map) {
        
        this.map = map;
        initDisplay();
        
    }
    
    public void displayGame() {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                
                setVisible(true);
                
            }
            
        });
        
    }
    
    private void initDisplay() {
        
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        
    }

}