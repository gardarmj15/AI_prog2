import Helpers.Point2D;

import java.util.ArrayList;

public class State
{
    private ArrayList<Point2D> whitePawns;
    private ArrayList<Point2D> blackPawns;
    private String role;

    public State()
    {
        whitePawns = new ArrayList<>();
        blackPawns = new ArrayList<>();
        role = "white";
    }

    public void addToLists(int w, int h)
    {
        for(int x = 1; x <= w; x++)
        {
            for(int y = 1; y <= 2; y++)
            {
                whitePawns.add(new Point2D(x,y));
            }
        }
        for(int x = 1; x <= w; x++)
        {
            for(int y = h; y > w; y--)
            {
                blackPawns.add(new Point2D(x,y));
            }
        }
    }
    void moveForward(int x, int y){
        System.out.println("move forward");
        for(Point2D p : blackPawns){
            if(p.getX() == x && p.getY()== y){
                p.setY(y + 1);
            }
        }
    }
    void moveDiagonally(int x, int y, Boolean right){
        if(right){
            System.out.println("move right");
            for(Point2D p : blackPawns){
                if(p.getX() == x && p.getY()== y){
                    p.setY(y - 1);
                    p.setX(x - 1);
                }
            }
        }
        else{
            System.out.println("move left");
            for(Point2D p : blackPawns){
                if(p.getX() == x && p.getY()== y){
                    p.setY(y - 1);
                    p.setX(x + 1);
                }
            }
        }
    }

    public ArrayList<Point2D> getBlackPawns() {
        return blackPawns;
    }

    public ArrayList<Point2D> getWhitePawns() {
        return whitePawns;
    }
}
