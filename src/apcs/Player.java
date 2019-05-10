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
    private Team t;
    
    public Player(Color c)
    {
        t = new Team(c);
    }
    public boolean hasNextPlay()
    {
        ArrayList<Territory> territories = t.getTerritories(); //needs to be more efficient
        for(Territory terr: territories)
        {
            if(((terr.availbleUnits())[0]>0) || ((terr.availbleUnits())[1]>0))
                return true;
        }
        return false;
    }

    public void playRound()
    {
        
    }

}
