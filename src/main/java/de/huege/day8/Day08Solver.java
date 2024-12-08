package de.huege.day8;


import module java.base;
import java.util.ArrayList;

import de.huege.common.grids.Coordinates;
import de.huege.common.grids.Grid;
import de.huege.helpers.Day;

public class Day08Solver extends Day {

    public Day08Solver() {
        super(8);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = readInput(isExample);

        var grid = new Grid(lines);

        var symbols = grid.retrieveAvailableSymbols(Set.of('.'));

        var symbolsWithCoordinates = symbols.stream().map(symbol -> {return new Pair<Character, List<Coordinates>>(symbol, grid.retrieveCoordinates(symbol));}).collect(Collectors.toMap((pair) -> pair.left(), (pair) -> pair.right() ));

        var set = new HashSet<Coordinates>();

        symbolsWithCoordinates.forEach((_, value) -> {
            var permutations = permutateList(value);

            var vectors = permutations.stream().map(pair -> new Pair<Pair<Coordinates, Coordinates>, int[]>(pair, Vector.createVector(pair.left(), pair.right()))).toList();

            vectors.forEach(pair -> {
                var p1 = pair.left().left();
                var p2 = pair.left().right();

                var vector = pair.right();

                var listPotentialPositions = new ArrayList<Coordinates>();

                listPotentialPositions.add(new Coordinates(p1.row() + vector[0], p1.col() + vector[1]));
                listPotentialPositions.add(new Coordinates(p2.row() + vector[0], p2.col() + vector[1]));
                listPotentialPositions.add(new Coordinates(p1.row() - vector[0], p1.col() - vector[1]));
                listPotentialPositions.add(new Coordinates(p2.row() - vector[0], p2.col() - vector[1]));

                listPotentialPositions.forEach(coord -> {
                    if (!coord.equals(p1) && !coord.equals(p2) && grid.hasTile(coord)) {
                        set.add(coord);
                    }    
                });
            });
        });

        return set.size();
    }

    @Override
    public long solvePart2(boolean isExample) {
        var lines = readInput(isExample);

        var grid = new Grid(lines);

        var symbols = grid.retrieveAvailableSymbols(Set.of('.'));

        var symbolsWithCoordinates = symbols.stream().map(symbol -> {return new Pair<Character, List<Coordinates>>(symbol, grid.retrieveCoordinates(symbol));}).collect(Collectors.toMap((pair) -> pair.left(), (pair) -> pair.right() ));

        var set = new HashSet<Coordinates>();

        symbolsWithCoordinates.forEach((_, value) -> {
            var permutations = permutateList(value);

            var vectors = permutations.stream().map(pair -> new Pair<Pair<Coordinates, Coordinates>, int[]>(pair, Vector.createVector(pair.left(), pair.right()))).toList();

            vectors.forEach(pair -> {
                var p1 = pair.left().left();
                var p2 = pair.left().right();

                var vector = pair.right();

                var listPotentialPositions = new ArrayList<Coordinates>();

                listPotentialPositions.addAll(multiply(p1, vector, grid));
                listPotentialPositions.addAll(multiply(p2, vector, grid));
                listPotentialPositions.addAll(multiply(p1, Vector.invert(vector), grid));
                listPotentialPositions.addAll(multiply(p2, Vector.invert(vector), grid));

                listPotentialPositions.forEach(coord -> {
                        set.add(coord);
                });
            });
        });

        return set.size();
    }

    private List<Coordinates> multiply(Coordinates point, int[] vector, Grid grid) {

        var positions = new ArrayList<Coordinates>();
        var row = point.row() + vector[0];
        var col = point.col() + vector[1];
        var coord = new Coordinates(row, col);

        for (var i = 2; grid.hasTile(coord); i++) {
            if (!coord.equals(null)) positions.add(coord);
            row = point.row() + vector[0] * i;
            col = point.col() + vector[1] * i;
            coord = new Coordinates(row, col);
        }

        return positions;
    }

    private <T> List<Pair<T, T>> permutateList(List<T> list) {
        List<Pair<T, T>> result = new ArrayList<>();
        
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j) {
                    result.add(new Pair<T, T>(list.get(i), list.get(j)));
                }
            }
        }
        return result;
    }   
}

record Pair<K, V>(K left, V right) {}

class Vector {
    public static int[] createVector(Coordinates p1, Coordinates p2) {
        int[] vector = new int[2];
        vector[0] = p2.row() - p1.row();
        vector[1] = p2.col() - p1.col();
        return vector;
    }

    public static int[] invert(int[] vector) {
        int[] invVector = new int[2];
        invVector[0] = vector[0] * -1;
        invVector[1] = vector[1] * -1;

        return invVector;
    }
}
