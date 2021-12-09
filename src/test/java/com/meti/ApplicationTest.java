package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.stream.Collectors;

import static com.meti.Application.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void content_source() throws IOException {
        assertIntegrativeCompile("def main() : I16 => {return 0;}", "int main(){return 0;}");
    }

    private void assertCreatesFile(String name) throws IOException {
        createSourceDirectory();
        createSourceTestFile(name, "");
        runImpl();
        assertTrue(Files.exists(Application.OutCDirectory.resolve(name + ".c")));
    }

    private void runImpl() {
        try {
            run();
        } catch (ApplicationException e) {
            fail(e);
        }
    }

    private void createSourceDirectory() throws IOException {
        Files.createDirectory(Application.SourceDirectory);
    }

    private void createSourceTestFile(String name, String input) throws IOException {
        Files.writeString(resolveSourceTestFile(name), input);
    }

    private Path resolveSourceTestFile(final String name) {
        return Application.SourceDirectory.resolve(name + ".mgf");
    }

    @Test
    void creates_another_file() throws IOException {
        assertCreatesFile("test1");
    }

    private void assertIntegrativeCompile(String input, String output) throws IOException {
        createSourceDirectory();
        createSourceTestFile("test", input);

        runImpl();

        var actual = Files.readString(OutCDirectory.resolve("test.c"));
        assertEquals(output, actual);
    }

    @Test
    void creates_files() throws IOException {
        createSourceDirectory();
        createSourceTestFile("first", "");
        createSourceTestFile("second", "");

        runImpl();

        var expected = Set.of("first.c", "second.c");
        var actual = Files.list(Application.OutCDirectory)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    void creates_package() throws IOException {
        var parent = Application.SourceDirectory.resolve("inner");
        Files.createDirectories(parent);
        Files.createFile(parent.resolve("test.mgf"));

        runImpl();

        assertTrue(Files.exists(Application.OutCDirectory.resolve("inner").resolve("test.c")));
    }

    @Test
    void creates_single_file() throws IOException {
        assertCreatesFile("test");
    }

    @Test
    void does_not_create_out_directory() {
        assertFalse(Files.exists(OutDirectory));
    }

    @Test
    void empty_source() throws IOException {
        assertIntegrativeCompile("", "");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(OutDirectory, new DeletingVisitor());
        Files.walkFileTree(Application.SourceDirectory, new DeletingVisitor());
    }

    private static class DeletingVisitor implements FileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
