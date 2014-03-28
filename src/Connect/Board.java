

package Connect;

/**
 * The Board class contains everything having to do with the board:
 * initialization, connect lines, valid moves and the ability to make moves.
 * @author janhaeberle
 */


class Board {
    
    final static int PLAYER_ONE = 1;
    final static int PLAYER_TWO = -1;
    final static int EMPTY = 0;
    
    Point[][] grid;
    int[] heights;
    
    int cols;
    int rows;
    
    int[] moves;
    int lm;
    
    int cp;
    Point[][] cl;
    
    /*
    Initializes the board with empty points and generates the 'connectlines'.
    Sets the player that is to move first as player one.
    */
    Board()
    {
        cols=7;
        rows=6;
        //grid is an array containing the points of the board.
        grid=new Point[cols][rows];
        //heights contains the current height for each of the columns
        heights= new int[cols];
        //moves stores every move played. The maximum number of moves that can be
        //played is cols*rows.
        moves=new int[cols*rows];
        /**
         * Sets the last move to be -1 so that the first move will be stored in
         * the first index of moves.
         */
        lm=-1;
        /**
         * Generates the board with all empty points.
         */
        for(int x =0; x<cols;x++)
        {
            heights[x]=0;
            for(int y=0;y<rows;y++)
                grid[x][y]=new Point(x,y,EMPTY);
        }
        generateCL();
        cp=PLAYER_ONE;
    }
    /**
     * Generates the 'connectlines' cl[][]. This is an array of points where the
     * first index has an entry for every possible way of making a winning line
     * in connect4. The second index is used to indicate which point of the
     * connectline is held by which player. If a player has all four points
     * in a connectline, he wins.
     */
    void generateCL()
    {
        cl=new Point[69][4];        //cl=connectline
        int count=0;
        /**
         * Horizontal segments
         */
        for(int y=0;y<rows;y++)
        {
            for(int x=0;x<cols-3;x++)
                /**
                 * The sum only goes from 0 to 2 because the leftmost connectline
                 * starts at the index 2 and goes to the index 6. Same principle
                 * applies for the other directions.
                 */
            {
                Point[] temp = new Point[4];
                for(int i=x;i<x+4;i++)
                {
                    temp[i-x]=grid[i][y];
                }
                cl[count]=temp;
                count++;
            }
            
        }
        /**
         * Vertical segments
         */
        for(int x=0;x<cols;x++)
        {
            for(int y=0;y<rows-3;y++)
            {
                Point[] temp = new Point[4];
                for(int i=y;i<y+4;i++)
                    temp[i-y]=grid[x][i];
                cl[count]=temp;
                count++;
            }
            
        }
        /**
         * Diagonal segments
         */
        for(int x=0;x<cols-3;x++)
        {
            for(int y=0;y<rows-3;y++)
            {
                Point[] temp = new Point[4];
                for(int t=x,i=y;t<x+4 && i<y+4;t++,i++)
                    temp[i-y]=grid[t][i];
                cl[count]=temp;
                count++;
            }
            
        }
        for(int x=0;x<cols-3;x++)
        {
            for(int y=rows-1;y>rows-4;y--)
            {
                Point[] temp = new Point[4];
                for(int t=x,i=y;t<x+4 && i>-1;t++,i--)
                    temp[t-x]=grid[t][i];
                cl[count]=temp;
                count++;
            }
        }
    }
    /**
     * Returns true if a proposed move is playable.
     * @param column
     * @return
     */
    boolean validMove(int column)
    {
        return heights[column]<rows;
    }
    /*
    Does all the operations neccessary when placing a stone in a column.
    */
    void makeMove(int column)
    {
        /*
        heights[] stores the information about how high each column is currently.
        The point on the grid will be set to the current player.
        */
        grid[column][heights[column]].setState(cp);
        /*
        Increases the heights value for that column so that the next stone will
        be placed in the row above this.
        */
        heights[column]++;
        lm++;
        moves[lm]=column;   //The column in which the last stone was played is stored.
        cp=-cp;             //The current player switches.
    }
    /**
     * undoes all the actions of makeMove()
     */
    void undoMove()
    {
        
        grid[moves[lm]][heights[moves[lm]]-1].setState(EMPTY);
        heights[moves[lm]]--;
        lm--;
        cp=-cp;
    }
    
    /**
     * Returns true if there are empty points on the board.
     */
    boolean validMovesLeft()
    {
        return lm<moves.length-1;
    }
    
    /**
     * Returns the winning player that is determined using getScore()
     */
    
    int winnerIs()
    {
        int clScore;
        for(int i=0;i<cl.length;i++)
        {
            clScore=getScore(cl[i]);
            if(clScore==4)
            {
                return PLAYER_ONE;
            }
            else if(clScore==-4)
            {
                return PLAYER_TWO;
            }
        }
        return 0;
        
    }
    /**
     * Returns how many stones a player has in a 1d array of points (usually a cl).
     * Returns 0 if both players have a stone in the array.
     * @param points
     * @return
     */
    private int getScore(Point[] points) {
        int playerone=0;
        int playertwo=0;
        for(int i=0;i<points.length;i++)
        {
            if(points[i].getState()==Board.PLAYER_ONE)
            {
                playerone++;
            }
            else if(points[i].getState()==Board.PLAYER_TWO)
            {
                playertwo++;
            }
        }
        if(playerone>0 && (playertwo==0))
        {
            //The score is returned as positive for player one and negative for player two (convention)
            return playerone;
        }
        if(playertwo>0 && (playerone==0))
        {
            return -playertwo;
        }
        return 0;
    }
    
    /**
     * Determines the strength of a position based on the number of uncontested tokens
     * in a connectline weighed by the weights[]. A uncontested cl is more valuable
     * the more tokens a player has in it. The right to move next is weighed as 16.
     * @return
     */
    int getStrength()
    {
        int sum=0;
        int[] weights = {0,1,10,50,600};
        for(int i=0;i<cl.length;i++)
        {
            int thisScore=getScore(cl[i]);
            if(thisScore>0)
            {
                sum+=weights[Math.abs(thisScore)];
            }
            if(thisScore<0)
            {
                sum+=-weights[Math.abs(thisScore)];
            }
        }
        //The right to move is worth 16:
        return sum+(cp==PLAYER_ONE?16:-16);
    }
    /**
     * Prints the board.
     * @return 
     */
    public String toString()
    {
        String temp = "";
        temp+="1234567\n";
        for(int y=rows-1;y>-1;y--){
            for(int x=0;x<cols;x++)
                if(grid[x][y].getState()==EMPTY)
                    temp = temp + "-";
                else if(grid[x][y].getState()==PLAYER_ONE)
                    temp = temp + "O";
                else
                    temp = temp + "X";
            temp += "\n";
        }
        temp+="1234567\n";
        return temp;
    }
    
    
}
