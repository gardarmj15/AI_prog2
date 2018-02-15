import Helpers.PawnPosition;

import java.util.ArrayList;
import java.util.HashSet;

public class State
{
    private HashSet<PawnPosition> black;
    private HashSet<PawnPosition> white;
    private HashSet<State> successorStates;

    public State()
    {
        black = new HashSet<>();
        white = new HashSet<>();
        successorStates = new HashSet<>();
    }

    public State(HashSet<PawnPosition> black, HashSet<PawnPosition> white)
    {

    }

    public void addToLists(int w, int h)
    {
        for(int x = 1; x <= w; x++)
        {
            for(int y = 1; y <= 2; y++)
            {
                white.add(new PawnPosition(x,y));
            }
        }
        for(int x = 1; x <= w; x++)
        {
            for(int y = h; y > w; y--)
            {
                black.add(new PawnPosition(x,y));
            }
        }
    }
    void moveForward(int x, int y){
        System.out.println("move forward");
        for(PawnPosition p : white){
            if(p.getX() == x && p.getY()== y){
                p = new PawnPosition(x, y + 1);
            }
            else if(p.getX() == x && p.getY()== y){
                p = new PawnPosition(x, y + 1);
            }
        }
    }
    void moveDiagonally(int x, int y, Boolean right){
        if(right){
            System.out.println("move right");
            for(PawnPosition p : white){
                if(p.getX() == x && p.getY()== y){
                    p.setY(y - 1);
                    p.setX(x - 1);
                }
            }
        }
        else{
            System.out.println("move left");
            for(PawnPosition p : white){
                if(p.getX() == x && p.getY()== y){
                    p.setY(y - 1);
                    p.setX(x + 1);
                }
            }
        }
    }

    public HashSet<PawnPosition> getWhitePawns() {
        return white;
    }

    public HashSet<PawnPosition> getBlackPawns() {
        return black;
    }

    public void findLegalMove(int width, int height)
    {
        for(PawnPosition p : white)
        {
            if(white.contains(new PawnPosition(p.getX(), p.getY() + 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(black.contains(new PawnPosition(p.getX(), p.getY() + 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(p.getX() != width && black.contains(new PawnPosition(p.getX() + 1, p.getY() + 1)))
            {
                HashSet<PawnPosition> newWhite = getWhiteCopy();
                HashSet<PawnPosition> newBlack = getBlackCopy();
                newWhite.remove(p);
                newWhite.add(new PawnPosition(p.getX() + 1, p.getY() + 1));
                newBlack.remove(new PawnPosition(p.getX() + 1, p.getY() + 1));
                successorStates.add(new State(newBlack, newWhite));
            }
            else if(p.getX() != 1 && black.contains(new PawnPosition(p.getX() - 1, p.getY() + 1)))
            {
                HashSet<PawnPosition> newWhite = getWhiteCopy();
                HashSet<PawnPosition> newBlack = getBlackCopy();
                newWhite.remove(p);
                newWhite.add(new PawnPosition(p.getX() - 1, p.getY() + 1));
                newBlack.remove(new PawnPosition(p.getX() - 1, p.getY() + 1));
                successorStates.add(new State(newBlack, newWhite));
            }
            else
            {
                HashSet<PawnPosition> newWhite = getWhiteCopy();
                HashSet<PawnPosition> newBlack = getBlackCopy();
                newWhite.remove(p);
                newWhite.add(new PawnPosition(p.getX(), p.getY() + 1));
                successorStates.add(new State(newBlack, newWhite));
            }
        }
    }

    private HashSet<PawnPosition> getWhiteCopy()
    {
        HashSet<PawnPosition> newWhite = new HashSet<>();
        newWhite.addAll(white);
        return newWhite;
    }

    private HashSet<PawnPosition> getBlackCopy()
    {
        HashSet<PawnPosition> newBlack = new HashSet<>();
        newBlack.addAll(white);
        return newBlack;
    }
}
