package com.meti.app;

import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.core.Results;
import com.meti.nio.Location;
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

    static Result<Option<Path>, CompileException> runImpl(Application application) {
        return application.compileAll().mapValue(set -> set.iter().head())
                .mapValue(s -> s.map((NIOTarget value) -> NIOPath.from(value.path())))
                .mapValue(s -> s.map(Location::unwrap));
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
        try {
            Files.createFile(source);
            final NIOPath source1 = new NIOPath(source);
            var gateway = new SingleVolatileGateway(source1);
            var application = new Application(gateway, gateway);
            return Results.unwrap(runImpl(application)).unwrapOrPanic();
        } catch (CompileException e) {
            return fail(e);
        }
    }

    @Test
    void generatesProperTarget() throws IOException {
        assertEquals(target, runWithSource());
    }

    @Test
    void generatesNothing() throws CompileException {
        var source1 = new NIOPath(source);
        var gateway = new SingleVolatileGateway(source1);
        var application = new Application(gateway, gateway);
        Results.unwrap(runImpl(application));
        assertFalse(Files.exists(target));
    }
}
