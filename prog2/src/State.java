import Helpers.PawnPosition;

import java.util.ArrayList;

public class State
{
    private ArrayList<PawnPosition> pawns;
    private String role;

    public State()
    {
        pawns = new ArrayList<>();
    }

    public void addToLists(int w, int h)
    {
        for(int x = 1; x <= w; x++)
        {
            for(int y = 1; y <= 2; y++)
            {
                pawns.add(new PawnPosition(x,y,true));
            }
        }
        for(int x = 1; x <= w; x++)
        {
            for(int y = h; y > w; y--)
            {
                pawns.add(new PawnPosition(x,y,false));
            }
        }
    }
    void moveForward(int x, int y, boolean white){
        System.out.println("move forward");
        for(PawnPosition p : pawns){
            if(p.getX() == x && p.getY()== y && p.isWhite()){
                p = new PawnPosition(x, y + 1, white);
            }
            else if(p.getX() == x && p.getY()== y && !p.isWhite()){
                p = new PawnPosition(x, y + 1, white);
            }
        }
    }
    void moveDiagonally(int x, int y, Boolean right){
        if(right){
            System.out.println("move right");
            for(PawnPosition p : pawns){
                if(p.getX() == x && p.getY()== y){
                    p.setY(y - 1);
                    p.setX(x - 1);
                }
            }
        }
        else{
            System.out.println("move left");
            for(PawnPosition p : pawns){
                if(p.getX() == x && p.getY()== y){
                    p.setY(y - 1);
                    p.setX(x + 1);
                }
            }
        }
    }

    public ArrayList<PawnPosition> pawnsList() {
        return pawns;
    }

    public void findLegalMove(int width, int height)
    {
        for(PawnPosition p : pawns)
        {
            if(p.isWhite() && pawns.contains(new PawnPosition(p.getX(), p.getY() + 1, true)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move white in front");
            }
            else if(p.isWhite() && pawns.contains(new PawnPosition(p.getX(), p.getY() + 1, false)))
            {
                System.out.println(p.getX() + "," + p.getY() + " cannot move black in front");
            }
            else if(p.isWhite() && p.getX() != width && pawns.contains(new PawnPosition(p.getX() + 1, p.getY() + 1, false)))
            {
                System.out.println(p.getX() + "," + p.getY() + " kill black to the right");
            }
            else if(p.isWhite() && p.getX() != 1 && pawns.contains(new PawnPosition(p.getX() + 1, p.getY() - 1, false)))
            {
                System.out.println(p.getX() + "," + p.getY() + " kill black to the right");
            }
            else
            {
                System.out.println(p.getX() + "," + p.getY() + " can move");
            }
        }
    }
}
