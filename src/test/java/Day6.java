import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day6.Day06Solver;

public class Day6 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day06Solver().solvePart1(true)).isEqualTo(41);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day06Solver().solvePart2(true)).isEqualTo(6);
    }
}
