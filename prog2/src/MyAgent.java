import Helpers.Point2D;

import java.util.ArrayList;
import java.util.Random;

public class MyAgent implements Agent
{
    private String role; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private int width, height; // dimensions of the board
    private State initialState;
    ArrayList<Point2D> black;
    ArrayList<Point2D> white;

    public void init(String role, int width, int height, int playclock) {
        this.role = role;
        this.playclock = playclock;
        myTurn = !role.equals("white");
        this.width = width;
        this.height = height;
        initialState = new State();
        initialState.addToLists(width,height);
        System.out.println(this.role + " " + this.playclock + " " + myTurn + " " + this.width + " " + this.height);
        // TODO: add your own initialization code here
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
                initialState.moveForward(x1,y1);
            }
            else if(x2 < x1 && y2 == y1){
                initialState.moveDiagonally(x1,y1, true);
            }
            else{
                initialState.moveDiagonally(x1,y1, false);
            }

        }
        black = initialState.getBlackPawns();
        white = initialState.getWhitePawns();
        drawBoard();
        // update turn (above that line it myTurn is still for the previous state)
        myTurn = !myTurn;
        if (myTurn) {
            // TODO: 2. run alpha-beta search to determine the best move

            // Here we just construct a random move (that will most likely not even be possible),
            // this needs to be replaced with the actual best move.
            int x1,y1,x2,y2;
            findLegalMove();
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

    private void drawBoard()
    {
        for(int y = height; y > 0; y--)
        {
            for(int x = width; x > 0; x--)
            {
                for(Point2D pawn : black)
                {
                    if(x == pawn.getX() && y == pawn.getY())
                    {
                        System.out.print("X ");
                        continue;
                    }
                }
                for(Point2D pawn : white)
                {
                    if(x == pawn.getX() && y == pawn.getY())
                    {
                        System.out.print("O ");
                        continue;
                    }
                }
                System.out.print("  ");
            }
            System.out.println("");
        }
    }

    private void findLegalMove()
    {

    }
}
