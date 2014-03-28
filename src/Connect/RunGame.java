package Connect;

import java.io.*;
import java.util.*;

class RunGame{
    public static void main(String args[]) throws Exception
    {
        //The next lines initiate the board and the AI.
        Board b;
        PlayerSearch p;
        b=new Board();
        p=new PlayerSearch();
        //nextGame is used to determine if a new game is played after a game has finished.
        Boolean nextGame=true;
        //The variable fail is used in the reader so that the user has the chance to do another input if there was an exception thrown.
        Boolean fail=true;
        //Scores are kept using these variables.
        int computerScore=0;
        int humanScore=0;
        /**
         * Set the difficulty of the computer by command line input.
         */
        System.out.println("Welcome to the game of Connect4! What difficulty would you like the computer to have? Recommended: 3-5");
        BufferedReader move = new BufferedReader(new InputStreamReader(System.in));
        while(fail){
            try{
                int userInput=Integer.parseInt(move.readLine());
                if(userInput>9){throw new IndexOutOfBoundsException("This would take too long. Please type a positive integer smaller 10.");}
                if(userInput<0){throw new IndexOutOfBoundsException("Please type a positive integer smaller 10.");}
                p.setAiStrength(userInput);
                fail=false;
            }
            catch(NumberFormatException e){
                System.out.println("Please type only integer numbers.");
            }
            catch(IndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }
        }
        
        System.out.println(b);
        System.out.println("What is your first move?");
        
        while(nextGame){
            while((b.winnerIs()==0) && b.validMovesLeft())
            {
                if(b.lm!=-1){System.out.println("What is your next move?");}
                if(b.cp == b.PLAYER_ONE)
                {
                    /*
                    The move that is entered is attempted and if it fails, the exceptions are thrown.
                    The user is then asked to enter a different move.
                    */
                    try{
                        b.makeMove(Integer.parseInt(move.readLine())-1);
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("Index out of bounds. Please select another column!");
                        b.cp=b.PLAYER_ONE;
                    }
                    catch(NumberFormatException e){
                        System.out.println("Please type only integer numbers.");
                        b.cp=b.PLAYER_ONE;
                    }
                }
                /*
                The computer move is evaluated and then executed.
                */
                if(b.cp==b.PLAYER_TWO)
                {
                    System.out.println("Computer is thinking. Please wait.");
                    
                    b.makeMove(p.getMove(b));
                    System.out.println(b);
                }
            }
            /*
            If a winner is decided, the appropriate message is sent and the user is asked if he wants a rematch.
            */
            if(b.winnerIs()==1){
                humanScore++;
            System.out.println("Congratulations! You win!\n"
                    +"That makes " + humanScore +":"+computerScore
                    + "\nPlay another game? (y/n)");}
            if(b.winnerIs()==-1)
            {
                computerScore++;
                System.out.println("The computer defeated you!\n"
                    + "That makes " + humanScore +":"+computerScore
                    + "\nPlay another game? (y/n)");
            }
            if(b.winnerIs()==0 && !b.validMovesLeft()){
                System.out.println("It's a draw!\n"
                    + "That makes " + humanScore +":"+computerScore
                    + "\nPlay another game? (y/n)");
            }
            //If The user types "y", the game restarts but the score is kept. If he types anything else, the program terminates.
            try{
                String newGame=move.readLine();
                if(newGame.equals("y")){
                    nextGame=true;
                    b=new Board();
                    System.out.println(b);
                    System.out.println("What is your first move?");
                    System.out.println(newGame);
                }
                else{
                    nextGame=false;
                }
            }
            catch(Exception a){
                
            }
        }
    }
}