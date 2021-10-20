package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

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
    void test_main() throws IOException, CompileException {
        var body = new Content("{return 0;}");
        var identity = new Content("int main");
        var root = new Function(identity, body);
        var renderer = new CFunctionRenderer();
        var output = renderer.render(root);
        var actual = output.compute();
        assertCompile(actual, "def main() : I16 => {return 0;}");
    }

    private void assertCompile(String output, String input) throws IOException {
        try {
            Files.writeString(Application.Source, input);
            new Application().run();
            assertEquals(output, Files.readString(Application.Source));
        } catch (CompileException e) {
            fail(e);
        }
    }

    private void runAndAssertSourceExists() throws IOException {
        try {
            new Application().run();
            assertTrue(Files.exists(Application.Source));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Application.Source);
    }
}
