package ar.edu.itba.solver.gridlock;

import ar.edu.itba.solver.engine.gps.GPSEngine;
import ar.edu.itba.solver.engine.gps.GPSNode;
import ar.edu.itba.solver.engine.gps.SearchStrategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.solver.engine.gps.SearchStrategy.*;
import static ar.edu.itba.solver.gridlock.StrategyHeuristic.H1;
import static ar.edu.itba.solver.gridlock.StrategyHeuristic.H2;
import static ar.edu.itba.solver.gridlock.StrategyHeuristic.NONE;

	/**
	* > Run Configurations (example):
	*
	*	./res/benchmarks/gridlock/easy.json -d -o ./res/benchmarks/gridlock/solution/easy
	*/

public class Main {

    private static String outFileName = "outfile";
    private static String outFileExtension = ".txt";

    private final static String outFileBFS = "-BFS";
    private final static String outFileDFS = "-DFS";
    private final static String outFileIDDFS = "-IDDFS";
    private final static String outFileGREEDY = "-GREEDY";
    private final static String outFileASTAR = "-ASTAR";
    private final static String outFileStats = "-stats";

    private final static String defaultInput = "input.txt";

    public static void main(String args[]){

        final List<SearchStrategy> strategyList = new ArrayList<>();
        final List<StrategyHeuristic> heuristicList = new ArrayList<>();

        String inputFileName = processArguments(args, strategyList, heuristicList);

        final StringBuilder sb = new StringBuilder();


        //Iterate over the strategy list and find the solution
        for (int i = 0; i < strategyList.size(); i++){
            SearchStrategy strategy = strategyList.get(i);
            StrategyHeuristic heuristic = heuristicList.get(i);

            GPSEngine engine = new GPSEngine(new GridlockProblem(inputFileName, heuristic), strategy);

            long startMillis = System.currentTimeMillis();
            engine.findSolution();
            long endmillis = System.currentTimeMillis();

            GPSNode solutionNode = engine.getSolutionNode();
            long explosionCounter = engine.getExplosionCounter();
            int frontier = engine.getOpen().toArray().length;

            if (solutionNode != null) {

                System.out.println("OK! " + strategy.name() +  " solution found!");

                writeSolutionFile(strategy, solutionNode.getSolution());

                sb.append(strategy.name()).append(" Solution cost: ").append(solutionNode.getCost()).append('\n');

                sb.append(strategy.name()).append(" Solution depth: ").append(solutionNode.getDepth()).append('\n');

            }else{
                System.out.println(strategy.name() +  " solution not found!");
            }
            sb.append(strategy.name()).append(" Frontier nodes: ").append(frontier).append('\n');
            sb.append(strategy.name()).append(" Expanded nodes: ").append(explosionCounter).append('\n');
            sb.append(strategy.name()).append(" Execution time(ms): ").append((endmillis - startMillis)).append('\n');
            sb.append('\n');

        }


        //Print stats
        try {
            FileWriter fwStats = new FileWriter(outFileName.concat(outFileStats).concat(outFileExtension), false);
            fwStats.write(sb.toString());
            fwStats.close();
        } catch (IOException e){
            e.printStackTrace();
        }

	}

    private static void writeSolutionFile(SearchStrategy strategy, final String solution){

        StringBuilder filename = new StringBuilder().append(outFileName);
        switch (strategy) {
            case BFS:
                filename.append(outFileBFS);
                break;

            case DFS:
                filename.append(outFileDFS);
                break;
            case ASTAR:
                filename.append(outFileASTAR);
                break;
            case IDDFS:
                filename.append(outFileIDDFS);
                break;
            case GREEDY:
                filename.append(outFileGREEDY);
                break;
        }
        filename.append(outFileExtension);

        try {
            FileWriter fw = new FileWriter(filename.toString(), false);
            fw.write(solution);
            fw.close();

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private static String processArguments(String[] args, List<SearchStrategy> list, List<StrategyHeuristic> hList) {

        if (args.length < 2){
            System.out.println("-o <output filename>");
            System.out.println("-d : Use DFS Strategy.");
            System.out.println("-b : Use BFS Strategy.");
            System.out.println("-a <[h1 | h2]> : Use A* Strategy.");
            System.out.println("-i : Use IDDFS Strategy.");
            System.out.println("-g <[h1 | h2]> : Use GREEDY Strategy.");
            System.out.println("--all <[h1 | h2]> : Use all implemented strategies.");
            System.out.println("Args: <input filename> [-options]+");
            System.exit(1);
        }

        String inputFileName = defaultInput;



        for (int i = 0; i < args.length; i++){

            String str = args[i];

            switch (str){

                case "-g":
                    list.add(GREEDY);
                    checkHeuristicFormat(args, i, hList);
                    i++;
                    break;

                case "-a":
                    list.add(ASTAR);
                    checkHeuristicFormat(args, i, hList);
                    i++;
                    break;

                case "-d":
                    list.add(DFS);
                    hList.add(NONE);
                    break;

                case "-b":
                    list.add(BFS);
                    hList.add(NONE);
                    break;

                case "-i":
                    list.add(IDDFS);
                    hList.add(NONE);
                    break;

                case "-o":
                    if (args.length > i + 1) {
                        outFileName = args[i + 1];
                        i++;
                    } else {
                        badArgFormat("Correct use: -o <output filename>");
                    }
                    break;

                case "--all":
                    if (args.length > i + 1) {
                        list.add(DFS);
                        hList.add(NONE);

                        list.add(BFS);
                        hList.add(NONE);

                        list.add(IDDFS);
                        hList.add(NONE);

                        list.add(GREEDY);
                        list.add(ASTAR);

                        if (args[i + 1].toUpperCase().equals("H1")){
                            hList.add(H1);
                            hList.add(H1);
                        } else if (args[i + 1].toUpperCase().equals("H2")){
                            hList.add(H2);
                            hList.add(H2);
                        } else {
                            badArgFormat("--all parameter needs to specify heuristic.");
                        }
                        i++;
                    } else {
                        badArgFormat("Heuristic needs to be specified.");
                    }
                    break;

                default:
                    if(i==0)
                        inputFileName = str;
                    else
                        badArgFormat("Unexpected argument: " + str);
                    break;
            }
        }

        return inputFileName;
    }

    private static void checkHeuristicFormat(String[] args, int i, List<StrategyHeuristic> list){
        if (args.length > i + 1){
            if (args[i + 1].toUpperCase().equals("H1")) {
                list.add(H1);
            } else if (args[i + 1].toUpperCase().equals("H2")){
                list.add(H2);
            } else {
                badArgFormat("Heuristic name incorrect: " + args[i+1]);
            }
        } else {
            badArgFormat("Correct use: -a|g <[h1 | h2]> : Use A*|GREEDY Strategy.");
        }
    }

    private static void badArgFormat(String explanation){
        System.err.println("Bad Argument Format:\n" + explanation);
        System.exit(1);
    }


}
