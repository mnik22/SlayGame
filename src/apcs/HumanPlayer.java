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
        for(int i = 0; i < Driver.players.length; i++) 
        {
            Driver.players[i].checkEndOfTurnStatus();
        }
        
    }
    
    public boolean endTurn() 
    {
        return super.endTurn();
    }
}