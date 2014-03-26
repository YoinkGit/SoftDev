
package connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author janhaeberle
 */
class playerRand {
    private Random rand = new Random();
    int getMove(Board cB)
    {
        List<Integer> poss= new ArrayList<Integer>();
        for(int i = 0; i <7; i++)
        {
            if(cB.validMove(i))
            {
                poss.add(i);
            }
        }
        return poss.get(Math.abs(rand.nextInt(poss.size())));
    }
}