package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.FileWrapper.Root;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtherApplicationTest {
    private static final FileWrapper Source = Root.resolve("test.mgf");
    private static final FileWrapper Target = Root.resolve("test.c");

    @Test
    void creates_proper_target() throws IOException {
        assertTrue(new Application(Source.create()).run()
                .filter(value -> value.equals(Target))
                .isPresent());
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }
}
