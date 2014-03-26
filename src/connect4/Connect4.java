

package gm;

import java.io.*;
import java.util.*;
class runGame {
    
    public static void main(String args[]) throws Exception
    {
        Board b; playerSearch p,k;
        b=new Board(7,6);
        p=new playerSearch();
        k=new playerSearch();
        
        BufferedReader move = new BufferedReader(new
         InputStreamReader(System.in));
        
        while((b.winnerIs()==0) && b.validMovesLeft())
        {
            if(b.cp == b.PLAYER_ONE)
                
                b.makeMove(Integer.parseInt(move.readLine()));// Make it so!
            else
                b.makeMove(p.getMove(b));// Make it so!
            System.out.println(b);
            
        }
        
    }
}



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
    
    
    Board(int columns, int inrows)
    {
        cols=columns;
        rows=inrows;
        grid=new Point[cols][rows];
        heights= new int[cols];
        moves=new int[cols*rows];
        lm=-1;
        for(int x =0; x<cols;x++)
        {
            heights[x]=0;
            for(int y=0;y<rows;y++)
                grid[x][y]=new Point(x,y,EMPTY);
            
        }
        generateCL();
        cp=PLAYER_ONE;
    }
    
    void generateCL()
    {
        cl=new Point[69][4];
        int count=0;
// horz segs
        for(int y=0;y<rows;y++)
        {
            for(int x=0;x<cols-3;x++)
            {
                Point[] temp = new Point[4];
                for(int i=x;i<x+4;i++)
                    temp[i-x]=grid[i][y];
                cl[count]=temp;
                count++;
            }
            
        }
        
// vert segs
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
        
// diag segs
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
    boolean validMove(int column)
    {
        return heights[column]<rows;
    }
    
    void makeMove(int column)
    {
        grid[column][heights[column]].setState(cp);
        heights[column]++;
        lm++;
        moves[lm]=column;
        cp=-cp;
    }
    
    void undoMove()
    {
        
        grid[moves[lm]][heights[moves[lm]]-1].setState(EMPTY);
        heights[moves[lm]]--;
        lm--;
        cp=-cp;
    }
    
    
    boolean validMovesLeft()
    {
        return lm<moves.length-1;
    }
    
    
    int winnerIs()
    {
        for(int i=0;i<cl.length;i++)
            if(getScore(cl[i])==4)
            {
                return PLAYER_ONE;
            }
            else if(getScore(cl[i])==-4)
                return PLAYER_TWO;
        return 0;
        
    }
    
    private int getScore(Point[] points) {
        int playerone=0;
        int playertwo=0;
        for(int i=0;i<points.length;i++)
            if(points[i].getState()==Board.PLAYER_ONE)
                playerone++;
            else if(points[i].getState()==Board.PLAYER_TWO)
                playertwo++;
        if((playerone+playertwo>0) && (!(playerone>0 && playertwo>0)))      //Returns yes if only one player has at least one token in this line.
        {
            return (playerone!=0)?playerone:playertwo;                      //Returns the amount of uncontested tokens in that line.
        }
        else
            return 0;
    }
    
    
    int getStrength()
    {
        int sum=0;
        int[] weights = {0,1,10,50,600};
        for(int i=0;i<cl.length;i++)
        {
            sum+=(getScore(cl[i])>0)?weights[Math.abs(getScore(cl[i]))]:-weights[Math.abs(getScore(cl[i]))];
        }
        return sum+(cp==PLAYER_ONE?16:-16);
    }
    
    public String toString()
    {
        String temp = "";
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
        return temp;
    }
    
    
}


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








class playerSearch {
    
// should return a number
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
//System.out.print(moves[i] + " ");
        }
//System.out.println();
        return moves[lowest];
        
    }
//careful with this
    int maxValue(Board cB, int ply)
    {
//System.out.print("Max" + ply + ": ");
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
//System.out.print(moves[i] + " ");
        }
//System.out.println();
        return moves[highest];
        
    }
    
    
}



class Point
{
    // This code was generated by a tool.
    int x;
    int y;
    int state;
    
    Point(int xt, int yt, int statet)
    {
        x=xt;
        y=yt;
        state=statet;
    }
    
    boolean equalsPosition(Point q)
    {
        return x==q.x && y==q.y;
    }
    
    void setState(int player)
    {
        state=player;
    }
    
    int getState()
    {
        return state;
    }
    
}
