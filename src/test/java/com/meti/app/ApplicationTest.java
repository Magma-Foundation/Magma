package com.meti.app;

import com.meti.app.clang.CRenderer;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.feature.Import;
import com.meti.app.magma.MagmaRenderingStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path Source = Paths.get(".", "index.mgf");
    public static final Path Target = Paths.get(".", "index.c");

    @Test
    void content() throws CompileException {
        var input = new MagmaRenderingStage(new Import("stdio"))
                .process()
                .asString()
                .orElse("");
        var output = new CRenderer(new Import("stdio")).process()
                .asString()
                .orElse("");
        assertCompile(input, output);
    }

    @Test
    void empty() {
        assertCompile("", "");
    }

    private void assertCompile(String input, String output) {
        try {
            Files.writeString(Source, input);
            runImpl();
            assertEquals(output, Files.readString(Target));
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void no_source() {
        var target = runImpl();
        assertFalse(Files.exists(target));
    }

    private Path runImpl() {
        try {
            return new Application(Source).run();
        } catch (ApplicationException e) {
            fail(e);
            return Paths.get(".");
        }
    }

    @Test
    void target() {
        assertEquals(Target, runImpl());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }

    @Test
    void with_source() throws IOException {
        Files.createFile(Source);
        runImpl();
        assertTrue(Files.exists(Target));
    }
}
