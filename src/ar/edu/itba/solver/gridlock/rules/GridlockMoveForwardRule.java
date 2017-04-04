package ar.edu.itba.solver.gridlock.rules;

import ar.edu.itba.solver.gridlock.Piece;
import ar.edu.itba.solver.gridlock.Piece.Orientation;
import ar.edu.itba.solver.gridlock.Position;

public class GridlockMoveForwardRule extends GridlockMoveRule{

    public GridlockMoveForwardRule(final short pieceIndex, final boolean mainPiece) {
        super(pieceIndex, mainPiece);
    }

    @Override
    public String getName() {
        return "Move Forward Rule";
    }

		@Override
		protected Position getNextWhitespace(final Piece piece) {
			if (piece.getOrientation() == Orientation.HORIZONTAL) {
				return new Position((short)(piece.getPosition().getX() + piece.getLength()), piece.getPosition().getY());
			} else {
				return new Position(piece.getPosition().getX(), (short)(piece.getPosition().getY() + piece.getLength()));
			}
		}

		@Override
		protected Position getNextPosition(final Piece piece) {
			if (piece.getOrientation() == Orientation.HORIZONTAL) {
				return new Position((short)(piece.getPosition().getX() + 1), piece.getPosition().getY());
			} else {
				return new Position(piece.getPosition().getX(), (short)(piece.getPosition().getY() + 1));
			}
		}
}
