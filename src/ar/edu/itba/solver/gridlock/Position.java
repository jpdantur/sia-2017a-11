package ar.edu.itba.solver.gridlock;

public class Position {

    private final short x;
    private final short y;

    public Position(short x, short y){
        this.x = x;
        this.y = y;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x || y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
