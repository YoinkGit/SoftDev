/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect;

import Connect.PlayerSearch;
import Connect.Board;
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
public class playerSearchTest {
    

    /**
     * Test of getMove method, of class playerSearch.
     */
    @Test
    public void testGetMove() {
        System.out.println("getMove");
        Board cB = new Board();
        PlayerSearch instance = new PlayerSearch();
        cB.makeMove(0);
        cB.makeMove(1);
        cB.makeMove(0);
        cB.makeMove(1);
        cB.makeMove(0);
        cB.makeMove(1);
        //Player one can win next turn. Will the computer prevent it?
        int expResult = 0;
        int result = instance.getMove(cB);
        assertEquals(expResult, result);
        cB.makeMove(4);
        //The computer can win by playing column 1.
        expResult=1;
        result=instance.getMove(cB);
        assertEquals(expResult, result);
    }

}
