
package Connect;

/**
 * This class is used to store the state of each point on the board.
 * @author janhaeberle
 */
class Point
{
    int x;
    int y;
    int state;
    
    Point(int xt, int yt, int statet)
    {
        x=xt;
        y=yt;
        state=statet;
    }
    /**
     * Compares the x and y coordinates of two points and returns true if they are the same.
     * @param q
     * @return 
     */
    boolean equalsPosition(Point q)
    {
        return x==q.x && y==q.y;
    }
    /**
     * The state of a point can be set to represent which player occupies it. 
     * @param player 
     */
    void setState(int player)
    {
        state=player;
    }
    /**
     * returns the state of the point as an integer.
     * @return 
     */
    int getState()
    {
        return state;
    }
    
}