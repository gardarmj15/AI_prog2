package Helpers;

public class PawnPosition
{
    private int x;
    private int y;
    private boolean white;

    public PawnPosition()
    {

    }

    public PawnPosition(int x, int y, boolean white)
    {
        this.x = x;
        this.y = y;
        this.white = white;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWhite() {
        return white;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && (object instanceof PawnPosition))
        {
            isEqual = (this.x == ((PawnPosition) object).x) && (this.y == ((PawnPosition) object).y);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.x + this.y;
    }
}
