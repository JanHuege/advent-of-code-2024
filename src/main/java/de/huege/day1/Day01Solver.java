package de.huege.day1;

import module java.base;
import de.huege.helpers.Day;
import de.huege.helpers.Reader;

public class Day01Solver extends Day {

    public Day01Solver() {
        super(1);
    }

    public long solvePart1(boolean isExample) {
        var lines = Reader.readAllLinesForDay(1, isExample);

        var unsortedVal1 = lines.stream().map((line) -> line.split("   ")[0]).collect(Collectors.toList());
        var unsortedVal2 = lines.stream().map((line) -> line.split("   ")[1]).collect(Collectors.toList());

        var sorted1 = unsortedVal1.stream().mapToLong(s -> Long.parseLong(s)).sorted().toArray();
        var sorted2 = unsortedVal2.stream().mapToLong(s -> Long.parseLong(s)).sorted().toArray();

        var sum = 0;

        for (int i = 0; i < sorted1.length; i++) {
            sum += calculateDiff(sorted1[i], sorted2[i]);
        }

        return sum;
    }

    private long calculateDiff(long a, long b) {
        return a > b ? a - b : b - a;
    }

    public long solvePart2(boolean isExample) {
        var lines = Reader.readAllLinesForDay(1, isExample);

        var list1 = lines.stream().map((line) -> line.split("   ")[0]).collect(Collectors.toList());
        var list2 = lines.stream().map((line) -> line.split("   ")[1]).collect(Collectors.toList());

        var result = list1.stream().mapToLong(value -> {
            var count = list2.stream().filter(x -> x.equals(value)).count();
            return count * Integer.parseInt(value);
        }).sum();

        return result;
    }
}