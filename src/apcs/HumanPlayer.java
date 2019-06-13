package apcs;

import java.awt.Color;


public class HumanPlayer extends Player
{
    private boolean endTurn;
    
    public HumanPlayer(Color c)
    {
        super(c);
    }

    public void playRound()
    {
        super.playRound();
        endTurn = false;
        while(!endTurn)
        {
            System.out.println("running HP");
        }
        
        
        
    }
    public void buttonEndTurn(boolean b)
    {
        endTurn = b;
        System.out.println("endTurn: " + endTurn);
    }
   
}