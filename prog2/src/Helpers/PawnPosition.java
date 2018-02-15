package Helpers;

public class PawnPosition
{
    private int x;
    private int y;

    public PawnPosition()
    {

    }

    public PawnPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
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
        int result = 18;
        int c = 0;

        c+= this.x;
        c+= this.y;

        result = 37 * result + c;
        return result;
    }
}
