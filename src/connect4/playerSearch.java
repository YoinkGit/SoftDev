

package connect4;

/**
 *
 * @author janhaeberle
 */
class playerSearch {
    int aiStrength=4;
    /**
     * This is called to tell the computer to find his next move given the state of the board.
     * The computer then tests all possible next aiStrength moves with and returns the best he finds.
     *
     * @param cB
     * @return
     */
    
    int getMove(Board cB)
    {
        int[] moves = new int[7];
        int highest = 0;
        
        for(int i=0;i<7;i++)
        {
            /**
             * -3450 is the lowest getStrengh() value that would be achieved
             * if a person had exactly 3 in each cl without winning. -5000 is
             * therefore a lower bound for the lowest move that can be made.
             */
            moves[i] = -5000;
            if(cB.validMove(i))
            {
                cB.makeMove(i);
                moves[i] = minValue(cB,aiStrength);
                if(moves[i]>=moves[highest])
                {
                    highest=i;
                }
                cB.undoMove();
            }
        }
        return highest;
    }
// don't change this unless you understand it
    int minValue(Board cB, int ply)
    {
        int[] moves = new int[7];
        int lowest = 0;
        for(int i=0;i<7;i++)
        {
            moves[i] = Integer.MAX_VALUE;
            if(cB.validMove(i))
            {
                cB.makeMove(i);
                if((cB.winnerIs() == 0) && ply>0)
                {
                    moves[i] = maxValue(cB,ply-1);
                }
                else
                {
                    
                    moves[i] = -cB.getStrength();
                }
                if(moves[i]<moves[lowest])
                    lowest=i;
                cB.undoMove();
            }
        }
        return moves[lowest];
        
    }
    int maxValue(Board cB, int ply)
    {
        int[] moves = new int[7];
        int highest = 0;
        for(int i=0;i<7;i++)
        {
            moves[i] = Integer.MAX_VALUE;
            if(cB.validMove(i))
            {
                cB.makeMove(i);
                if((cB.winnerIs() == 0) && ply>0)
                {
                    moves[i] = minValue(cB,ply-1);
                }
                else
                    moves[i] =-cB.getStrength();
                if(moves[i]<moves[highest])
                    highest=i;
                cB.undoMove();
            }
        }
        return moves[highest];
        
    }
    void setAi(int strength){
        aiStrength=strength;
    }
    
    
}

