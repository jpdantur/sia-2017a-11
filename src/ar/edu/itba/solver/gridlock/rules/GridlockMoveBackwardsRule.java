package ar.edu.itba.solver.gridlock.rules;

import ar.edu.itba.solver.gridlock.Piece;
import ar.edu.itba.solver.gridlock.Position;
import ar.edu.itba.solver.gridlock.Piece.Orientation;

public class GridlockMoveBackwardsRule extends GridlockMoveRule {

    public GridlockMoveBackwardsRule(final short pieceIndex, final boolean mainPiece) {
        super(pieceIndex, mainPiece);
    }

    @Override
    public String getName() {
        return "Move Backwards Rule";
    }

		@Override
		protected Position getNextWhitespace(final Piece piece) {
			if (piece.getOrientation() == Orientation.HORIZONTAL) {
				return new Position((short)(piece.getPosition().getX() - 1), piece.getPosition().getY());
			} else {
				return new Position(piece.getPosition().getX(), (short)(piece.getPosition().getY() - 1));
			}
		}

		@Override
		protected Position getNextPosition(final Piece piece) {
			if (piece.getOrientation() == Orientation.HORIZONTAL) {
				return new Position((short)(piece.getPosition().getX() - 1), piece.getPosition().getY());
			} else {
				return new Position(piece.getPosition().getX(), (short)(piece.getPosition().getY() - 1));
			}
		}
}
