import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day3.Day03Solver;

public class Day3 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day03Solver().solvePart1(true)).isEqualTo(161);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day03Solver().solvePart2(true)).isEqualTo(48);
    }
}
