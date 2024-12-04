import java.io.IOException;
import java.net.URISyntaxException;

import de.huege.day1.Day01Solver;
import de.huege.day2.Day02Solver;
import de.huege.day3.Day03Solver;
import de.huege.day4.Day04Solver;
import de.huege.helpers.Day;

void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
    solveDay(new Day01Solver());
    solveDay(new Day02Solver());
    solveDay(new Day03Solver());
    solveDay(new Day04Solver());
}

void solveDay(Day day) {
    var number = day.getDay();
    System.out.println();
    System.out.println("### Solving day" + day.getDay() + " ###");
    System.out.println("Part 1: " + day.solvePart1(false));
    System.out.println("Part 2: " + day.solvePart2(false));
    System.out.println((number >= 10) ? "###################" : "####################");
}
