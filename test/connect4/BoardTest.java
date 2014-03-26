/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package connect4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author janhaeberle
 */
public class BoardTest {
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of generateCL method, of class Board.
     */
    @Test
    public void testGenerateCL() {
        System.out.println("generateCL");
        Board instance = new Board();
        instance.generateCL();
        int expResult=0;
        int result=0;
        for(int i=0;i<69;i++){
            for(int j=0;j<4;j++){
                result+=instance.cl[i][j].getState();
            }
        }
        assertEquals(result,expResult);
    }
    
    /**
     * Test of validMove method, of class Board.
     */
    @Test
    public void testValidMove() {
        System.out.println("validMove");
        int column = 2;
        Board instance = new Board();
        boolean expResult = true;
        instance.makeMove(column);
        //There is only one token in column with index 2, it should therefore be allowed to place more here
        boolean result = instance.validMove(column);
        assertEquals(expResult, result);
        instance.makeMove(column);
        instance.makeMove(column);
        instance.makeMove(column);
        instance.makeMove(column);
        instance.makeMove(column);
        //There are already 6 tokens in column with index 2. Therefore, this move should not be valid.
        expResult=false;
        result=instance.validMove(column);
        assertEquals(expResult,result);
        //Placing a token somewhere else, however, should still be legal.
        expResult=true;
        result=instance.validMove(column+1);
        assertEquals(expResult,result);
        
    }
    
    /**
     * Test of makeMove method, of class Board.
     * Makes a move and then checks its state. Does this twice for both players
     */
    @Test
    public void testMakeMove() {
        System.out.println("makeMove");
        int column = 3;
        Board instance = new Board();
        instance.makeMove(column);
        int expResult=1;
        int result=instance.grid[3][0].getState();
        assertEquals(expResult,result);
        instance.makeMove(column);
        expResult=-1;
        result=instance.grid[3][1].getState();
        assertEquals(expResult,result);
    }
    
    /**
     * Test of undoMove method, of class Board.
     * Makes a move, then undoes it and tests if it worked.
     */
    @Test
    public void testUndoMove() {
        System.out.println("undoMove");
        Board instance = new Board();
        instance.makeMove(3);
        instance.undoMove();
        assertEquals(instance.heights[3],0);
    }
    
    /**
     * Test of validMovesLeft method, of class Board.
     * Tests if there are any moves left in the very beginning.
     * Then fills the board completely and tests again.
     */
    @Test
    public void testValidMovesLeft() {
        System.out.println("validMovesLeft");
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.validMovesLeft();
        assertEquals(expResult, result);
        for(int i=0;i<7;i++){
            for(int j=0;j<6;j++){
                instance.makeMove(i);
            }
        }
        expResult=false;
        result=instance.validMovesLeft();
        assertEquals(expResult,result);
    }
    
    /**
     * Test of winnerIs method, of class Board.
     * Tests after just one move and finds that no one has won yet.
     * Makes moves so that player one gets 4 in a row in the first column and tests again.
     */
    @Test
    public void testWinnerIs() {
        System.out.println("winnerIs");
        Board instance = new Board();
        instance.makeMove(0);
        int expResult = 0;
        int result = instance.winnerIs();
        assertEquals(expResult, result);
        instance.makeMove(1);
        instance.makeMove(0);
        instance.makeMove(1);
        instance.makeMove(0);
        instance.makeMove(1);
        instance.makeMove(0);
        expResult=instance.PLAYER_ONE;
        result=instance.winnerIs();
        assertEquals(expResult,result);
    }
    
    /**
     * Test of getStrength method, of class Board.
     */
    @Test
    public void testGetStrength() {
        System.out.println("getStrength");
        Board instance = new Board();
        playerSearch s=new playerSearch();
        instance.makeMove(0);
        //The token should be part of three possible cl, but it's player twos turn, so:
        int expResult=-13;
        int result = instance.getStrength();
        assertEquals(expResult, result);
        instance.makeMove(3);
        //This token will be part of 6 possible cl and denies one of player ones options.
        //It is player ones move after this again and therefore: (2-6+16)
        expResult=12;
        result=instance.getStrength();
        assertEquals(expResult,result);
    }
    
    /**
     * Test of toString method was removed because the output can best be interpreted by eye.
     */
    
    
    
}
