package ar.edu.itba.solver.gridlock.rules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.itba.solver.engine.gps.api.GPSRule;
import ar.edu.itba.solver.engine.gps.api.GPSState;
import ar.edu.itba.solver.gridlock.GridlockState;
import ar.edu.itba.solver.gridlock.Piece;
import ar.edu.itba.solver.gridlock.Position;

public abstract class GridlockMoveRule implements GPSRule {

	private final static Optional<GPSState> OPTIONAL_STATE = Optional.empty();
	
	private short pieceIndex;
	private boolean mainPiece;

	public GridlockMoveRule(final short pieceIndex, final boolean mainPiece) {
		this.pieceIndex = pieceIndex;
		this.mainPiece = mainPiece;
	}

	@Override
	public Integer getCost() {
		return 1;
	}
	
	protected abstract Position getNextWhitespace(final Piece piece);
	protected abstract Position getNextPosition(final Piece piece);

	private boolean isOutOfBounds(final GridlockState state, final Position p) {
		return p.getY() >= state.getSide() || p.getY() < 0 || p.getX() >= state.getSide() || p.getX() < 0 || state.getMatrix().isOccupied(p);
	}
	
	@Override
	public String toString() {
		return  getName() + " [pieceIndex=" + pieceIndex + ", mainPiece=" + mainPiece + "]";
	}

	@Override
	public Optional<GPSState> evalRule(GPSState state){
		
		final GridlockState gridlockState = (GridlockState) state;
		
		// copy the pieces
		final List<Piece> pieces = gridlockState.getPieces().stream().map(p -> p.getCopy()).collect(Collectors.toList());
		final Piece mainPiece = gridlockState.getMainPiece().getCopy();
		
		// grab the piece
		final Piece pieceToMove = this.mainPiece ? mainPiece : pieces.get(pieceIndex);
		
		// get next position
		final Position toBeOccupied = getNextWhitespace(pieceToMove);
		
		// check if it can be placed there
		if (isOutOfBounds(gridlockState, toBeOccupied)) {
			return OPTIONAL_STATE;
		}
		
		// set the piece position
		pieceToMove.setPosition(getNextPosition(pieceToMove));
		
		return Optional.of(GridlockState.createWithSideAndPieces(mainPiece, gridlockState.getSide(), pieces));
	}
}
