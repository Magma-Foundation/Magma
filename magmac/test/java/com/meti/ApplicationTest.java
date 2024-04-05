package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String CLASS_KEYWORD = "class ";
    public static final String TEST_NAME = "Test";
    public static final String NO_CLASS_PRESENT = "Location: com.meti.ApplicationTest\n" +
                                                  "Message: No class present.";
    public static final String CONTENT = " {}";

    private static String createInput(String name) {
        return CLASS_KEYWORD + name + CONTENT;
    }

    private static String createOutput(String output) {
        return CLASS_KEYWORD + "def " + output + "() =>" + CONTENT;
    }

    private static Result run(String input) {
        if (input.startsWith(CLASS_KEYWORD)) {
            var name = input.substring(CLASS_KEYWORD.length(), input.indexOf(CONTENT));
            return new Ok(createOutput(name));
        }

        return new Err(NO_CLASS_PRESENT);
    }

    @Test
    void classMissing() {
        assertEquals(NO_CLASS_PRESENT, run("").err().orElseThrow());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void classPresent(String name) {
        assertEquals(createOutput(name), run(createInput(name)).value().orElseThrow());
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
