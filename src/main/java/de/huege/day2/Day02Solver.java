package de.huege.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.huege.helpers.Day;

public class Day02Solver extends Day {

    public Day02Solver() {
        super(2);
    }

    public long solvePart1(boolean isExample) {
        var lines = this.readInput(isExample);

        var numbers = lines.stream().map(line -> Arrays.stream(line.split(" ")).map(str -> Long.parseLong(str)).collect(Collectors.toList())).toList();

        return numbers.stream().filter(report -> checkSafe(report)).count();
    }

    public long solvePart2(boolean isExample) {
        var lines = this.readInput(isExample);

        var numbers = lines.stream().map(line -> Arrays.stream(line.split(" ")).map(str -> Long.parseLong(str)).collect(Collectors.toList())).toList();

        return numbers.stream().filter(report -> checkSafeAllow1BadLevel(report)).count();
    }

    private boolean checkSafe(List<Long> report) {
        List<Long> distances = new ArrayList<>();
        for (int i = 0; i < report.size() - 1; i++) {
            distances.add(calculateDistance(report.get(i + 1), report.get(i)));
        }

        var isIncreasing = distances.stream().allMatch((n) -> n > 0);
        var isDecreasing = distances.stream().allMatch((n) -> n < 0);

        if (!isIncreasing && !isDecreasing) {
            return false;
        }

        for (var distance : distances) {
            var absDistance = Math.absExact(distance);
            if (!(absDistance > 0 && absDistance < 4)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkSafeAllow1BadLevel(List<Long> report) {
       var preliminaryResult = checkSafe(report);

       if (preliminaryResult) return true;

       for (int i = 0; i < report.size(); i++) {
        var copy = new ArrayList<>(report);
        copy.remove(i);
        
        if (checkSafe(copy)) {
            return true;
        }
       }

       return false;
    }

    private long calculateDistance(long a, long b) {
        return a - b;
    }
}