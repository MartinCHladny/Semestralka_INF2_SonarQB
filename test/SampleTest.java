import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class SampleTest {

    @Test
    void simpleTest() {
        assertEquals(4, 2 + 2);
    }
}

public class PRSQTestLoopTest {

    @Test
    void testDoSmothingTimeout() {
        PRSQTest sq = new PRSQTest();

        // Stop the method if it runs longer than 2 seconds
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            sq.doSmothing();
        }, "doSmothing() ran longer than expected (infinite loop detected)");
    }
}
