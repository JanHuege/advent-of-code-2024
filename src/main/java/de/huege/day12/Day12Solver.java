package de.huege.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import de.huege.common.grids.Coordinates;
import de.huege.common.grids.Direction;
import de.huege.common.grids.Grid;
import de.huege.common.grids.Position;
import de.huege.helpers.Day;

public class Day12Solver extends Day{

    public Day12Solver() {
        super(12);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = readInput(isExample);
        Grid grid = new Grid(lines);
        Set<Coordinates> visited = new HashSet<>();
        List<Region> regions = new ArrayList<>();
        
        Set<Character> plantTypes = grid.retrieveAvailableSymbols(Set.of());
        
        for (char plantType : plantTypes) {
            List<Coordinates> plantLocations = grid.retrieveCoordinates(plantType);
            for (Coordinates start : plantLocations) {
                if (!visited.contains(start)) {
                    Region region = findRegion(grid, visited, start);
                    if (region != null) {
                        regions.add(region);
                    }
                }
            }
        }

        return regions.stream()
                     .mapToInt(Region::getPrice)
                     .sum();
    }
    
    @Override
    public long solvePart2(boolean isExample) {
        var lines = readInput(isExample);
        Grid grid = new Grid(lines);
        Set<Coordinates> visited = new HashSet<>();
        List<Region> regions = new ArrayList<>();
        
        Set<Character> plantTypes = grid.retrieveAvailableSymbols(Set.of());
        
        for (char plantType : plantTypes) {
            List<Coordinates> plantLocations = grid.retrieveCoordinates(plantType);
            for (Coordinates start : plantLocations) {
                if (!visited.contains(start)) {
                    Region region = findRegion(grid, visited, start);
                    if (region != null) {
                        regions.add(region);
                    }
                }
            }
        }
        
        return regions.stream()
            .mapToLong(r -> {
                var edges = new Walker(r).calculateEdges(regions);
                return edges * r.elements.size();
            })
            .sum();
    }

    class Walker {
        private List<Coordinates> path;
        private List<Position> visited;
        private Coordinates position;
        private Direction currentDirection;
        private int turns; 
        private boolean circle;

        public Walker(Region region) {
            this.path = region.calculatePerimeter();
            this.position = path.getFirst();
            this.currentDirection = Direction.EAST;
            this.turns = 0;
            this.circle = false;
            this.visited = new ArrayList<>();
        }

        public Walker(List<Coordinates> coordinates) {
            this.path = coordinates;
            this.position = path.getFirst();
            this.currentDirection = Direction.EAST;
            this.turns = 0;
            this.circle = false;
            this.visited = new ArrayList<>();
        }

        public void turn() {
            var tempDir = currentDirection.right();
            var possibleNextPosOnRightTurn = new Coordinates(position.row() + tempDir.r, position.col() + tempDir.c);
            
            if (path.contains(possibleNextPosOnRightTurn)) {
                currentDirection = currentDirection.right();
            } else {
                currentDirection = currentDirection.left();
            }
            
        }

        public boolean checkNextPosition() {
            return path.contains(new Coordinates(position.row() + currentDirection.r, position.col() + currentDirection.c));
        }

        public Position calculateNextPosition() {
            var newPosition = new Position(position.row() + currentDirection.r, position.col() + currentDirection.c, currentDirection);
            if (visited.contains(newPosition)) {
                circle = true;
            }
            return newPosition;
        }
        
        public void walk() {
            if (checkNextPosition() && !couldTurnRight()) {
                var position = calculateNextPosition();
                this.position = Coordinates.fromPosition(position);
                visited.add(position);
            } else {
                turn();
                if (!visited.isEmpty()) {
                    turns++;
                }
            }
        }

        private boolean couldTurnRight() {
            var tempDir = currentDirection.right();
            var possibleNextPosOnRightTurn = new Coordinates(position.row() + tempDir.r, position.col() + tempDir.c);

            if (visited.isEmpty() || visited.stream().anyMatch(p -> Coordinates.fromPosition(p).equals(possibleNextPosOnRightTurn))) return false;

            return path.contains(possibleNextPosOnRightTurn);
        }

        public List<Coordinates> getLeftOverCoordinates() {
            var list = path.stream().filter(c -> !visited.stream().map(Coordinates::fromPosition).toList().contains(c)).toList();

            return new HashSet<>(list).stream().sorted((o1, o2) -> {
                if (o1.row() < o2.row() && o1.col() < o2.col()) {
                    return -1;
                } else if (o1.row() > o2.row() && o1.col() > o2.col()) {
                    return 1;
                } else if (o1.row() < o2.row()) {
                    return -1;
                } else if (o1.row() > o2.row()) {
                    return 1;
                }
                return 0;
            }).toList();
        }

        public boolean isCircle() {
            return this.circle;
        }
        
        public int calculateEdges(List<Region> regions) {
            while(!circle) {
                walk();
            }
            
            final var leftCoords = getLeftOverCoordinates();

            var subRegionTurns = regions.stream().filter(r -> r.elements.stream().anyMatch(c -> leftCoords.contains(c))).mapToInt(r -> {
                var chilWalker = new Walker(r);
                return chilWalker.calculateEdges(new ArrayList<>());
            }).sum();

            return turns + subRegionTurns;
        }
    }

    class Region {
        char type;
        int area;
        int perimeter;
        Set<Coordinates> elements;

        public Region(char type, int area, int perimeter, Set<Coordinates> elements) {
            this.type = type;
            this.area = area;
            this.perimeter = perimeter;
            this.elements = elements;
        }
        
        public int getPrice() {
            return area * perimeter;
        }

        public List<Coordinates> getElementsAsList() {
            return this.elements.stream().toList();
        }

        private List<Coordinates> getSurrouCoordinates(Coordinates c) {
            
            var fieldLeft = new Coordinates(c.row(), c.col() - 1);
            var fieldTopLeft = new Coordinates(c.row() - 1, c.col() - 1);
            var fieldBottomLeft = new Coordinates(c.row() + 1, c.col() - 1);
            var fieldRight = new Coordinates(c.row(), c.col() + 1);
            var fieldTopRight = new Coordinates(c.row() - 1, c.col() + 1);
            var fieldBottomRight = new Coordinates(c.row() + 1, c.col() + 1);
            var fieldTop = new Coordinates(c.row() - 1, c.col());
            var fieldBottom = new Coordinates(c.row() + 1, c.col());
    
            return Arrays.asList(
                fieldLeft,
                fieldTopLeft,
                fieldBottomLeft,
                fieldRight,
                fieldTopRight,
                fieldBottomRight,
                fieldTop,
                fieldBottom
            );
        }

        public List<Coordinates> calculatePerimeter() {
            var path = this.elements.stream().flatMap(el -> getSurrouCoordinates(el).stream()).filter(x -> !elements.contains(x)).sorted((o1, o2) -> {
                if (o1.row() < o2.row() && o1.col() < o2.col()) {
                    return -1;
                } else if (o1.row() > o2.row() && o1.col() > o2.col()) {
                    return 1;
                } else if (o1.row() < o2.row()) {
                    return -1;
                } else if (o1.row() > o2.row()) {
                    return 1;
                }
                return 0;
            }).toList();
            return path;
        }
    }
    
    private Region findRegion(Grid grid, Set<Coordinates> visited, Coordinates start) {
        Set<Coordinates> elements = new HashSet<>(Arrays.asList(start));

        if (visited.contains(start)) {
            return null;
        }
        
        char type = grid.getTile(start);
        int area = 0;
        int perimeter = 0;
        Queue<Coordinates> queue = new LinkedList<>();
        queue.offer(start);
        
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        
        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }
            
            visited.add(current);
            area++;
            
            int localPerimeter = 4;
            for (Direction dir : directions) {
                Coordinates next = new Coordinates(current.row() + dir.r, current.col() + dir.c);
                if (grid.hasTile(next) && grid.getTile(next) == type) {
                    localPerimeter--;

                    if (!visited.contains(next)) {
                        elements.add(next);
                        queue.offer(next);
                    }
                }
            }
            perimeter += localPerimeter;
        }
        
        return new Region(type, area, perimeter, elements);
    }
}
