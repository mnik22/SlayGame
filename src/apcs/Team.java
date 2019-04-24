/*Name:	
 *Date:
 *Period:
 *Teacher:
 *Description:
 */
package apcs;

import java.awt.Color;
import java.util.ArrayList;


public class Team
{
    private Color color;
    
    ArrayList<Territory> territories = new ArrayList<Territory>();
    
    public Team(Color c, AI a)
    {
        color = c;
        
    }
    
    public Team(Color c, Player p)
    {
        color = c;
    }
    
    public void addTerritory(Territory t)
    {
        territories.add(t);
    }
    
    public void removeTerritory(Territory t) //up for change
    {
        for(int i = 0; i < territories.size(); i++)
        {
            if(territories.get(i).equals(t))
            {
                territories.remove(i);
            }
        }
    }
    
    
}
