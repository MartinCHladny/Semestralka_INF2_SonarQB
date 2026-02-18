package test;

import game.PRSQTest; // <- this tells the compiler exactly where to find it
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import java.time.Duration;

public class PRSQTestLoopTest {

    @Test
    void testInfiniteLoopTimeout() {
        PRSQTest sq = new PRSQTest();
        // Run doSmothing() but fail if it takes more than 2 seconds
        //assertTimeout(Duration.ofSeconds(2), () -> sq.doSmothing());
    }
}
