package de.huege.day14;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import de.huege.helpers.Day;

public class Day14Solver extends Day {

    public Day14Solver() {
        super(14);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = readInput(isExample);
        return new Puzzle(lines).solve();
    }

    @Override
    public long solvePart2(boolean isExample) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solvePart2'");
    }


    class Puzzle {
        final Grid grid;
        final int width = 101;
        final int height = 103;
    
        Puzzle(List<String> lines) {
            grid = Grid.from(lines.stream(), width, height);
        }
    
        long solve() {
            return grid.move(100).safetyFactor();
        }
    }
    
    record Coordinate(int x, int y) {
        static Coordinate from(String x, String y) {
            return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
        }
    }
    
    record Robot(int serial, Coordinate position, Coordinate speed) {
        static final AtomicInteger COUNTER = new AtomicInteger();
        static final Pattern PATTERN = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
    
        static Robot from(String line) {
            var matcher = PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(line);
            }
            return new Robot(
                    COUNTER.incrementAndGet(),
                    Coordinate.from(matcher.group(1), matcher.group(2)),
                    Coordinate.from(matcher.group(3), matcher.group(4))
            );
        }
    }
    
    record Grid(List<Robot> robots, Coordinate bounds) {

        static Grid from(Stream<String> lines, int width, int height) {
            return new Grid(lines.map(Robot::from).toList(), new Coordinate(width, height));
        }
    
        static int mod(int a, int b) {
            int c = a % b;
            return c >= 0 ? c : c + b;
        }
    
        Robot move(Robot robot, int times) {
            return new Robot(robot.serial(), new Coordinate(
                    mod(robot.position().x() + times * robot.speed().x(), bounds.x()),
                    mod(robot.position().y() + times * robot.speed().y(), bounds.y())
            ), robot.speed());
        }
    
        Coordinate quadrant(Robot robot) {
            return new Coordinate(
                    Integer.compare(robot.position().x() - bounds.x() / 2, 0),
                    Integer.compare(robot.position().y() - bounds.y() / 2, 0)
            );
        }
    
        Grid move(int times) {
            return new Grid(robots.stream().map(r -> move(r, times)).toList(), bounds);
        }
    
        long safetyFactor() {
            Map<Coordinate, Set<Robot>> quadrantMap = new HashMap<>();
            for (var robot : robots) {
                var quadrant = quadrant(robot);
                if (quadrant.x() != 0 && quadrant.y() != 0) {
                    quadrantMap.computeIfAbsent(quadrant, _ -> new HashSet<>());
                    quadrantMap.get(quadrant).add(robot);
                }
            }
            return quadrantMap.values().stream()
                    .mapToLong(Set::size)
                    .reduce(1, (a, b) -> a * b);
        }
    }

}
