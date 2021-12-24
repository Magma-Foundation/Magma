package com.meti;

import com.meti.io.File;
import com.meti.io.PathWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.io.PathWrapper.Root;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtherApplicationTest {
    private static final PathWrapper Source = Root.resolve("test.mgf");
    private static final PathWrapper Target = Root.resolve("test.c");

    @Test
    void creates_proper_target() throws IOException, ApplicationException {
        Source.createAsFile();
        assertTrue(new Application(Source)
                .run()
                .map(File::asPath)
                .filter(value -> value.equals(Target))
                .isPresent());
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }
}
