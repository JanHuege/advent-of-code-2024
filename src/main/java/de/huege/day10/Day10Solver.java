package de.huege.day10;

import java.util.*;

import de.huege.helpers.Day;
import de.huege.common.grids.*;

public class Day10Solver extends Day {

    private static final int[] DX = {0, 0, 1, -1};
    private static final int[] DY = {1, -1, 0, 0};

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
            totalScore += calculateScore(grid, trailhead);
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
            totalRating += calculateRating(grid, trailhead);
        }
        
        return totalRating;
    }

    private static int calculateRating(Grid grid, Coordinates start) {
        Set<String> paths = new HashSet<>();
        StringBuilder currentPath = new StringBuilder();
        depthFirstSearchForPaths(grid, start, 0, new HashSet<>(), currentPath, paths);
        return paths.size();
    }
    
    private static void depthFirstSearchForPaths(Grid grid, Coordinates pos, int currentHeight,
                                  Set<Coordinates> visited, StringBuilder currentPath,
                                  Set<String> paths) {
        if (visited.contains(pos)) return;
        
        visited.add(pos);
        currentPath.append(pos.row()).append(',').append(pos.col()).append(';');
        
        char currentTile = grid.getTile(pos);
        if (currentTile == '9') {
            paths.add(currentPath.toString());
        }
        
        for (int i = 0; i < 4; i++) {
            Coordinates newPos = new Coordinates(pos.row() + DX[i], pos.col() + DY[i]);
            try {
                char nextTile = grid.getTile(newPos);
                if ((nextTile - '0') == currentHeight + 1) {
                    depthFirstSearchForPaths(grid, newPos, currentHeight + 1,
                               new HashSet<>(visited),
                               new StringBuilder(currentPath),
                               paths);
                }
            } catch (Exception e) {
                // Exceptions for control flow :P
            }
        }
    }
    
    private static int calculateScore(Grid grid, Coordinates start) {
        Set<String> reachableNines = new HashSet<>();
        dfs(grid, start, 0, new HashSet<>(), reachableNines);
        return reachableNines.size();
    }
    
    private static void dfs(Grid grid, Coordinates pos, int currentHeight,
                          Set<Coordinates> visited, Set<String> reachableNines) {
        if (visited.contains(pos)) return;
        visited.add(pos);
        
        char currentTile = grid.getTile(pos);
        if (currentTile == '9') {
            reachableNines.add(pos.row() + "," + pos.col());
            return;
        }
        
        for (int i = 0; i < 4; i++) {
            Coordinates newPos = new Coordinates(pos.row() + DX[i], pos.col() + DY[i]);
            try {
                char nextTile = grid.getTile(newPos);
                if ((nextTile - '0') == currentHeight + 1) {
                    dfs(grid, newPos, currentHeight + 1, visited, reachableNines);
                }
            } catch (Exception e) {
                // Exceptions for control flow :P
            }
        }
    }
}
