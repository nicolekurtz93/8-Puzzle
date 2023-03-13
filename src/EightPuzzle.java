import java.util.Arrays;

public class EightPuzzle {
    public static void main(String[] args) throws Exception {
        int[][] initalStates = {
                { 2, 0, 3, 1, 8, 5, 4, 7, 6 },
                { 1, 8, 2, 0, 4, 3, 7, 6, 5 },
                { 1, 2, 3, 7, 5, 0, 6, 8, 4 },
                { 7, 2, 5, 0, 1, 4, 6, 3, 8 },
                { 5, 7, 0, 2, 1, 3, 8, 4, 6 },
                // { 1, 2, 3, 4, 5, 6, 0, 8, 7 }
        };
        int[] goal = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
        for (Algorithms algorithm : Algorithms.values()) {
            System.out.println("----------------------");
            for (Heuristics heuristic : Heuristics.values()) {
                double average = 0.0;

                for (int[] initalState : initalStates) {
                    Puzzle puzzle = new Puzzle(initalState, heuristic, algorithm, 3, goal);
                    Run_Algorithm Run_Algorithm = new Run_Algorithm();
                    Puzzle result = null;
                    System.out.println("\nInital State " + Arrays.toString(initalState));

                    if (!puzzle.isGoalReachable()) {
                        System.out.println("The goal is unreachable from this inital state.");
                        continue;
                    }

                    result = Run_Algorithm.Search(puzzle);
                    if (result != null) {
                        System.out.println(
                                "Algorithm: " + algorithm.toString()
                                        + "\nHeuristic: " + heuristic.toString() +
                                        " The goal state was found in "
                                        + result.level + " moves, " + Run_Algorithm.NumberOfExpansions
                                        + " puzzle expansions and " + Run_Algorithm.NumberOfIterations
                                        + " Iterations.");
                        result.PrintFullPath(result);
                        average += result.level;
                        System.out.println("\n");
                    } else {
                        System.out.println(
                                "Algorithm: " + algorithm.toString()
                                        + "\n Heuristic: " + heuristic.toString() +
                                        " The goal state was NOT found. Reached Max number of iterations\n");

                    }

                }
                average = average / 5;
                System.out
                        .println("\nAverage Number of Moves for Heuristic: " + heuristic + ", and Algorithm: "
                                + algorithm
                                + ", : " + average);
            }
            System.out.println("----------------------\n\n");
        }
    }

    public enum Heuristics {
        NumberOfMismatchedTiles,
        ManhattanDistance,
        PortlandDistance
    }

    public enum Algorithms {
        BestFirst,
        AStar
    }
}
