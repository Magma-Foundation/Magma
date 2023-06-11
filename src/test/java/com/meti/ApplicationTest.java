package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Path target;
    private Path source;

    @BeforeEach
    void setUp() {
        target = Paths.get(".", "Main.mgs");
        source = Paths.get(".", "Main.java");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(target);
        Files.deleteIfExists(source);
    }

    @Test
    void generatesProperTarget() throws IOException {
        assertEquals(target, runWithSource());
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    private Path runWithSource() throws IOException {
        Files.createFile(source);
        final NativePath source1 = new NativePath(source);
        return new Application(new SourceGateway(source1)).runOnce()
                .unwrapOrPanic()
                .map(NativePath::unwrap)
                .unwrap();
    }

    @Test
    void generatesNothing() throws IOException {
        final NativePath source1 = new NativePath(source);
        new Application(new SourceGateway(source1)).runOnce().unwrapOrPanic();
        assertFalse(Files.exists(target));
    }

}
