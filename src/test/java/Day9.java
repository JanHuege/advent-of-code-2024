import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day9.Day09Solver;

public class Day9 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day09Solver().solvePart1(true)).isEqualTo(1928);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day09Solver().solvePart2(true)).isEqualTo(2858);
    }
}
