package com.meti;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.Application;
import com.meti.compile.CompileException;
import com.meti.compile.SingleSourceSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path TARGET = Paths.get(".", "Index.mgs");
    public static final Path SOURCE = Paths.get(".", "Index.java");

    private static Option<Path> runWithSource() {
        try {
            Files.createFile(SOURCE);
            var list = new Application(new SingleSourceSet(SOURCE), Paths.get(".")).run().$();
            return list.isEmpty() ? None.None() : Some.Some(list.get(0));
        } catch (IOException e) {
            return fail(e);
        } catch (CompileException e) {
            return fail(e);
        }
    }

    @Test
    void generatesNothing() throws IOException, CompileException {
        new Application(new SingleSourceSet(SOURCE), Paths.get(".")).run();
        assertFalse(Files.exists(TARGET));
    }

    @Test
    void generatesProperTarget() {
        assertEquals(runWithSource().orElseNull(), TARGET);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(SOURCE);
        Files.deleteIfExists(TARGET);
    }

    @Test
    void generatesSomething() {
        runWithSource();
        assertTrue(Files.exists(TARGET));
    }
}
