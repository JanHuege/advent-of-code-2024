import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import module java.base;

public class Day0 {

    @Test
    public void shouldAnswerWithTrue() {
        final var y = Stream.of(1, 2, 3).count();

        assertThat(y).isEqualTo(3);
    }
}
