import Helpers.Environment;
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
    private Environment env;
    private String currentPlayer;
    private int score;

    public State(Environment env)
    {
        currentPlayer = "white";
        black = new HashSet<>();
        white = new HashSet<>();
        successorStates = new HashSet<>();
        legalMoves = new ArrayList<>();
        this.env = env;
    }

    public State(HashSet<PawnPosition> black, HashSet<PawnPosition> white, Environment env, String currentPlayer)
    {
        if(currentPlayer.equals("white"))
            this.currentPlayer = "black";
        else
            this.currentPlayer = "white";
        this.black = black;
        this.white = white;
        successorStates = new HashSet<>();
        legalMoves = new ArrayList<>();
        this.env = env;
        evaluateScore(currentPlayer);
    }

    public void addToLists()
    {
        for(int x = 1; x <= env.getWidth(); x++)
        {
            for(int y = 1; y <= 2; y++)
            {
                white.add(new PawnPosition(x,y));
            }
        }
        for(int x = 1; x <= env.getWidth(); x++)
        {
            for(int y = env.getHeight(); y > env.getHeight() - 2; y--)
            {
                black.add(new PawnPosition(x,y));
            }
        }
    }
    void move(int x1, int x2, int y1, int y2, String role)
    {
        if(role.equals("white"))
        {
            white.remove(new PawnPosition(x1, y1));
            white.add(new PawnPosition(x2, y2));
            if(black.contains(new PawnPosition(x2, y2)))
                black.remove(new PawnPosition(x2, y2));
        }
        else if(role.equals("black"))
        {
            black.remove(new PawnPosition(x1, y1));
            black.add(new PawnPosition(x2, y2));
            if(white.contains(new PawnPosition(x2,y2)))
                white.remove(new PawnPosition(x2,y2));
        }
    }


    public ArrayList<Move> getLegalMoves(String role) {
        if(role.equals("white"))
            return getWhiteLegalMoves();
        else if(role.equals("black"))
            return getBlackLegalMoves();
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
        newBlack.addAll(black);
        return newBlack;
    }

    private ArrayList<Move> getWhiteLegalMoves()
    {
        for(PawnPosition p : white)
        {
            if(white.contains(new PawnPosition(p.getX(), p.getY() + 1)))
            {
                //System.out.println(p.getX() + "," + p.getY() + " cannot move white in front");
            }
            else if(black.contains(new PawnPosition(p.getX(), p.getY() + 1)))
            {
                //System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(p.getX() != env.getWidth() && black.contains(new PawnPosition(p.getX() + 1, p.getY() + 1)))
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

    private ArrayList<Move> getBlackLegalMoves()
    {
        for(PawnPosition p : black)
        {
            if(black.contains(new PawnPosition(p.getX(), p.getY() - 1)))
            {
                //System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(white.contains(new PawnPosition(p.getX(), p.getY() - 1)))
            {
                //System.out.println(p.getX() + "," + p.getY() + " cannot move white in front");
            }
            else if(p.getX() != env.getWidth() && white.contains(new PawnPosition(p.getX() + 1, p.getY() - 1)))
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
        return new State(newBlack, newWhite, env, currentPlayer);
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void evaluateScore(String role)
    {
        if(role.equals("white"))
        {
            if(checkForDraw(role))
                score =  0;
            else if(checkForWin(role))
                score =  100000;
            else if(checkForWin("black"))
                score = -100000;
            else
                getNonTerminalStateScore(role);
        }
        else if(role.equals("black"))
        {
            if(checkForDraw(role))
                score = 0;
            else if(checkForWin(role))
                score = 100000;
            else if(checkForWin("white"))
                score = -100000;
            else
                getNonTerminalStateScore(role);
        }
    }

    private boolean checkForWin(String role)
    {
        if(role.equals("white"))
        {
            for(PawnPosition p : white) {
                if (p.getY() == env.getHeight()) {
                    score = 100000;
                    return true;
                }
            }
        }
        else if(role.equals("black"))
        {
            for(PawnPosition p : black) {
                if(p.getY() == 1) {
                    score = 100000;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkForDraw(String role)
    {
        if(getLegalMoves(role).isEmpty())
        {
            return true;
        }
        return false;
    }

    public boolean isGameOver(String role) {
        evaluateScore(role);
        if(checkForWin(role))
            return true;
        if (checkForDraw(role)) {
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public void print() {
        for (int i = 1; i <= env.getHeight(); i++) {
            for (int j = 1; j <= env.getWidth(); j++) {
                PawnPosition pos = new PawnPosition(j, i);
                if (black.contains(pos)) System.out.print(" X ");
                else if (white.contains(pos)) System.out.print(" O ");
                else System.out.print("   ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    private void getNonTerminalStateScore(String role)
    {
        score = furthestPlayer(role);
        score += sizeDifference(role);
        score += canWin(role);
    }

    private int furthestPlayer(String role)
    {
        int bestWhite = 0;
        int bestBlack = 0;
        for(PawnPosition p : black)
        {
            bestBlack += (env.getHeight() - p.getY() + 1) * 5;
        }
        for(PawnPosition p : white)
        {
            bestWhite += p.getY() * 5;
        }
        if(role.equals("white"))
        {
            return bestWhite - bestBlack;
        }
        else if(role.equals("black"))
        {
            return bestBlack - bestWhite;
        }
        return 0;
    }

    private int sizeDifference(String role)
    {
        if(role.equals("white"))
            return (white.size() - black.size()) * 5;
        if(role.equals("black"))
            return (black.size() - white.size()) * 5;
        return 0;
    }

    private int canWin(String role)
    {
        for (PawnPosition p : white)
        {
            if (p.getY() == env.getHeight() - 1 && role.equals("white"))
                return 2000;
            if( p.getY() == env.getHeight() - 1 && role.equals("black"))
                return -10000;
        }
        for (PawnPosition p : black)
        {
            if (p.getY() == 1 && role.equals("black"))
                return 2000;
            if( p.getY() == 1 && role.equals("white"))
                return -10000;
        }
        return 0;
    }
}