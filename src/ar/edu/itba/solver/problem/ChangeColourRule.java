package ar.edu.itba.solver.problem;

import ar.edu.itba.solver.engine.gps.api.GPSRule;
import ar.edu.itba.solver.engine.gps.api.GPSState;
import ar.edu.itba.solver.engine.gps.exception.NotAppliableException;

/**
 * Created by Ariel D on 18/3/2017.
 */
public class ChangeColourRule implements GPSRule {

    Integer colour;

    public ChangeColourRule(Integer colour) {
        this.colour=colour;
    }

    @Override
    public Integer getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "Change to colour " + colour;
    }

    @Override
    public GPSState evalRule(GPSState state) throws NotAppliableException {

        if (((State)state).getColours()[0][0] == this.colour){
            throw new NotAppliableException();
        }else{
            //TODO: Generar estado nuevo
        }

        return null;
    }
}
