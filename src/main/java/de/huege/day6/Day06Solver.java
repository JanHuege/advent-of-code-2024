package de.huege.day6;

import module java.base;

import de.huege.common.grids.Position;
import de.huege.helpers.Day;
import de.huege.common.grids.Coordinates;
import de.huege.common.grids.Direction;
import de.huege.common.grids.Grid;
import de.huege.common.grids.OutOfBoundsException;

public class Day06Solver extends Day {

    public Day06Solver() {
        super(6);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = readInput(isExample);

        var grid = new Grid(lines, Set.of('#'));

        var startingPosition = grid.searchForCharacter('^').get();
        grid.replaceTile(startingPosition, '.');
        
        var walker = new Walker(new Position(startingPosition.row(), startingPosition.col(), Direction.NORTH));

        while(true) {
            try {
                walker.walk(grid);
            } catch (ObstructedException e) {
                walker.turnRight();
            } catch (OutOfBoundsException e) {
                break;
            } catch (LoopDetectedException e) {
                throw new RuntimeException("This is not good");
            }
        }

        return walker.reportVisitedTiles().stream().map(pos -> new Coordinates(pos.row(), pos.col())).distinct().count();
    }

    @Override
    public long solvePart2(boolean isExample) {
        var lines = readInput(isExample);

        var grid = new Grid(lines, Set.of('#'));

        var startingPosition = grid.searchForCharacter('^').map(start -> new Position(start.row(), start.col(), Direction.NORTH)).orElseThrow();
        
        var walker = new Walker(new Position(startingPosition.row(), startingPosition.col(), Direction.NORTH));

        while(true) {
            try {
                walker.walk(grid);
            } catch (ObstructedException e) {
                walker.turnRight();
            } catch (OutOfBoundsException e) {
                break;
            } catch (LoopDetectedException e) {
                throw new RuntimeException("This is not good");
            }   
        }

        final var start = startingPosition;

        var potentialObstructionPositions = walker.reportVisitedTileCoordinates().parallelStream().filter(coord -> !coord.equals(Coordinates.fromPosition(start))).toList();

        return potentialObstructionPositions.parallelStream().filter(pos -> {
            var loopWalker = new Walker(new Position(start.row(), start.col(), Direction.NORTH));
            var copy = new Grid(lines, Set.of('#'));
            copy.replaceTile(pos, '#');
            copy.replaceTile(start, '.');
            while (true) {
                try {
                    loopWalker.walk(copy);
                } catch (ObstructedException e) {
                    loopWalker.turnRight();
                } catch (LoopDetectedException e) {
                    return true;
                } catch (OutOfBoundsException e) {
                    return false;
                } 
            }
        }).count();
    }
}

class Walker {
    private Position currentPosition; 

    private Set<Position> visited = new HashSet<>();
    private List<Position> path = new ArrayList<>();

    public Walker(Position pos) {
        currentPosition = pos;
        visited.add(pos);
        path.add(pos);
    }

    public void walk(Grid grid) throws OutOfBoundsException, ObstructedException, LoopDetectedException {
        var newPosition = new Position(currentPosition.row() + currentPosition.direction().r, currentPosition.col() + currentPosition.direction().c, currentPosition.direction());

        if (grid.isObstructed(newPosition)) throw new ObstructedException();
        if (visited.contains(newPosition)) throw new LoopDetectedException(); 
            
        currentPosition = newPosition;
        visit(newPosition);
    }

    public void turnRight() {
        currentPosition = new Position(currentPosition.row(), currentPosition.col(), currentPosition.direction().right());
    }
    
    public void turnLeft() {
        currentPosition = new Position(currentPosition.row(), currentPosition.col(), currentPosition.direction().left());
    }

    public Position where() {
        return currentPosition;
    }

    public Set<Position> reportVisitedTiles() {
        return new HashSet<>(visited);
    }

    public Set<Coordinates> reportVisitedTileCoordinates() {
        return new HashSet<>(visited.stream().map(Coordinates::fromPosition).toList());
    }

    public List<Position> reportTakenPath() {
        return List.copyOf(path);
    }

    private void visit(Position pos) {
        visited.add(pos);
        path.add(pos);
    }
}
