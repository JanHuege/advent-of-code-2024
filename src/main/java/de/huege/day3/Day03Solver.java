package de.huege.day3;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.huege.helpers.Day;

public class Day03Solver extends Day {
   public Day03Solver() {
    super(3);
   }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = this.readInput(isExample);
        return calculate(lines);
    }

    @Override
    public long solvePart2(boolean isExample) {
        var lines = this.readInput(isExample);

        var sum = 0;
        var enabled = true;
        
        for (var line: lines) {        
            var pattern = Pattern.compile("don't\\(\\)|do\\(\\)");

            var lastEnabledIndex = 0;

            var activeMatcher = pattern.matcher(line);
            var matches = activeMatcher.results().toList();

            for (var match : matches) {
                if (enabled && match.group().equals("do()")) {
                    sum += calculate(List.of(line.substring(lastEnabledIndex, match.end())));
                    lastEnabledIndex = match.end();
                } else if (enabled && match.group().equals("don't()")) {
                    sum += calculate(List.of(line.substring(lastEnabledIndex, match.end())));
                    enabled = false;
                } else if (!enabled && match.group().equals("do()")) {
                    enabled = true;
                    lastEnabledIndex = match.end();
                }
            }

            if (enabled) {
                sum += calculate(List.of(line.substring(lastEnabledIndex, line.length())));
            }
        }    
        return sum;
    } 

    private long calculate(List<String> lines) {
        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var sum = 0L;
        for (var line : lines) {
            var matcher = pattern.matcher(line);
            var x = matcher.results().map(match -> Long.parseLong(match.group(1)) * Long.parseLong(match.group(2))).collect(Collectors.summarizingLong(Long::longValue));

            sum += x.getSum();
        }
        return sum;
    }
}
