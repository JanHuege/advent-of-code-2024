package de.huege.common.grids;

public record Coordinates(int row, int col) {
    public static Coordinates fromPosition(Position pos) {
        return new Coordinates(pos.row(), pos.col());
    }
}