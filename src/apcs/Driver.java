/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

public class Driver {
    
    static Tile[][] map;
    
    public static void main(String[] args) {
        
        map = new Tile[5][5];
        GUI gui = new GUI(map);
        gui.displayGame();
        
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