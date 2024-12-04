import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day4.Day04Solver;

public class Day4 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day04Solver().solvePart1(true)).isEqualTo(18);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day04Solver().solvePart2(true)).isEqualTo(9);
    }
}
