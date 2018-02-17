package Helpers;

import java.awt.*;

public class Move
{
    PawnPosition from, to;

    public Move(PawnPosition from, PawnPosition to)
    {
        this.from = from;
        this.to = to;
    }

    public PawnPosition getFrom() {
        return from;
    }

    public PawnPosition getTo() {
        return to;
    }
}
