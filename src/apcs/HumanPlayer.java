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
        super.playRound();
        while(!endTurn())
        {
            //needs to connect to GUI
        }
        
        //end stuff
        super.endOfTurnStuff();
//        for(int i = 0; i < players.length; i++) // this needs to access all the players to see if they have enough money to maintaine their territories.
//        {
//            
//        }
        
    }
    
    public boolean endTurn() //might need parameter to connect to GUI button
    {
        //connect to GUI
        return super.endTurn();
    }
}
