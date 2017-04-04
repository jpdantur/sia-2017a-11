package ar.edu.itba.solver.gridlock;

public class Piece {
    Position position;
    short length;
    Orientation orientation;

    public enum Orientation{
        HORIZONTAL,
        VERTICAL
    }

    public Piece(Position begin, short length, Orientation orientation) {
        this.position = begin;
        this.length = length;
        this.orientation = orientation;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    public short getLength() {
        return length;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Piece(new Position(position.getX(), position.getY()), length, orientation);
    }

    public Piece getCopy(){
        try {
            return (Piece) this.clone();
        }catch (CloneNotSupportedException e){
            return null;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;

        Piece piece = (Piece) o;

        if (length != piece.length) return false;
        if (!position.equals(piece.position)) return false;
        return orientation == piece.orientation;
    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + (int) length;
        result = 31 * result + orientation.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + position +
                ", length=" + length +
                ", orientation=" + orientation.name() +
                '}';
    }
}
