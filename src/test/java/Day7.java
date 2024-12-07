import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day7.Day07Solver;

public class Day7 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day07Solver().solvePart1(true)).isEqualTo(3749);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day07Solver().solvePart2(true)).isEqualTo(11387);
    }
}
