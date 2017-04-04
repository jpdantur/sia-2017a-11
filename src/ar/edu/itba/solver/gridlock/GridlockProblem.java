package ar.edu.itba.solver.gridlock;


import ar.edu.itba.solver.engine.gps.api.GPSRule;
import ar.edu.itba.solver.engine.gps.api.GPSState;
import ar.edu.itba.solver.gridlock.input.GridlockInput;
import ar.edu.itba.solver.gridlock.rules.GridlockMoveBackwardsRule;
import ar.edu.itba.solver.gridlock.rules.GridlockMoveForwardRule;

import java.util.ArrayList;
import java.util.List;

public class GridlockProblem implements ar.edu.itba.solver.engine.gps.api.GPSProblem{

    private List<GPSRule> rules;
    private GridlockState initialState;
    private StrategyHeuristic strategyHeuristic;

    public GridlockProblem(String path, StrategyHeuristic heuristic) {
    	initialState = GridlockInput.getFromFile(path);
    	strategyHeuristic = heuristic;
		}
    
    @Override
    public List<GPSRule> getRules() {
        return rules;
    }

    @Override
    public boolean isGoal(GPSState state) {
        GridlockState gridlockState = (GridlockState) state;
        return gridlockState.getMainPiece().getPosition().getX() == gridlockState.getSide() - gridlockState.getMainPiece().getLength();
    }

    @Override
    public Integer getHValue(GPSState state) {
        GridlockState gridlockState = (GridlockState) state;
        Piece piece = gridlockState.getMainPiece();
        BooleanMatrix matrix = gridlockState.getMatrix();
        Integer sum = 0;

        switch (strategyHeuristic){
            case H1:
                short x = (short) (piece.getPosition().getX() + piece.getLength());
                for (short xi = x; xi < gridlockState.getSide(); xi++) {
                    sum += matrix.isOccupied(new Position(xi, piece.getPosition().getY())) ? 2 : 1;
                }
                break;
            case H2:
                sum = gridlockState.getSide() - piece.getLength() - piece.getPosition().getX();
                break;
		default:
			/**/System.out.println(">>> DEFAULT HEURISTIC!!!");
			break;
        }
        return sum;
    }

    @Override
    public GPSState getInitState() {
        rules = new ArrayList<>();

        rules.add(new GridlockMoveBackwardsRule((short) 0, true));
        rules.add(new GridlockMoveForwardRule((short) 0, true));

        for (short i = 0; i < initialState.getPieces().size(); i++) {
            rules.add(new GridlockMoveForwardRule(i,false));
            rules.add(new GridlockMoveBackwardsRule(i, false));
        }
        return initialState;
    }
}
