/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;
import java.util.ArrayList;


public class Player
{
    private Team team;
    
    public Player(Color c)
    {
        team = new Team(c);
    }

    public boolean hasNextPlay()
    {
        ArrayList<Territory> territories = team.getTerritories(); 
        for(Territory t: territories)
        {
            if(t.canPurchaseUnits() || t.canMoveUnit())
                return true;
        }
        return false;
    }

    public void playRound()
    {
        
    }

}
