import java.util.Hashtable;
import java.util.LinkedList;

public class Run_Algorithm {
    LinkedList<Puzzle> OpenPuzzle = new LinkedList<Puzzle>();
    Hashtable<String, Puzzle> ClosedPuzzle = new Hashtable<String, Puzzle>();
    int NumberOfIterations = 0;
    int NumberOfExpansions = 0;

    Puzzle Search(Puzzle puzzle) {
        OpenPuzzle.add(puzzle);
        while (NumberOfIterations < 300000 && !OpenPuzzle.isEmpty()) {
            ++NumberOfIterations;
            Puzzle bestPuzzle = OpenPuzzle.pop();
            if (ClosedPuzzle.containsKey(bestPuzzle.HashKey)) {
                continue;
            }
            ClosedPuzzle.put(bestPuzzle.HashKey, bestPuzzle);
            // bestPuzzle.Print();
            if (bestPuzzle.HerusticValue == 0) // This is the goal state!
            {
                return bestPuzzle;
            }

            OpenPuzzle.addAll(bestPuzzle.CreateChildrenPuzzles());
            OpenPuzzle.sort(new PuzzleComparator());
            ++NumberOfExpansions;
        }

        return null;
    }

}
