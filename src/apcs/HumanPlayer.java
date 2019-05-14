/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;


public class HumanPlayer extends Player
{
    public HumanPlayer(Color c)
    {
        super(c);
    }

    public void playRound()
    {
        while(!endTurn())
        {
            //needs to connect to GUI
        }
        
    }
    
    public boolean endTurn() //might need parameter to connect to GUI button
    {
        if(!hasNextPlay()) //needs other case to consider an end turn button
            return true;
        return false;
    }
}
