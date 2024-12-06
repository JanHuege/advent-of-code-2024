package de.huege.day6;

import module java.base;

import de.huege.helpers.Day;

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

record Position(int row, int col, Direction direction) {}

record Coordinates(int row, int col) {
    public static Coordinates fromPosition(Position pos) {
        return new Coordinates(pos.row(), pos.col());
    }
}

enum Direction {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    final int r;
    final int c;

    Direction(int r, int c) {
        this.r = r;
        this.c = c;
    }

    Direction right() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    Direction left() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
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

class Grid {
    private List<List<Character>> grid = new ArrayList<>();
    private Set<Character> obstacles = new HashSet<>();

    public Grid(List<String> lines, Set<Character> obstacles) {
        this.obstacles = obstacles;

        lines.stream().forEach(line -> {
            var chars = line.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
            grid.add(chars);
        });
    }

    public void replaceTile(Position pos, char symbol) {
        replaceTile(Coordinates.fromPosition(pos), symbol);
    }

    public void replaceTile(Coordinates pos, char symbol) {
        grid.get(pos.row()).set(pos.col(), symbol);
    }
    
    public char getTile(Position pos) {
        return grid.get(pos.row()).get(pos.col());
    }

    public Optional<Position> searchForCharacter(char symbol) {
        for (int row = 0; row < grid.size(); row++) {
            List<Character> currentRow = grid.get(row);
            for (int col = 0; col < currentRow.size(); col++) {
                if (currentRow.get(col) == symbol) {
                    return Optional.of(new Position(row, col, null));
                }
            }
        }
        return Optional.empty();
    }

    public List<Position> searchForAllOccurencesForCharacter(char symbol) {
        var list = new ArrayList<Position>();
        for (int row = 0; row < grid.size(); row++) {
            List<Character> currentRow = grid.get(row);
            for (int col = 0; col < currentRow.size(); col++) {
                if (currentRow.get(col) == symbol) {
                    list.add(new Position(row, col, null));
                }
            }
        }
        return list;
    }

    public boolean isObstructed(Position pos) throws OutOfBoundsException {
        try {
            var symbol = grid.get(pos.row()).get(pos.col());
            return obstacles.contains(symbol);
        } catch (Exception e) {
            throw new OutOfBoundsException();
        }
    }
}

class OutOfBoundsException extends Throwable {}
class LoopDetectedException extends Throwable {}
class ObstructedException extends Throwable {}
