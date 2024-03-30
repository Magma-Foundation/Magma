package com.meti;

import com.meti.java.JavaApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private static void runImpl() {
        JavaApplication.run().ifPresent(Assertions::fail);
    }

    @Test
    void generatesTarget() {
        assertDoesNotThrow(() -> JavaApplication.createSource().consume(Options::panic));

        runImpl();
        assertTrue(JavaApplication.exists());
    }

    @Test
    void generatesNoTarget() {
        runImpl();
        assertFalse(JavaApplication.exists());
    }
}
