package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    @Test
    void exists() throws IOException {
        Files.createFile(Application.Source);
        runAndAssertSourceExists();
    }

    @Test
    void no_exists() throws IOException {
        runAndAssertSourceExists();
    }

    @Test
    void empty() throws IOException {
        assertCompile("", "");
    }

    @Test
    void test_main() throws IOException {
        assertCompile(new FunctionRenderer().render("main", "int", "{return 0;}"), "def main() : I16 => {return 0;}");
    }

    private void assertCompile(String output, String input) throws IOException {
        Files.writeString(Application.Source, input);
        new Application().run();
        assertEquals(output, Files.readString(Application.Source));
    }

    private void runAndAssertSourceExists() throws IOException {
        new Application().run();
        assertTrue(Files.exists(Application.Source));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Application.Source);
    }
}
