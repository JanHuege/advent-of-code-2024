import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.huege.day2.Day02Solver;

public class Day2 {

    @Test
    public void shouldSolvePart1() {
        assertThat(new Day02Solver().solvePart1(true)).isEqualTo(2);
    }

    @Test
    public void shouldSolvePart2() {
        assertThat(new Day02Solver().solvePart2(true)).isEqualTo(4);
    }
}
