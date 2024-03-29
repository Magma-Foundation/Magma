package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.meti.Application.run;
import static org.junit.jupiter.api.Assertions.*;

public class MultipleApplicationTest {
    public static final Path TEMP = Paths.get(".", "temp");
    public static final Path TEMP_SRC = TEMP.resolve("src");
    public static final Path TEMP_NAMESPACE = Paths.get("com", "meti");
    public static final Path TEMP_TARGET = TEMP.resolve("dist");
    public static final String TEMP_NAME = "Application";
    public static final String TEMP_TARGET_EXTENSION = ".mgs";

    private static void assertContent(String csq) {
        try {
            runWithContent(csq);
            assertTrue(Files.exists(TEMP_TARGET.resolve(TEMP_NAMESPACE).resolve(TEMP_NAME + TEMP_TARGET_EXTENSION)));
        } catch (IOException | CompileException e) {
            fail(e);
        }
    }

    private static void runWithContent(String csq) throws IOException, CompileException {
        var actualSource = TEMP_SRC.resolve(TEMP_NAMESPACE).resolve(TEMP_NAME + ".java");
        Files.createDirectories(actualSource.getParent());
        Files.writeString(actualSource, csq);

        run(new DirectorySourceSet(TEMP_SRC),
                TEMP_TARGET,
                TEMP_TARGET_EXTENSION);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(TEMP, new DeletingVisitor());
    }

    @Test
    void withinNamespace() {
        assertContent("");
    }

    @Test
    void withValidPackage() {
        assertContent("package com.meti;");
    }

    @Test
    void withInvalidPackage() {
        assertThrows(CompileException.class, () -> runWithContent("package test;"));
    }
}
