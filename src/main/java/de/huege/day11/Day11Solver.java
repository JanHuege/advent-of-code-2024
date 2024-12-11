package de.huege.day11;

import java.util.Arrays;
import java.util.HashMap;

import de.huege.helpers.Day;

public class Day11Solver extends Day {

    private static HashMap<Pair<Long, Integer>, Long> cache = new HashMap<>();

    public Day11Solver() {
        super(11);
    }

    @Override
    public long solvePart1(boolean isExample) {
        return Arrays.stream(readInput(isExample).get(0).split(" "))
            .mapToLong(Long::parseLong)
            .map(stone -> blink(stone, 25))
            .sum();
    }

    @Override
    public long solvePart2(boolean isExample) {
        return Arrays.stream(readInput(isExample).get(0).split(" "))
            .mapToLong(Long::parseLong)
            .map(stone -> blink(stone, 75))
            .sum();
    }

    private long blink(long stone, int iteration) {
        var key = Pair.of(stone, iteration);
        
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        
        if (iteration == 0) {
            cache.put(key, 1L);
            return 1;
        }

        if (stone == 0) {
            var res = blink(1, iteration - 1);
            cache.put(key, res);
            return res;
        }
        
        var length = String.valueOf(stone).length();
        if (length % 2 == 0) {
            int mid = length / 2;

            String left = String.valueOf(stone).substring(0, mid).replaceFirst("^0+", "");
            String right = String.valueOf(stone).substring(mid).replaceFirst("^0+", "");

            if (left.isEmpty()) left = "0";
            if (right.isEmpty()) right = "0";

            var sum = blink(Long.parseLong(left), iteration - 1) + blink(Long.parseLong(right), iteration - 1);
            cache.put(key, sum);

            return sum;
        } 
            
        var res = blink(stone * 2024, iteration -1);
        cache.put(key, res);

        return res;
    }

    public record Pair<L, R>(L left, R right) {
        public static <L, R> Pair<L, R> of(L l, R r) {
            return new Pair<L, R>(l, r);
        }
    }
}
