import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day13.Day13Solver;

public class Day13 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day13Solver().solvePart1(true)).isEqualTo(480);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day13Solver().solvePart2(true)).isEqualTo(-1);
    }
}
