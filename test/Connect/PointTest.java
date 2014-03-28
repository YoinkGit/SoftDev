/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect;

import Connect.Point;
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
public class PointTest {
    /**
     * Test of equalsPosition method, of class Point.
     * Creates two Points with different state and different position. The equalsPosition method should then be false.
     * Then creates a third point that has the same coordinates but a different state as the Point that is being tested against.
     * The positions are equal so equalsPosition should be true.
     */
    @Test
    public void testEqualsPosition() {
        System.out.println("equalsPosition");
        Point q = new Point(0,1,1);
        Point instance = new Point(0,0,0);
        boolean expResult = false;
        boolean result = instance.equalsPosition(q);
        assertEquals(expResult, result);
        Point k = new Point(0,0,1);
        expResult=true;
        result=instance.equalsPosition(k);
        assertEquals(expResult, result);
    }

    /**
     * Test of setState method, of class Point.
     * Creates a point in state 0. Then sets the state to 1 and tests its state afterwards.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        int state = 1;
        Point instance = new Point(0,0,0);
        instance.setState(state);
        int expResult=1;
        int result=instance.getState();
        assertEquals(result,expResult);
    }

    /**
     * Test of getState method, of class Point.
     * Creates a point and tests its state.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        Point instance = new Point(2,4,1);
        int expResult = 1;
        int result = instance.getState();
        assertEquals(expResult, result);
        
    }
    
}
