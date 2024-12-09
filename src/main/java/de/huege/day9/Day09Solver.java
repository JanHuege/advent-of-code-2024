package de.huege.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import de.huege.helpers.Day;

public class Day09Solver extends Day {

    public Day09Solver() {
        super(9);
    }

    @Override
    public long solvePart1(boolean isExample) {

        var chars = readInput(isExample).get(0).toCharArray();

        var stack = new Stack<Block>();
        var list = new ArrayList<Block>();
        var nextId = 0;


        for (var i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                var length = chars[i] - '0';
                for (var j = 0; j < length; j++) {
                    stack.push(new Block(nextId, i, length, false));
                    list.add(new Block(nextId, i, length, false ));
                }

                nextId++;
            } else {
                var length = chars[i] - '0';
                for (var j = 0; j < length; j++) {
                    list.add(new Block(-1, i, length, true ));
                }
            }
        }

        var values = new ArrayList<Integer>();
        var freeSpace = list.stream().filter(block -> block.free).toList().size();

        for (var i = 0; list.get(i).pos <= stack.peek().pos && i + freeSpace < list.size(); i++) {
            if (list.get(i).free) {
                values.add(stack.pop().id);
            } else {
                values.add(list.get(i).id);
            }
        }

        long result = 0;
        for (var i = 0; i < values.size(); i++) {
            result += i * values.get(i);
        }

      return result;
    }

    @Override
    public long solvePart2(boolean isExample) {
        var chars = readInput(isExample).get(0).toCharArray();

        var list = new ArrayList<Block>();
        var nextId = 0;

        for (var i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                var length = chars[i] - '0';
                list.add(new Block(nextId, i, length, false ));
                nextId++;
            } else {
                var length = chars[i] - '0';
                list.add(new Block(-1, i, length, true ));
            }
        }

        var blocks = new ArrayList<Block>();
        var wasMoved = new ArrayList<Block>();

        var reversed = list.reversed().stream().filter(b -> !b.free).toList();

        for (var i = 0; i < list.size(); i++) {
            if (list.get(i).free || wasMoved.contains(list.get(i))) {
                var used = findFitting(reversed, list.get(i).length);
                wasMoved.addAll(used);
                reversed = reversed.stream().filter(b -> !used.contains(b)).filter(b -> !blocks.contains(b)).toList();
                blocks.addAll(used);
            } else if (!wasMoved.contains(list.get(i))) {
                blocks.add(list.get(i));
            }
        }

        var numbers = blocks.stream().flatMap(block -> IntStream.range(0, block.length).mapToObj(_ -> block.free ? 0 : block.id)).toList();
        
        long result = 0;
        for (var i = 0; i < numbers.size(); i++) {
            result += i * numbers.get(i);
        }

      return result;
    }

    private List<Block> findFitting(List<Block> candidates, int space) {
        var list = new ArrayList<Block>();

        if (space == 0) {
            return list;
        }
        
        var block = candidates.stream().filter(b -> b.length <= space).findFirst();

        if (block.isPresent()) {
            list.add(block.get());
            var newSpace = space - block.get().length;
            var nextBlock = findFitting(candidates.stream().filter(b -> b.id < block.get().id).toList(), newSpace);
            list.addAll(nextBlock);
        } else {
            list.add(new Block(-1, -1, space, true));
        }

        return list;
    }
    
    record Block(int id, int pos, int length, boolean free){}
}
