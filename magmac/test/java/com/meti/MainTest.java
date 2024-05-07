package com.meti;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class MainTest {
    private static void splitSingle(String value) {
        var output = Main.split(value);
        assertIterableEquals(singletonList(value), output);
    }

    @Test
    void split() {
        splitSingle("test");
    }

    @Test
    void semicolon() {
        var output = Main.split("a;b");
        assertIterableEquals(List.of("a", "b"), output);
    }

    @Test
    void braces() {
        splitSingle("{a;b}");
    }

    @Test
    void two_braces() {
        var output = Main.split("{}{}");
        assertIterableEquals(List.of("{}", "{}"), output);
    }

    @Test
    void quotes() {
        splitSingle("';'");
    }

    @Test
    void quotes_braces() {
        splitSingle("{'}'}");
    }
}
