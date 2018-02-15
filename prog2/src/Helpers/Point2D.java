package Helpers;

public class Point2D
{
    private int x;
    private int y;

    public Point2D()
    {

    }

    public Point2D(int x, int y)
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

        if (object != null && (object instanceof Point2D))
        {
            isEqual = (this.x == ((Point2D) object).x) && (this.y == ((Point2D) object).y);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.x + this.y;
    }
}
