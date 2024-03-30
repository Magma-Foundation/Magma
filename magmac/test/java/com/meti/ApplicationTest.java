package com.meti;

import com.meti.app.Application;
import com.meti.core.Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static void runImpl() {
        Application.run().ifPresent(Assertions::fail);
    }

    @AfterEach
    void tearDown() {
        Application.TARGET.deleteIfExists();
        Application.SOURCE.deleteIfExists();
    }

    @Test
    void generatesTarget() {
        assertDoesNotThrow(() -> Application.SOURCE.createFile().consume(Options::panic));

        runImpl();
        assertTrue(Application.TARGET.exists());
    }

    @Test
    void generatesNoTarget() {
        runImpl();
        assertFalse(Application.TARGET.exists());
    }
}
