package test;

import game.PRSQTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.*;

public class PRSQTestLoopTest {

    @Test
    void testInfiniteLoopFails() {
        PRSQTest sq = new PRSQTest();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> sq.doSmothing());

        try {
            future.get(2, TimeUnit.SECONDS); // expect method to finish quickly
        } catch (TimeoutException e) {
            fail("Method doSmothing() is stuck in an infinite loop"); // fail if it takes too long
        } catch (Exception e) {
            fail("Unexpected exception: " + e);
        } finally {
            executor.shutdownNow(); // stop the thread safely
        }
    }
}
