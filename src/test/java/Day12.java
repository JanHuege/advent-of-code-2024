import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day12.Day12Solver;

public class Day12 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day12Solver().solvePart1(true)).isEqualTo(1930);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day12Solver().solvePart2(true)).isEqualTo(1206);
    }
}
