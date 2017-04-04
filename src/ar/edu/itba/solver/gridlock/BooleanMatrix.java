package ar.edu.itba.solver.gridlock;


import java.util.Arrays;

public class BooleanMatrix {

    private boolean array[];
    private int side;

    private BooleanMatrix(final int side) {
    	this.array = new boolean[side * side];
      this.side = side;
      for (int i = 0; i < side * side; i++) {
      	array[i] = false;
      }
    }
    
    public static BooleanMatrix createWithSide(final int side) {
    	return new BooleanMatrix(side);
    }

    public void setInPosition(final Position position, final boolean value){
        array[position.getX() + side * position.getY()] = value;
    }
    
    public void setInXY(final int x, final int y, final boolean value){
      array[x + side * y] = value;
  }

    public boolean isOccupied(final Position position){
        return array[position.getX() + side * position.getY()];
    }
    
    public boolean isOccupied(final int x, final int y){
      return array[x + side * y];
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanMatrix)) return false;

        BooleanMatrix that = (BooleanMatrix) o;

        if (side != that.side) return false;
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(array);
        result = 31 * result + side;
        return result;
    }

    private StringBuilder stringBuilder = new StringBuilder();
    @Override
    public String toString() {

        stringBuilder.delete(0,stringBuilder.capacity());
        stringBuilder.append('\n');
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                stringBuilder.append("█");
            } else {
                stringBuilder.append("░");
            }
            if (i % side == side-1) {
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.append('\n').toString();
    }
}
