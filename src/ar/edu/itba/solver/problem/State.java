package ar.edu.itba.solver.problem;

import ar.edu.itba.solver.engine.gps.api.GPSState;

/**
 * Created by Ariel D on 18/3/2017.
 */
public class State implements GPSState{

    //TODO: cambiar por clase Board u otra implementación
    private int[][] colours;

    @Override
    public boolean equals(Object obj) {
        //TODO: complete
        return super.equals(obj);
    }

    /**
     * Retorna los colores presentes en el tablero
     * @return tablero con colores
     */
    public int[][] getColours(){
        return colours;
    }
}
