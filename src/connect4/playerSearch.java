/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

/**
 *
 * @author janhaeberle
 */
class playerSearch {
    
    int getMove(Board cB)
    {
        int[] moves = new int[7];
        int highest = 0;
        for(int i=0;i<7;i++)
        {
            moves[i] = Integer.MIN_VALUE;
            if(cB.validMove(i))
            {
                cB.makeMove(i);
                moves[i] = minValue(cB,6);
                if(moves[i]>=moves[highest])
                    highest=i;
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
    
    
}

