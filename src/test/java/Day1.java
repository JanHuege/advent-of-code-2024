import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day1.Day01Solver;

public class Day1 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day01Solver().solvePart1(true)).isEqualTo(11);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day01Solver().solvePart2(true)).isEqualTo(31);
    }
}
