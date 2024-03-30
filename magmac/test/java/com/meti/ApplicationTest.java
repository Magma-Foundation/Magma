package com.meti;

import com.meti.java.JavaApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private static void runImpl() {
        JavaApplication.run().ifPresent(Assertions::fail);
    }

    @AfterEach
    void tearDown() {
        new JavaPath(JavaApplication.TARGET).deleteIfExists();
        new JavaPath(JavaApplication.SOURCE).deleteIfExists();
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
