package de.huege.day13;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.huege.helpers.Day;

public class Day13Solver extends Day {

    public Day13Solver() {
        super(13);
    }

    @Override
    public long solvePart1(boolean isExample) {
        var lines = readInput(isExample);

        List<GameMachine> machines = new ArrayList<>();
        Pattern numberPattern = Pattern.compile("\\d+");
        int buttonAX = 0, buttonAY = 0, bx = 0, by = 0, px = 0, py = 0;

        for (var line : lines) {
            Matcher matcher = numberPattern.matcher(line);
            if (line.startsWith("Button A:")) {
                matcher.find();
                buttonAX = Integer.parseInt(matcher.group());
                matcher.find();
                buttonAY = Integer.parseInt(matcher.group());
            } else if (line.startsWith("Button B:")) {
                matcher.find();
                bx = Integer.parseInt(matcher.group());
                matcher.find();
                by = Integer.parseInt(matcher.group());
            } else if (line.startsWith("Prize:")) {
                matcher.find();
                px = Integer.parseInt(matcher.group());
                matcher.find();
                py = Integer.parseInt(matcher.group());
                machines.add(new GameMachine(buttonAX, buttonAY, bx, by, px, py));
            }
        }
        
        return solve(machines).longValue();
    }

    @Override
    public long solvePart2(boolean isExample) {
        var lines = readInput(isExample);

        List<GameMachine> machines = new ArrayList<>();
        Pattern numberPattern = Pattern.compile("\\d+");
        long ax = 0, ay = 0, bx = 0, by = 0, px = 0, py = 0;

        for (var line : lines) {
            Matcher matcher = numberPattern.matcher(line);
            if (line.startsWith("Button A:")) {
                matcher.find();
                ax = Integer.parseInt(matcher.group());
                matcher.find();
                ay = Integer.parseInt(matcher.group());
            } else if (line.startsWith("Button B:")) {
                matcher.find();
                bx = Integer.parseInt(matcher.group());
                matcher.find();
                by = Integer.parseInt(matcher.group());
            } else if (line.startsWith("Prize:")) {
                matcher.find();
                px = Long.parseLong(matcher.group()) + 10000000000000L;
                matcher.find();
                py = Long.parseLong(matcher.group()) + 10000000000000L;
                machines.add(new GameMachine(ax, ay, bx, by, px, py));
            }
        }
        
        return solve(machines).longValue();
    }

    public BigInteger solve(List<GameMachine> machines) {
        BigInteger totalTokens = BigInteger.valueOf(0);
        for (GameMachine m : machines) {
            if (m.isWinnable()) {
                totalTokens = totalTokens.add(m.calculateRequiredTokens());
            }
        }
        return totalTokens;
    }

    class GameMachine {
        private BigInteger buttonAX, buttonAY, buttonBX, buttonBY, priceX, priceY;
        
        public GameMachine(long ax, long ay, long bx, long by, long px, long py) {
            this.buttonAX = BigInteger.valueOf(ax);
            this.buttonAY = BigInteger.valueOf(ay);
            this.buttonBX = BigInteger.valueOf(bx);
            this.buttonBY = BigInteger.valueOf(by);
            this.priceX = BigInteger.valueOf(px);
            this.priceY = BigInteger.valueOf(py);
        }
        
        private BigInteger det(BigInteger x1, BigInteger x2, BigInteger y1, BigInteger y2) {
            return x1.multiply(y2).subtract(x2.multiply(y1));
        }
        
        public boolean isWinnable() {
            BigInteger det = det(buttonAX, buttonBX, buttonAY, buttonBY);
            if (det.equals(BigInteger.ZERO)) {
                return false;
            }
            
            BigInteger detA = det(priceX, buttonBX, priceY, buttonBY);
            BigInteger detB = det(buttonAX, priceX, buttonAY, priceY);
            
            if (!detA.remainder(det).equals(BigInteger.ZERO) || 
                !detB.remainder(det).equals(BigInteger.ZERO)) {
                return false;
            }
            
            BigInteger a = detA.divide(det);
            BigInteger b = detB.divide(det);
            
            return a.compareTo(BigInteger.ZERO) >= 0 && b.compareTo(BigInteger.ZERO) >= 0;
        }
        
        public BigInteger calculateRequiredTokens() {
            BigInteger detMain = det(buttonAX, buttonBX, buttonAY, buttonBY);
            BigInteger detA = det(priceX, buttonBX, priceY, buttonBY);
            BigInteger detB = det(buttonAX, priceX, buttonAY, priceY);
            
            BigInteger a = detA.divide(detMain);
            BigInteger b = detB.divide(detMain);
            
            return a.multiply(BigInteger.valueOf(3))
                    .add(b);
        }
    }
}
