package de.huege.common.grids;

import module java.base;
import java.util.HashSet;

public class Grid {
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

    public Grid(List<String> lines) {
        this.obstacles = new HashSet<>();

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

    public char getTile(Coordinates pos) {
        return grid.get(pos.row()).get(pos.col());
    }

    public Optional<Character> getTileOpt(Coordinates pos) {
        try {
            return Optional.of(grid.get(pos.row()).get(pos.col()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public boolean hasTile(Coordinates pos) {
        try {
            grid.get(pos.row()).get(pos.col());
        } catch (Exception e) {
            return false;
        }
        return true;
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

    public List<Coordinates> retrieveCoordinates(char symbol) {
        var list = new ArrayList<Coordinates>();
        for (int row = 0; row < grid.size(); row++) {
            List<Character> currentRow = grid.get(row);
            for (int col = 0; col < currentRow.size(); col++) {
                if (currentRow.get(col) == symbol) {
                    list.add(new Coordinates(row, col));
                }
            }
        }
        return list;
    }

    public Set<Character> retrieveAvailableSymbols(Set<Character> ignored) {
        var availableChars = new HashSet<Character>();
        for (int row = 0; row < grid.size(); row++) {
            List<Character> currentRow = grid.get(row);
            for (int col = 0; col < currentRow.size(); col++) {
                var currentChar = currentRow.get(col);
                if (!ignored.contains(currentChar) && !availableChars.contains(currentChar)) {
                    availableChars.add(currentChar);
                }
            }
        }
        return availableChars;
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