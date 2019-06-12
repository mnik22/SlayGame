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
        while(!endTurn());
        
        
        
    }
    
    public boolean endTurn() 
    {
        return super.endTurn();
    }
}