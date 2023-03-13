public class Coordinate {
    int x;
    int y;

    Coordinate(int valuex, int valuey) {
        x = valuex;
        y = valuey;
    }

    Coordinate AddCoordinates(Coordinate coordinate) {
        return new Coordinate(x + coordinate.x, y + coordinate.y);
    }
}
