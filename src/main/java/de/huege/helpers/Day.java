package de.huege.helpers;

public abstract class Day implements Solver {
    private int day;

    public Day(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

}
