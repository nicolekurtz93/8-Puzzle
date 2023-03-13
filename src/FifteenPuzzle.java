import java.util.Arrays;

public class FifteenPuzzle {
    public static void main(String[] args) throws Exception {
        int[][] initalStates = {
                { 5, 2, 11, 3, 7, 1, 15, 4, 9, 13, 8, 0, 14, 6, 10, 12 },
                { 5, 2, 11, 3, 7, 1, 15, 4, 9, 13, 0, 8, 14, 6, 10, 12 },
                { 5, 2, 11, 3, 7, 1, 0, 4, 9, 13, 15, 8, 14, 6, 10, 12 },
                { 5, 2, 0, 3, 7, 1, 11, 4, 9, 13, 15, 8, 14, 6, 10, 12 },
                { 5, 1, 2, 3, 7, 0, 11, 4, 9, 13, 15, 8, 14, 6, 10, 12 }
        };
        int[] goal = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
        for (EightPuzzle.Algorithms algorithm : EightPuzzle.Algorithms.values()) {
            System.out.println("----------------------");
            for (EightPuzzle.Heuristics heuristic : EightPuzzle.Heuristics.values()) {

                double average = 0.0;
                for (int[] initalState : initalStates) {
                    Puzzle puzzle = new Puzzle(initalState, heuristic, algorithm, 4, goal);
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

}
