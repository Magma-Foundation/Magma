package com.meti.app;

import com.meti.java.NIOPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void empty() throws IOException {
        Files.createFile(Main.Project);
        assertThrows(ApplicationException.class, ApplicationTest::runExceptionally);
    }

    private static void runExceptionally() throws ApplicationException {
        var path = new NIOPath(Main.Project);
        var application = new Application(path);
        application.run();
    }

    @Test
    void exists() throws IOException {
        Files.writeString(Main.Project, "{}");
        runAndAssertProjectFileExists();
    }

    private static void runAndAssertProjectFileExists() {
        runImpl();
        assertTrue(Files.exists(Main.Project));
    }

    private static void runImpl() {
        try {
            runExceptionally();
        } catch (ApplicationException e) {
            fail(e);
        }
    }

    @Test
    void no_exists() {
        runAndAssertProjectFileExists();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Main.Project);
    }
}
