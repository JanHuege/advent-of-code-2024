import java.io.IOException;
import java.net.URISyntaxException;

import de.huege.day1.Day01Solver;
import de.huege.day2.Day02Solver;
import de.huege.day3.Day03Solver;
import de.huege.day4.Day04Solver;
import de.huege.day5.Day05Solver;
import de.huege.day6.Day06Solver;
import de.huege.day7.Day07Solver;
import de.huege.day8.Day08Solver;
import de.huege.day9.Day09Solver;
import de.huege.day10.Day10Solver;
import de.huege.helpers.Day;

void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
    solveDay(new Day01Solver());
    solveDay(new Day02Solver());
    solveDay(new Day03Solver());
    solveDay(new Day04Solver());
    solveDay(new Day05Solver());
    solveDay(new Day06Solver());
    solveDay(new Day07Solver());
    solveDay(new Day08Solver());
    solveDay(new Day09Solver());
    solveDay(new Day10Solver());
}

void solveDay(Day day) {
    var number = day.getDay();

    System.out.println();
    System.out.println("### Solving day" + day.getDay() + " ###");

    try {
        System.out.println("Part 1: " + day.solvePart1(false));
        System.out.println("Part 2: " + day.solvePart2(false));
    } catch (Exception e) {
        System.err.println("Input is probably missing");   
    }

    System.out.println((number >= 10) ? "###################" : "####################");
}
