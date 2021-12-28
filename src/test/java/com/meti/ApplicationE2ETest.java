package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.meti.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationE2ETest {
    private static final Path Target = Root.resolveChild("index.c");
    private static final Path Source = Root.resolveChild("index.mg");

    @Test
    void no_writes_target() throws ApplicationException {
        run();
        assertFalse(Target.exists());
    }

    private static void run() throws ApplicationException {
        try {
            if (Source.exists()) {
                var name = Source.computeFileNameWithoutExtension();
                var target = Source.resolveSibling(name + ".mg");
                target.create();
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    @AfterEach
    void tearDown() throws com.meti.IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }

    @Test
    void writes_target() throws ApplicationException, com.meti.IOException {
        Source.create();
        run();
        assertTrue(Target.exists());
    }
}
