package com.meti.bug;

import com.meti.rule.Rule;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

public class RuleTest {
    protected static void assertValid(String input, Rule rule) {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            var present = rule
                    .lexImpl(input)
                    .isPresent();

            Assertions.assertTrue(present);
        });
    }
}
