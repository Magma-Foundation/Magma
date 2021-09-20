package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path Source = Paths.get(".", "index.mgs");
    public static final Path TargetHeader = Paths.get(".", "index.h");
    public static final Path TargetSource = Paths.get(".", "index.c");

    @Test
    void target_header_content() throws IOException {
        runImpl();

        var content = Files.readString(TargetHeader);
        assertEquals("#ifndef index_h\n" +
                "#define index_h\n" +
                "struct _index_ {}" +
                "struct _index_ __index__();" +
                "#endif\n", content);
    }

    @Test
    void target_source_content() throws IOException {
        runImpl();

        var content = Files.readString(TargetHeader);
        assertEquals("struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;" +
                "}", content);
    }

    @Test
    void target_header_present() throws IOException {
        runImpl();
        assertTrue(Files.exists(TargetHeader));
    }

    private void runImpl() throws IOException {
        Files.createFile(Source);
        new Application(Source).run();
    }

    @Test
    void target_source_present() throws IOException {
        runImpl();
        assertTrue(Files.exists(TargetSource));
    }

    @Test
    void target_header_missing() throws IOException {
        new Application(Source).run();
        assertFalse(Files.exists(TargetHeader));
    }

    @Test
    void target_source_missing() throws IOException {
        new Application(Source).run();
        assertFalse(Files.exists(TargetSource));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(TargetHeader);
        Files.deleteIfExists(TargetSource);
    }
}
