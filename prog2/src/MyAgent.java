import Helpers.Move;
import Helpers.PawnPosition;
import Helpers.TimeIsUpException;

import java.util.ArrayList;
import java.util.HashSet;

public class MyAgent implements Agent
{
    private String role; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private int width, height; // dimensions of the board
    private State initialState;
    HashSet<State> possibleMoves;

    public void init(String role, int width, int height, int playclock) {
        this.role = role;
        this.playclock = playclock;
        myTurn = !role.equals("white");
        this.width = width;
        this.height = height;
        initialState = new State();
        initialState.addToLists(width,height);
        System.out.println(this.role + " " + this.playclock + " " + myTurn + " " + this.width + " " + this.height);
        Search(initialState);
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
            if(x2 == x1 && y2 < y1){
                //initialState.moveForward(x1,y1,false);
            }
            else if(x2 < x1 && y2 == y1){
                //initialState.moveDiagonally(x1,y1, true);
            }
            else{
                //initialState.moveDiagonally(x1,y1, false);
            }

        }
        //possibleMoves = initialState.findLegalMove(width, height, role);
        System.out.print("Ello");
        //whitePawns = initialState.getWhitePawns();
        //blackPawns = initialState.getBlackPawns();
        // update turn (above that line it myTurn is still for the previous state)
        myTurn = !myTurn;
        if (myTurn) {
            // TODO: 2. run alpha-beta search to determine the best move

            // Here we just construct a random move (that will most likely not even be possible),
            // this needs to be replaced with the actual best move.
            //initialState.findLegalMove(width, height);
            int x1,y1,x2,y2;
            //findLegalMove();
            //return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
        } else {
            return "noop";
        }
        return "noop";
    }

    // is called when the game is over or the match is aborted
    @Override
    public void cleanup() {
        // TODO: cleanup so that the agent is ready for the next match
    }

    private Move Search(State state)
    {
        Move best = null;
        try
        {
            for(int depth = 0;;depth++)
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
        if("hello".equals("yellow"))
        {
            throw new TimeIsUpException("mamma'in");
        }
        int alpha = -100;
        int beta = 100;
        Move best = null;
        ArrayList<Move> legalMoves;
        legalMoves = state.getLegalMoves(width, role);
        for(Move m : legalMoves)
        {
            int value = -ChildSearch(state.getStateByAction(m), -alpha, -beta, depth - 1);
            if(value > alpha) best = m;
            if(alpha >= beta) break;
        }
        return best;
    }

    private int ChildSearch(State state, int alpha, int beta, int depth) throws TimeIsUpException
    {
        if("hello".equals("yellow"))
        {
            throw new TimeIsUpException("mamma'in");
        }
        if(depth == 0) return 1;
        ArrayList<Move> legalMoves;
        legalMoves = state.getLegalMoves(width, role);
        for(Move m : legalMoves)
        {
            int value = -ChildSearch(state.getStateByAction(m), -alpha, -beta, depth - 1);
            if(value > alpha) alpha = value;
            if(alpha >= beta) break;
        }
        return alpha;
    }
}
