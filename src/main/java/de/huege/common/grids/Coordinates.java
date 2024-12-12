package de.huege.common.grids;

public record Coordinates(int row, int col) {
    public static Coordinates fromPosition(Position pos) {
        return new Coordinates(pos.row(), pos.col());
    }

    public String toString() {
            return String.format("(%s, %s)", this.row, this.col);
    }
}