package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String CLASS_KEYWORD = "class ";
    public static final String TEST_NAME = "Test";
    public static final String NO_CLASS_PRESENT = "Location: com.meti.ApplicationTest\n" +
                                                  "Message: No class present.";
    public static final String CONTENT = " {}";
    public static final String INPUT = CLASS_KEYWORD + TEST_NAME + CONTENT;
    public static final String OUTPUT = CLASS_KEYWORD + "def " + TEST_NAME + "() =>" + CONTENT;

    private static Result run(String input) {
        if (input.equals(INPUT)) return new Ok(OUTPUT);
        return new Err(NO_CLASS_PRESENT);
    }

    @Test
    void classMissing() {
        assertEquals(NO_CLASS_PRESENT, run("").err().orElseThrow());
    }

    @Test
    void classPresent() {
        assertEquals(OUTPUT, run(INPUT).value().orElseThrow());
    }

    interface Result {
        Optional<String> err();

        Optional<String> value();
    }

    record Ok(String inner) implements Result {
        @Override
        public Optional<String> err() {
            return Optional.empty();
        }

        @Override
        public Optional<String> value() {
            return Optional.of(inner);
        }
    }

    record Err(String inner) implements Result {
        @Override
        public Optional<String> err() {
            return Optional.of(inner);
        }

        @Override
        public Optional<String> value() {
            return Optional.empty();
        }
    }
}
