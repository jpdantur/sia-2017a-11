package ar.edu.itba.solver.problem;

import ar.edu.itba.solver.engine.gps.api.GPSProblem;
import ar.edu.itba.solver.engine.gps.api.GPSRule;
import ar.edu.itba.solver.engine.gps.api.GPSState;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ariel D on 18/3/2017.
 */
public class FillZone implements GPSProblem{

    GPSState initState;
    List<GPSRule> rules;

    public FillZone(GPSState initState,int numberOfColours) {
        this.initState=initState;
        initRules(numberOfColours);
    }

    private void initRules(int colours) {

        rules=new LinkedList<>();

        for (int i = 1; i<colours ; i++  ){
            rules.add(new ChangeColourRule(i));
        }

    }

    @Override
    public GPSState getInitState() {
        return initState;
    }

    @Override
    public boolean isGoal(GPSState state) {
        //TODO: checkeo más eficiente

        int[][] board = ((State)state).getColours();

        int firstColour = board[0][0];

        for ( int[] row : board ){

            for (int colour: row){

                if (colour != firstColour){
                    return false;
                }

            }
        }

        return true;
    }

    @Override
    public List<GPSRule> getRules() {
        return this.rules;
    }

    @Override
    public Integer getHValue(GPSState state) {
        //TODO: heurística
        return null;
    }
}
