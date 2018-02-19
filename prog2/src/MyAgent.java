import Helpers.Environment;
import Helpers.Move;
import Helpers.PawnPosition;
import Helpers.TimeIsUpException;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;

public class MyAgent implements Agent
{
    private String role; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private State initialState;
    private State currentState;
    HashSet<State> possibleMoves;
    private long timeNow;
    private long maxTime;

    public void init(String role, int width, int height, int playclock) {
        this.role = role;
        this.playclock = playclock;
        maxTime = (playclock * 1000) - 50;
        myTurn = !role.equals("white");
        Environment env = new Environment(height, width);
        initialState = new State(env);
        initialState.addToLists(width,height);
    }

    // lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
        if (lastMove != null) {
            int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
            String roleOfLastPlayer;
            if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
                roleOfLastPlayer = "white";
            } else {
                roleOfLastPlayer = "black";
            }
            System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);

            // TODO: 1. update your internal world model according to the action that was just executed
            currentState.move(x1,x2,y1,y2, roleOfLastPlayer);
            currentState.print();
        }
        else {
            currentState = initialState;
            currentState.print();
        }
        // update turn (above that line it myTurn is still for the previous state)
        myTurn = !myTurn;
        if (myTurn) {
            // TODO: 2. run alpha-beta search to determine the best move

            Move bestMove = Search(currentState);
            currentState.move(bestMove.getFrom().getX(), bestMove.getTo().getX(), bestMove.getFrom().getY(), bestMove.getTo().getY(), role);
            return "(move " + bestMove.getFrom().getX() + " " + bestMove.getFrom().getY() + " " + bestMove.getTo().getX() + " " + bestMove.getTo().getY() + ")";
        } else {
            return "noop";
        }
    }

    // is called when the game is over or the match is aborted
    @Override
    public void cleanup() {
        // TODO: cleanup so that the agent is ready for the next match
    }

    private Move Search(State state)
    {
        timeNow = System.currentTimeMillis();
        Move best = null;
        try
        {
            for(int depth = 2;;depth++)
            {
                best = RootSearch(state, depth);
            }
        }
        catch (TimeIsUpException msg)
        {
            return best;
        }
    }

    private Move RootSearch(State state, int depth) throws TimeIsUpException
    {
        if(timeNow + maxTime < System.currentTimeMillis())
        {
            throw new TimeIsUpException("mamma'in");
        }
        int alpha = -1000;
        int beta = 1000;
        Move best = null;
        ArrayList<Move> legalMoves;
        legalMoves = state.getLegalMoves(state.getCurrentPlayer());
        for(Move m : legalMoves)
        {
            int value = -ChildSearch(state.getStateByAction(m), -beta, -alpha, depth - 1);
            if(value > alpha) best = m;
            if(value >= beta) break;
        }
        return best;
    }

    private int ChildSearch(State state, int alpha, int beta, int depth) throws TimeIsUpException
    {
        if(timeNow + maxTime < System.currentTimeMillis())
        {
            throw new TimeIsUpException("mamma'in");
        }
        if(depth <= 0 || state.isGameOver(state.getCurrentPlayer()))
            return state.getScore();
        ArrayList<Move> legalMoves;
        legalMoves = state.getLegalMoves(state.getCurrentPlayer());
        for(Move m : legalMoves)
        {
            int value = -ChildSearch(state.getStateByAction(m), -beta, -alpha, depth - 1);
            if(value > alpha) alpha = value;
            if(alpha >= beta) break;
        }
        return alpha;
    }
}