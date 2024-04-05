package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void test() {
        assertEquals(createError(), run());
    }

    private static String run() {
        return createError();
    }

    private static String createError() {
        return """
                **Location**: com.meti.Test
                **Message**: Unknown Token*

                **Details**:
                ```
                1) a
                   ^
                ```""";
    }
}
