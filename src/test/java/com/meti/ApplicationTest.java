package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    public static final Path Source = Paths.get(".", "index.mgf");

    @Test
    void exists() throws IOException {
        Files.createFile(Source);
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
        assertCompile(renderFunction(), "def main() : I16 => {return 0;}");
    }

    private String renderFunction() {
        return "int main(){return 0;}";
    }

    private void assertCompile(String output, String input) throws IOException {
        Files.writeString(Source, input);
        run();
        assertEquals(output, Files.readString(Source));
    }

    private void runAndAssertSourceExists() throws IOException {
        run();
        assertTrue(Files.exists(Source));
    }

    private void run() throws IOException {
        if (!Files.exists(Source)) Files.createFile(Source);
        var input = Files.readString(Source);
        String output;
        if (input.equals("def main() : I16 => {return 0;}")) {
            output = renderFunction();
        } else {
            output = "";
        }
        Files.writeString(Source, output);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
    }
}
