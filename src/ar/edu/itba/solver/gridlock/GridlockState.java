package ar.edu.itba.solver.gridlock;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.itba.solver.engine.gps.api.GPSState;

public class GridlockState implements GPSState{

	private final static int DEFAULT_SIDE = 6;

	private Piece mainPiece;
    private List<Piece> pieces;
    private BooleanMatrix matrix;
    private int side = 0;

    private GridlockState(final Piece mainPiece) {
    	this.side = DEFAULT_SIDE;
    	this.pieces = new LinkedList<>();
    	this.matrix = BooleanMatrix.createWithSide(DEFAULT_SIDE);
    	this.mainPiece = mainPiece;
    	
    	addToBoard(mainPiece);
    }
    
    private GridlockState(final Piece mainPiece, final int side) {
    	this.side = side;
    	this.pieces = new LinkedList<>();
    	this.matrix = BooleanMatrix.createWithSide(side);
    	this.mainPiece = mainPiece;
    	
    	addToBoard(mainPiece);
    }
    
    private GridlockState(final Piece mainPiece, final List<Piece> pieces) {
    	this.side = DEFAULT_SIDE;
    	this.pieces = pieces;
    	this.matrix = BooleanMatrix.createWithSide(DEFAULT_SIDE);
    	this.mainPiece = mainPiece;
    	
    	addToBoard(mainPiece);
    	
    	for (final Piece piece : pieces) {
    		addToBoard(piece);
    	}
    }
    
    private GridlockState(final Piece mainPiece, final int side, final List<Piece> pieces) {
    	this.side = side;
    	this.pieces = pieces;
    	this.matrix = BooleanMatrix.createWithSide(side);
    	this.mainPiece = mainPiece;
    	
    	addToBoard(mainPiece);
    	
    	for (final Piece piece : pieces) {
    		addToBoard(piece);
    	}
    }
    
    public static GridlockState createWithSideAndPieces(final Piece mainPiece, final int side, final List<Piece> pieces) {
    	return new GridlockState(mainPiece, side, pieces);
    }
    
    public static GridlockState createWithSide(final Piece mainPiece, final int side) {
    	return new GridlockState(mainPiece, side);
    }
    
    public static GridlockState createWithPieces(final Piece mainPiece, final List<Piece> pieces) {
    	return new GridlockState(mainPiece, pieces);
    }
    
    private boolean pieceFits(final Piece piece) {
    	boolean fix = true;
      for (short i = 0; i < piece.getLength(); i++) {
      	if (piece.getOrientation() == Piece.Orientation.HORIZONTAL) {
      		fix &= !matrix.isOccupied( piece.getPosition().getX() + i, piece.getPosition().getY() );
      	} else {
      		fix &= !matrix.isOccupied( piece.getPosition().getX(), piece.getPosition().getY() + i );
      	}
      }
      return fix;
    }
    
    private void placePiece(final Piece piece) {
      for (short i = 0; i < piece.getLength(); i++) {
      	if (piece.getOrientation() == Piece.Orientation.HORIZONTAL) {
      		matrix.setInXY(piece.getPosition().getX() + i, piece.getPosition().getY(), true);
      	} else {
      		matrix.setInXY(piece.getPosition().getX(), piece.getPosition().getY() + i, true);
      	}
      }
    }
    
    private boolean addToBoard(final Piece piece) {
    	if (pieceFits(piece)) {
    		placePiece(piece);
    		return true;
    	}
    	return false;
    }
    
    public boolean addPiece(final Piece piece) {
    	pieces.add(piece);
    	return addToBoard(piece);
    }

    public Collection<Piece> getPieces() {
        return pieces.stream().filter(p -> !p.equals(mainPiece)).collect(Collectors.toList());
    }

    public BooleanMatrix getMatrix() {
        return matrix;
    }

    public int getSide() {
        return side;
    }
    
    public Piece getMainPiece() {
    	return mainPiece;
    }


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GridlockState)) return false;

		GridlockState that = (GridlockState) o;

		if (side != that.side) return false;
		if (mainPiece != null ? !mainPiece.equals(that.mainPiece) : that.mainPiece != null) return false;
		if( pieces != null){
			for (int i = 0; i < pieces.size(); i++) {
				if(!pieces.get(i).equals(that.pieces.get(i))){
					return false;
				}
			}
		}return matrix != null ? matrix.equals(that.matrix) : that.matrix == null;
	}

	@Override
	public int hashCode() {
		int result = mainPiece != null ? mainPiece.hashCode() : 0;
		result = 31 * result + side;
		if( pieces != null){
			for(Piece p : pieces){
				result = 31 * result + p.hashCode();
			}
		}else
			result = 31 * result;
		return result;
	}

	private StringBuilder stringBuilder = new StringBuilder();
    @Override
    public String toString() {
    	final char posArray[] = new char[side * side];
    	int i = 0;
    	Arrays.fill(posArray,'░');
    	for (final Piece p : pieces) {
    		for (int j = 0; j < p.getLength(); j++) {
    			if (p.getOrientation() == Piece.Orientation.HORIZONTAL) {
    				posArray[p.getPosition().getX() + side * p.getPosition().getY() + j] = (char)(i + '0');
    			} else {
    				posArray[p.getPosition().getX() + side * p.getPosition().getY() + j * side] = (char)(i + '0');
    			}
    		}
    		i++;
    	}
    	for (int j = 0; j < mainPiece.getLength(); j++) {
  			if (mainPiece.getOrientation() == Piece.Orientation.HORIZONTAL) {
  				posArray[mainPiece.getPosition().getX() + side * mainPiece.getPosition().getY() + j] = 'P';
  			} else {
  				posArray[mainPiece.getPosition().getX() + side * mainPiece.getPosition().getY() + j * side] = 'P';
  			}
  		}
    	stringBuilder.delete(0,stringBuilder.capacity());
    	stringBuilder.append(' ');
    	for (i = 0; i < side; i++) {
    		stringBuilder.append(i);
    	}
    	stringBuilder.append("\n0");
    	for (i = 0; i < posArray.length - 1; i++) {
    		stringBuilder.append(posArray[i]);
    		if ( i % side == side - 1 ) {
    			stringBuilder.append("\n" + (i / side + 1));
    		}
    	}
    	stringBuilder.append('\n');
    	
    	return stringBuilder.toString();
    }
}
