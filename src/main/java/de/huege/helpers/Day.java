package de.huege.helpers;

import java.util.List;

public abstract class Day implements Solver {
    private int day;

    public Day(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    protected List<String> readInput(boolean isExample) {
        return Reader.readAllLinesForDay(day, isExample);
    }

}
