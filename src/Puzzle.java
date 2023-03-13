import java.util.*;

public class Puzzle {
    private int size;
    private int[][] puzzle;
    public int HerusticValue = 0;
    private int[] goal;
    private Coordinate EmptySlot;
    public int level = 0;
    EightPuzzle.Heuristics heuristic;
    EightPuzzle.Algorithms algorithm;
    public String HashKey;
    Puzzle parent = null;

    Puzzle(int[] orderOfNumbers, EightPuzzle.Heuristics herustic, EightPuzzle.Algorithms algorithm, int size,
            int[] goal) {
        int counter = 0;
        this.size = size;
        puzzle = new int[size][size];
        this.goal = goal;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                puzzle[i][j] = orderOfNumbers[counter];
                if (puzzle[i][j] == 0) {
                    EmptySlot = new Coordinate(i, j);
                }
                ++counter;
            }
        }
        this.algorithm = algorithm;
        setHeuristicValue(herustic);
        CreateHashKey();
    }

    Puzzle(int[][] parent, EightPuzzle.Heuristics heuristic, int level, EightPuzzle.Algorithms algorithm, int size,
            int[] goal) {
        this.size = size;
        puzzle = new int[size][size];
        this.goal = goal;
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                puzzle[i][j] = parent[i][j];
                if (puzzle[i][j] == 0) {
                    EmptySlot = new Coordinate(i, j);
                }
            }
        }
        this.level = level + 1;
        this.algorithm = algorithm;
        setHeuristicValue(heuristic);
        CreateHashKey();

    }

    void setHeuristicValue(EightPuzzle.Heuristics heuristic) {
        this.heuristic = heuristic;

        switch (heuristic) {
            case ManhattanDistance:
                ManhattanDistance();
                break;
            case PortlandDistance:
                PortlandDistance();
                break;
            case NumberOfMismatchedTiles:
                NumberOfMismatchedTiles();
                break;
        }

    }

    private int PortlandDistance() {
        List<Coordinate> cornerCoordinates = new ArrayList<Coordinate>(
                List.of(new Coordinate(0, 0), new Coordinate(0, size - 1), new Coordinate(size - 1, 0),
                        new Coordinate(size - 1, size - 1)));
        HerusticValue = 0;
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                int valueAtLocation = puzzle[row][col];
                if (valueAtLocation != 0) {
                    int correctRow = (valueAtLocation - 1) / size;
                    int correctCol = (valueAtLocation - 1) % size;
                    int Distance = Math.abs(correctRow - row) + Math.abs(correctCol - col);
                    Coordinate curCoordinate = new Coordinate(row, col);
                    if (cornerCoordinates.contains(curCoordinate))
                        Distance += 5;
                    HerusticValue += Distance;
                }

            }
        }
        return HerusticValue;
    }

    void SwapPuzzle(Coordinate coordinate) {
        puzzle[EmptySlot.x][EmptySlot.y] = puzzle[coordinate.x][coordinate.y];
        puzzle[coordinate.x][coordinate.y] = 0;
        EmptySlot.x = coordinate.x;
        EmptySlot.y = coordinate.y;
        setHeuristicValue(heuristic);
        CreateHashKey();
    }

    void CreateHashKey() {
        String hashKey = new String();
        hashKey += "(";
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col)
                hashKey += puzzle[row][col] + " ";
        }
        hashKey += ")";
        HashKey = hashKey;
    }

    int NumberOfMismatchedTiles() {
        HerusticValue = 0;
        int counter = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (puzzle[i][j] != goal[counter] && puzzle[i][j] != 0) {
                    ++HerusticValue;
                }
                ++counter;
            }
        }
        return HerusticValue;
    }

    int ManhattanDistance() {
        HerusticValue = 0;
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                int valueAtLocation = puzzle[row][col];
                if (valueAtLocation != 0) {
                    int correctRow = (valueAtLocation - 1) / size;
                    int correctCol = (valueAtLocation - 1) % size;
                    int Distance = Math.abs(correctRow - row) + Math.abs(correctCol - col);
                    HerusticValue += Distance;
                }

            }
        }
        return HerusticValue;
    }

    Collection<Puzzle> CreateChildrenPuzzles() {
        List<Puzzle> childPuzzles = new ArrayList<Puzzle>();
        List<Coordinate> coordinates = new ArrayList<Coordinate>(
                List.of(new Coordinate(-1, 0), new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(0, -1)));

        for (Coordinate coordinate : coordinates) {
            Coordinate childCoordinate = EmptySlot.AddCoordinates(coordinate);
            if (childCoordinate.x >= 0 && childCoordinate.y >= 0) {
                if (childCoordinate.x < size && childCoordinate.y < size) {
                    Puzzle childPuzzle = new Puzzle(puzzle, heuristic, level, algorithm, size, goal);
                    childPuzzle.SwapPuzzle(childCoordinate);
                    childPuzzle.parent = this;
                    childPuzzles.add(childPuzzle);
                }
            }
        }
        return childPuzzles;
    }

    boolean isGoalReachable() {
        int[] puzzleToList = new int[size * size];
        int counter = 0;
        int parity = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                puzzleToList[counter] = puzzle[i][j];
                ++counter;
            }
        }

        for (int i = 0; i < (size * size); ++i) {
            if (puzzleToList[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < (size * size); ++j) {
                if (puzzleToList[i] > puzzleToList[j] && puzzleToList[j] != 0) {
                    ++parity;
                }
            }
        }
        if (size % 2 != 0) // grid is odd
        {
            return (parity % 2) == 0;
        } else // grid is even, need to check location of blank
        {
            int rowOfBlank = size - EmptySlot.x;
            if (rowOfBlank % 2 != 0) {
                return parity % 2 == 0;
            }
            return parity % 2 != 0;
        }
    }

    void Print() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print(puzzle[i][j]);
            }
            System.out.println();
        }
        System.out.println("Heuristic value " + HerusticValue + "Level " + level);
        System.out.println();
    }

    void PrintFullPath(Puzzle puzzleToPrint) {
        if (puzzleToPrint == null) {
            System.out.println();
            return;
        }
        PrintFullPath(puzzleToPrint.parent);

        if (puzzleToPrint.parent != null)
            System.out.print(" -> " + puzzleToPrint.HashKey);
        else
            System.out.print(puzzleToPrint.HashKey);
    }
}
