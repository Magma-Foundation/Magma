package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static final Path Source = Paths.get(".", "index.mgs");
    private static final Path Target = Paths.get(".", "index.c");

    private boolean isTargetCreated() throws IOException {
        return runImpl().isPresent();
    }

    @Test
    void no_target() throws IOException {
        assertFalse(isTargetCreated());
    }

    @Test
    void testMain() throws IOException {
        Files.writeString(Source, "def main() : I16 => {return 0;}");
        runImpl();
        var output = Files.readString(Target);
        assertEquals("int main(){return 0;}", output);
    }

    private Option runImpl() throws IOException {
        return new Application(Source).run();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(Target);
    }

    @Test
    void with_target() throws IOException {
        Files.createFile(Source);
        assertTrue(isTargetCreated());
    }
}
