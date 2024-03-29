package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultipleApplicationTest {
    public static final Path TEMP = Paths.get(".", "temp");
    public static final Path TEMP_SRC = TEMP.resolve("src");
    public static final Path TEMP_NAMESPACE = Paths.get("com", "meti");
    public static final Path TEMP_TARGET = TEMP.resolve("dist");
    public static final String TEMP_NAME = "Application";
    public static final String TEMP_TARGET_EXTENSION = ".mgs";

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(TEMP, new DeletingVisitor());
    }

    @Test
    void test() throws IOException {
        var actualSource = TEMP_SRC.resolve(TEMP_NAMESPACE).resolve(TEMP_NAME + ".java");
        Files.createDirectories(actualSource.getParent());
        Files.createFile(actualSource);

        Application.run(new DirectorySourceSet(TEMP_SRC),
                TEMP_TARGET,
                TEMP_TARGET_EXTENSION);

        assertTrue(Files.exists(TEMP_TARGET.resolve(TEMP_NAMESPACE).resolve(TEMP_NAME + TEMP_TARGET_EXTENSION)));
    }
}
