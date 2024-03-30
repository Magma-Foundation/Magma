package com.meti;

import com.meti.java.JavaApplication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTest {
    @Test
    void test() {
        assertFalse(JavaApplication.exists());
    }
}
