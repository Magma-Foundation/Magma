package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    @Test
    void empty() {
        assertEquals("", run(""));
    }

    @Test
    void package_string() {
        assertEquals("", run("package com.meti;"));
    }

    private static String run(String s) {
        return "";
    }
}
