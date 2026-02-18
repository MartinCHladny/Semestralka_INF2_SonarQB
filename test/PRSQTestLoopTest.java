package test;

import game.PRSQTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.*;

public class PRSQTestLoopTest {

    @Test
    void testInfiniteLoopSafely() {
        PRSQTest sq = new PRSQTest();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> sq.doSmothing());

        try {
            future.get(2, TimeUnit.SECONDS); // allow only 2 seconds
            fail("Expected infinite loop did not occur"); // optional: fail if method ends early
        } catch (TimeoutException e) {
            // Success! The method ran longer than allowed (infinite loop confirmed)
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        } finally {
            executor.shutdownNow(); // stop the thread safely
        }
    }
}
