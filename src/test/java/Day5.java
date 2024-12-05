import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day5.Day05Solver;

public class Day5 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day05Solver().solvePart1(true)).isEqualTo(143);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day05Solver().solvePart2(true)).isEqualTo(123);
    }
}
