package de.huege.day7;

import module java.base;
import java.util.regex.Pattern;

import de.huege.helpers.Day;

public class Day07Solver extends Day {

    public Day07Solver() {
        super(7);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = readInput(isExample);
        return prepareInput(lines).stream().mapToLong(eq -> solve(eq, false)).sum();
    }

    @Override
    public long solvePart2(boolean isExample) {
        var lines = readInput(isExample);
        return prepareInput(lines).stream().mapToLong(eq -> solve(eq, true)).sum();
    }

    private List<Equation> prepareInput(List<String> lines) {
        var pattern = Pattern.compile("(\\d+): (.*)");

        return lines.stream().map(line -> {
            var matcher = pattern.matcher(line);
            matcher.find();
            var result = Long.parseLong(matcher.group(1));
            var elements = Arrays.stream(matcher.group(2).split(" ")).map(str -> Long.parseLong(str)).toList();

            return new Equation(result, elements);
        }).toList();
    }

    private long solve(Equation equation, boolean isPart2) {
        if (calc(equation.result(), equation.elements().get(0), equation.elements().subList(1, equation.elements().size()), isPart2)) {
            return equation.result();
        } else {
            return 0;
        }
    }


    private boolean calc(Long match, Long curTotal, List<Long> rest, boolean isPart2) {
        boolean result;
        if (rest.size()==0) {
            return match.equals(curTotal);
        } else {
            result = calc(match, curTotal * rest.get(0), rest.subList(1, rest.size()), isPart2);
            result |= calc(match, curTotal + rest.get(0), rest.subList(1, rest.size()), isPart2);
            if (isPart2) {
                result |= calc(match, (long) (curTotal * Math.pow(10, (""+rest.get(0)).length()) + rest.get(0)), rest.subList(1, rest.size()), isPart2);
            }
        }
        return result;
    }

    record Equation(long result, List<Long> elements) {}

}