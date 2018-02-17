import Helpers.Move;
import Helpers.PawnPosition;

import java.util.ArrayList;
import java.util.HashSet;

public class State
{
    private HashSet<PawnPosition> black;
    private HashSet<PawnPosition> white;
    private HashSet<State> successorStates;
    private ArrayList<Move> legalMoves;

    public State()
    {
        black = new HashSet<>();
        white = new HashSet<>();
        successorStates = new HashSet<>();
        legalMoves = new ArrayList<>();
    }

    public State(HashSet<PawnPosition> black, HashSet<PawnPosition> white)
    {
        this.black = black;
        this.white = white;
        successorStates = new HashSet<>();
        legalMoves = new ArrayList<>();
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

    public ArrayList<Move> getLegalMoves(int width, String role) {
        if(role.equals("white"))
            return getWhiteLegalMoves(width);
        else if(role.equals("black"))
            return getBlackLegalMoves(width);
        return null;
    }

    public HashSet<State> createLegalStates(int width, int height, String role)
    {
        if(role.equals("white"))
        {
            return getWhiteMoves(width);
        }
        else if(role.equals("black"))
        {
            return getBlackMoves(width);
        }
        return null;
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

    private HashSet<State> getWhiteMoves(int width)
    {
        for(PawnPosition p : white)
        {
            if(white.contains(new PawnPosition(p.getX(), p.getY() + 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move white in front");
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
        return successorStates;
    }

    private HashSet<State> getBlackMoves(int width)
    {
        for(PawnPosition p : black)
        {
            if(black.contains(new PawnPosition(p.getX(), p.getY() - 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(white.contains(new PawnPosition(p.getX(), p.getY() - 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move white in front");
            }
            else if(p.getX() != width && white.contains(new PawnPosition(p.getX() + 1, p.getY() - 1)))
            {
                HashSet<PawnPosition> newWhite = getWhiteCopy();
                HashSet<PawnPosition> newBlack = getBlackCopy();
                newBlack.remove(p);
                newBlack.add(new PawnPosition(p.getX() + 1, p.getY() - 1));
                newWhite.remove(new PawnPosition(p.getX() + 1, p.getY() - 1));
                successorStates.add(new State(newBlack, newWhite));
            }
            else if(p.getX() != 1 && white.contains(new PawnPosition(p.getX() - 1, p.getY() - 1)))
            {
                HashSet<PawnPosition> newWhite = getWhiteCopy();
                HashSet<PawnPosition> newBlack = getBlackCopy();
                newBlack.remove(p);
                newBlack.add(new PawnPosition(p.getX() - 1, p.getY() - 1));
                newWhite.remove(new PawnPosition(p.getX() - 1, p.getY() - 1));
                successorStates.add(new State(newBlack, newWhite));
            }
            else
            {
                HashSet<PawnPosition> newWhite = getWhiteCopy();
                HashSet<PawnPosition> newBlack = getBlackCopy();
                newBlack.remove(p);
                newBlack.add(new PawnPosition(p.getX(), p.getY() - 1));
                successorStates.add(new State(newBlack, newWhite));
            }
        }
        return successorStates;
    }

    private ArrayList<Move> getWhiteLegalMoves(int width)
    {
        for(PawnPosition p : white)
        {
            if(white.contains(new PawnPosition(p.getX(), p.getY() + 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move white in front");
            }
            else if(black.contains(new PawnPosition(p.getX(), p.getY() + 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(p.getX() != width && black.contains(new PawnPosition(p.getX() + 1, p.getY() + 1)))
            {
                legalMoves.add(new Move(new PawnPosition(p.getX(), p.getY()), new PawnPosition(p.getX() + 1, p.getY() + 1)));
            }
            else if(p.getX() != 1 && black.contains(new PawnPosition(p.getX() - 1, p.getY() + 1)))
            {
                legalMoves.add(new Move(new PawnPosition(p.getX(), p.getY()), new PawnPosition(p.getX() - 1, p.getY() + 1)));
            }
            else
            {
                legalMoves.add(new Move(new PawnPosition(p.getX(), p.getY()), new PawnPosition(p.getX(), p.getY() + 1)));
            }
        }
        return legalMoves;
    }

    private ArrayList<Move> getBlackLegalMoves(int width)
    {
        for(PawnPosition p : black)
        {
            if(black.contains(new PawnPosition(p.getX(), p.getY() - 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(white.contains(new PawnPosition(p.getX(), p.getY() - 1)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move white in front");
            }
            else if(p.getX() != width && white.contains(new PawnPosition(p.getX() + 1, p.getY() - 1)))
            {
                legalMoves.add(new Move(new PawnPosition(p.getX(), p.getY()), new PawnPosition(p.getX() + 1, p.getY() - 1)));
            }
            else if(p.getX() != 1 && white.contains(new PawnPosition(p.getX() - 1, p.getY() - 1)))
            {
                legalMoves.add(new Move(new PawnPosition(p.getX(), p.getY()), new PawnPosition(p.getX() - 1, p.getY() - 1)));
            }
            else
            {
                legalMoves.add(new Move(new PawnPosition(p.getX(), p.getY()), new PawnPosition(p.getX(), p.getY() - 1)));
            }
        }
        return legalMoves;
    }

    public State getStateByAction(Move move)
    {
        HashSet<PawnPosition> newWhite = getWhiteCopy();
        HashSet<PawnPosition> newBlack = getBlackCopy();
        if(black.contains(move.getFrom()))
        {
            newBlack.remove(move.getFrom());
            newBlack.add(move.getTo());
            if(move.getFrom().getX() != move.getTo().getX())
            {
                newWhite.remove(move.getTo());
            }
        }
        else if(white.contains(move.getFrom()))
        {
            newWhite.remove(move.getFrom());
            newWhite.add(move.getTo());
            if(move.getFrom().getX() != move.getTo().getX())
            {
                newBlack.remove(move.getTo());
            }
        }
        return new State(newBlack, newWhite);
    }
}
