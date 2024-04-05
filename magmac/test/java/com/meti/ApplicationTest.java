package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void test() {
        assertEquals("""
                **Location**: com.meti.Test
                **Message**: Unknown Token*

                **Details**:
                ```
                1) a
                   ^
                ```""", """
                **Location**: com.meti.Test
                **Message**: Unknown Token*

                **Details**:
                ```
                1) a
                   ^
                ```""");
    }
}
