package de.huege.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.huege.helpers.Day;

public class Day05Solver extends Day {

    public Day05Solver() {
        super(5);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = readInput(isExample);

        var rules = lines.stream().filter(line -> !line.isEmpty() && line.contains("|")).toList();
        var updates = lines.stream().filter(line -> !line.isEmpty() && line.contains(",")).toList();

        var rulesMap = new HashMap<String, List<String>>();
        rules.forEach(rule -> {
            var x = rule.split("\\|");
            if (rulesMap.containsKey(x[0])) {
                rulesMap.get(x[0]).add(x[1]);
            } else {
                var arr = new ArrayList<String>();
                arr.add(x[1]);
                rulesMap.put(x[0], arr);
            }
        });

        return updates.stream().filter(update -> isCorrect(update, rulesMap)).mapToInt(update -> getMiddle(update)).sum();   
    }
        
    private int getMiddle(String update) {

        var arr = update.split(",");

        return Integer.parseInt(arr[arr.length/2]);
    }
    
    private boolean isCorrect(String update, Map<String, List<String>> rules) {
        var arr = update.split(",");

        for (var i = 0; i < arr.length; i++) {
            for (var j = i; j < arr.length; j++) {
                final var y = i; 
                if (rules.containsKey(arr[j]) && rules.get(arr[j]).stream().anyMatch(x -> x.equals(arr[y]))) return false;
            }
            
        }
        
        return true;
    }

    @Override
    public long solvePart2(boolean isExample) {
        var lines = readInput(isExample);

        var rules = lines.stream().filter(line -> !line.isEmpty() && line.contains("|")).toList();
        var updates = lines.stream().filter(line -> !line.isEmpty() && line.contains(",")).toList();

        var rulesMap = new HashMap<String, List<String>>();
        rules.forEach(rule -> {
            var x = rule.split("\\|");
            if (rulesMap.containsKey(x[0])) {
                rulesMap.get(x[0]).add(x[1]);
            } else {
                var arr = new ArrayList<String>();
                arr.add(x[1]);
                rulesMap.put(x[0], arr);
            }
        });

        return updates.stream().filter(update -> !isCorrect(update, rulesMap)).map(update -> sortUpdate(update, rulesMap)).mapToInt(update -> getMiddle(update)).sum();   
    }
        
    private String sortUpdate(String update, HashMap<String, List<String>> rules) {
        var arr = update.split(",");
        var numbers = Arrays.stream(arr)
            .mapToInt(Integer::parseInt)
            .toArray();
        
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < numbers.length - 1; i++) {
                String current = String.valueOf(numbers[i]);
                String next = String.valueOf(numbers[i + 1]);
                
                if (rules.containsKey(next) && 
                    rules.get(next).contains(current)) {
                    int temp = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
        
        return Arrays.stream(numbers)
            .mapToObj(String::valueOf)
            .reduce((a, b) -> a + "," + b)
            .orElse("");
    }

}