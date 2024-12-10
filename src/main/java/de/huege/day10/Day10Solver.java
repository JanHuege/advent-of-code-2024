package de.huege.day10;

import java.util.*;
import java.util.stream.Collectors;

import de.huege.helpers.Day;
import de.huege.common.grids.*;

public class Day10Solver extends Day {

    private static final int[] DimensionenX = {0, 0, 1, -1};
    private static final int[] DimensionenY = {1, -1, 0, 0};

    public Day10Solver() {
        super(10);
    }

    @Override
    public long solvePart1(boolean isExample) {
        List<String> lines = readInput(isExample);
        Grid grid = new Grid(lines);
        
        int totalScore = 0;
        List<Coordinates> trailheads = grid.retrieveCoordinates('0');

        for (Coordinates trailhead : trailheads) {
            Set<Coordinates> endPoints = findAllPaths(grid, trailhead).stream()
                .map(path -> path.get(path.size() - 1))
                .collect(Collectors.toSet());
            totalScore += endPoints.size();
        }
        
        return totalScore;
    }

    @Override
    public long solvePart2(boolean isExample) {
        List<String> lines = readInput(isExample);
        Grid grid = new Grid(lines);

        int totalRating = 0;
        List<Coordinates> trailheads = grid.retrieveCoordinates('0');
        
        for (Coordinates trailhead : trailheads) {
            totalRating += findAllPaths(grid, trailhead).size();
        }
        
        return totalRating;
    }

    private Set<List<Coordinates>> findAllPaths(Grid grid, Coordinates start) {
        return depthFirstSearchForPaths(grid, start, 0, new HashSet<>(), new ArrayList<>());
    }
    
    private Set<List<Coordinates>> depthFirstSearchForPaths(Grid grid, Coordinates pos, int currentHeight, Set<Coordinates> visited, List<Coordinates> currentPath) {
        Set<List<Coordinates>> paths = new HashSet<>();
        
        if (visited.contains(pos)) return paths;
    
        visited.add(pos);
        currentPath.add(pos);
        
        char currentTile = grid.getTile(pos);
        if (currentTile == '9') {
            paths.add(new ArrayList<>(currentPath));
            return paths;
        }
        
        for (int i = 0; i < 4; i++) {
            Coordinates newPos = new Coordinates(pos.row() + DimensionenX[i], pos.col() + DimensionenY[i]);
            try {
                char nextTile = grid.getTile(newPos);
                if ((nextTile - '0') == currentHeight + 1) {
                    Set<List<Coordinates>> newPaths = depthFirstSearchForPaths(grid, newPos, currentHeight + 1,
                                                            new HashSet<>(visited),
                                                            new ArrayList<>(currentPath));
                    paths.addAll(newPaths);
                }
            } catch (Exception e) {
                // Exceptions for control flow :P
            }
        }
        return paths;
    }
}
