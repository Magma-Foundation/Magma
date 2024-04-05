package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String CLASS_KEYWORD = "class ";
    public static final String NO_CLASS_PRESENT = "No class present.";
    public static final String CONTENT = " {}";
    public static final String ONLY_ONE_ROOT_CLASS = "Only one root class is permitted.";

    private static String getString(String message) {
        return "Location: com.meti.ApplicationTest\n" + "Message: " + message;
    }

    private static String createInput(String name) {
        return CLASS_KEYWORD + name + CONTENT;
    }

    private static String createOutput(String output) {
        return CLASS_KEYWORD + "def " + output + "() =>" + CONTENT;
    }

    private static Result run(String input) {
        if (input.startsWith(CLASS_KEYWORD)) {
            var contentStart = input.indexOf("{");
            var contentEnd = input.lastIndexOf('}');

            var name = input.substring(CLASS_KEYWORD.length(), contentStart).strip();
            if(input.substring(contentStart + 1, contentEnd).contains(CLASS_KEYWORD)) {
                return new Err(getString(ONLY_ONE_ROOT_CLASS));
            }
            return new Ok(createOutput(name));
        }

        return new Err(getString(NO_CLASS_PRESENT));
    }

    @Test
    void classMissing() {
        assertError(NO_CLASS_PRESENT, "");
    }

    private static void assertError(String message, String input) {
        assertEquals(getString(message), run(input).err().orElseThrow());
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void classPresent(String name) {
        assertEquals(createOutput(name), run(createInput(name)).value().orElseThrow());
    }

    @Test
    void multipleClasses(){
        assertError(ONLY_ONE_ROOT_CLASS, createInput("First") + createInput("Second"));
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
