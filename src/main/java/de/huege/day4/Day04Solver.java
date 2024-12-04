package de.huege.day4;

import de.huege.helpers.Day;

public class Day04Solver extends Day {
    public Day04Solver() { 
        super(4);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = this.readInput(isExample);

        var charArray = lines.stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        var count = 0;

        for (var row = 0; row < charArray.length; row++) {

            for (var col = 0; col < charArray[row].length; col++) {
                if ((charArray[row][col] == 'X' || charArray[row][col] == 'S')) {
                    count += checkXMAS(col, row, charArray);
                }
            }
        }

        return count;
    }

    private int checkXMAS(int column, int row, char[][] charArray) {
        return checkInline(column, row, charArray) + checkColumn(column, row, charArray) + checkDiagonal(column, row, charArray);
    }

    private int checkInline(int column, int row, char[][] charArray) {
        if (column + 3 > charArray[row].length - 1) return 0;

        var char1 = charArray[row][column];
        var char2 = charArray[row][column + 1];
        var char3 = charArray[row][column + 2];
        var char4 = charArray[row][column + 3];

        var word = new StringBuilder().append(char1).append(char2).append(char3).append(char4).toString();

        if (word.equals("XMAS") || word.equals("SAMX")) return 1;

        return 0;
    }

    private int checkColumn(int column, int row, char[][] charArray) {
        if (row + 3 > charArray.length - 1) return 0;

        var char1 = charArray[row][column];
        var char2 = charArray[row + 1][column];
        var char3 = charArray[row + 2][column];
        var char4 = charArray[row + 3][column];

        var word = new StringBuilder().append(char1).append(char2).append(char3).append(char4).toString();

        if (word.equals("XMAS") || word.equals("SAMX")) return 1;

        return 0;
    }

    private int checkDiagonal(int column, int row, char[][] charArray) {
        var count = 0;

        if (!(row + 3 > charArray.length - 1) && !(column + 3 > charArray[row].length - 1)) {
            var char1 = charArray[row][column];
            var char2 = charArray[row + 1][column + 1];
            var char3 = charArray[row + 2][column + 2];
            var char4 = charArray[row +3][column + 3];
    
            var word = new StringBuilder().append(char1).append(char2).append(char3).append(char4).toString();
    
            if (word.equals("XMAS") || word.equals("SAMX")) count++;    
        }
        
        if (!(column - 3 < 0) && !(row + 3 > charArray.length - 1)) {
            var char1 = charArray[row][column];
            var char2 = charArray[row + 1][column - 1];
            var char3 = charArray[row + 2][column - 2];
            var char4 = charArray[row + 3][column - 3];
    
            var word = new StringBuilder().append(char1).append(char2).append(char3).append(char4).toString();
    
            if (word.equals("XMAS") || word.equals("SAMX")) count++;
        };

        return count;
    }

    @Override
    public long solvePart2(boolean isExample) {
        var lines = this.readInput(isExample);

        var charArray = lines.stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

        var count = 0;

        for (var row = 0; row < charArray.length; row++) {

            for (var col = 0; col < charArray[row].length; col++) {
                if ((charArray[row][col] == 'A')) {
                    count += checkCrossMAS(col, row, charArray);
                }
            }
        }

        return count;
    }

    private int checkCrossMAS(int col, int row, char[][] charArray) {
        var topLeft = '_';
        var topRight = '_';
        var bottomLeft = '_';
        var bottomRight = '_';
        
        try {
            topLeft = charArray[row -1][col -1];
            topRight = charArray[row -1][col +1];
            bottomLeft = charArray[row + 1][col -1];
            bottomRight = charArray[row + 1][col + 1];
        } catch (Exception e) {
            return 0;
        }

        var firstPart = new StringBuilder().append(topLeft).append('A').append(bottomRight).toString();
        var secondPart = new StringBuilder().append(topRight).append('A').append(bottomLeft).toString();
        
        if (firstPart.equals("MAS") && secondPart.equals("MAS")) return 1;
        if (firstPart.equals("SAM") && secondPart.equals("SAM")) return 1;
        if (firstPart.equals("MAS") && secondPart.equals("SAM")) return 1;
        if (firstPart.equals("SAM") && secondPart.equals("MAS")) return 1;

        return 0;

    }
}
