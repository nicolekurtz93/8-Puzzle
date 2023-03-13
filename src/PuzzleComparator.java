import java.lang.System.Logger.Level;
import java.util.Comparator;

public class PuzzleComparator implements Comparator<Puzzle> {

    @Override
    public int compare(Puzzle o1, Puzzle o2) {
        if (o1.algorithm == EightPuzzle.Algorithms.AStar) {
            if ((o1.HerusticValue + o1.level) > (o2.HerusticValue + o2.level))
                return 1;
            if ((o1.HerusticValue + o1.level) < (o2.HerusticValue + o2.level))
                return -1;
            return 0;
        }
        if (o1.HerusticValue > o2.HerusticValue)
            return 1;
        if (o2.HerusticValue > o1.HerusticValue)
            return -1;
        return 0;
    }

}
