package connect4;

import java.io.*;
import java.util.*;

class runGame{
    public static void main(String args[]) throws Exception
    {
        Board b; playerSearch p,k;
        b=new Board();
        p=new playerSearch();
        System.out.println("Welcome to the game of Connect4! What difficulty would you like the computer to have? Recommended: 3-6");
        BufferedReader move = new BufferedReader(new
                                                 InputStreamReader(System.in));
        
        p.setAi(Integer.parseInt(move.readLine()));
        System.out.println(b);
        System.out.println("What is your first move?");
        while((b.winnerIs()==0) && b.validMovesLeft())
        {
            
            if(b.cp == b.PLAYER_ONE)
                try{
                    b.makeMove(Integer.parseInt(move.readLine()));// Make it so!
                }
                catch(ArrayIndexOutOfBoundsException e)
                {
                    System.out.println("Index out of bounds. Please select another column!");
                    b.cp=b.PLAYER_ONE;
                }
            else if(b.cp==b.PLAYER_TWO){
                {
                    System.out.println("Computer is thinking. Please wait.");
                }
                b.makeMove(p.getMove(b));// Make it so!
                System.out.println(b);
                System.out.println("What is your next move?");
            }
        }
        
    }
}