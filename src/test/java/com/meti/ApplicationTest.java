package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Path source;
    private Path target;

    @BeforeEach
    void setUp() {
        source = Paths.get(".", "Main.java");
        target = Paths.get(".", "Main.mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }

    @Test
    void generates_target() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    private Optional<Path> run() {
        try {
            var sourceGateway = new SingleGateway(Paths.get("."), source);
            return new Application(sourceGateway, sourceGateway).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generatesProperTarget() {
        assertEquals(target, runWithSource());
    }

    private Path runWithSource() {
        try {
            Files.createFile(source);
            return run().orElseThrow();
        } catch (IOException e) {
            fail(e);
            return null;
        }
    }

    @Test
    void generates_nothing() {
        run();
        assertFalse(Files.exists(target));
    }
}
