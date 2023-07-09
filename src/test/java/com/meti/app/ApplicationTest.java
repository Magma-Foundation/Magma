package com.meti.app;

import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.core.Results;
import com.meti.nio.NIOLocation;
import com.meti.nio.NIOPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private final Path source = Paths.get(".", "Main.java");
    private final Path target = Paths.get(".", "Main.mgs");

    static Result<Option<Path>, IOException> runImpl(Application application) {
        return application.compileAll()
                .mapValue(s -> s.map(NIOPath::from))
                .mapValue(s -> s.map(NIOLocation::unwrap));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(source);
        Files.deleteIfExists(target);
    }

    @Test
    void generatesTarget() throws IOException {
        runWithSource();
        assertTrue(Files.exists(target));
    }

    private Path runWithSource() throws IOException {
        Files.createFile(source);
        final NIOPath source1 = new NIOPath(source);
        Application application = new Application(new SingleVolatileGateway(source1));
        return Results.unwrap(runImpl(application)).unwrapOrPanic();
    }

    @Test
    void generatesProperTarget() throws IOException {
        assertEquals(target, runWithSource());
    }

    @Test
    void generatesNothing() throws IOException {
        final NIOPath source1 = new NIOPath(source);
        Application application = new Application(new SingleVolatileGateway(source1));
        Results.unwrap(runImpl(application));
        assertFalse(Files.exists(target));
    }
}
