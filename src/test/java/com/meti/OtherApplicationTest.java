package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OtherApplicationTest {
    public static final Path Source = Paths.get(".", "main");
    public static final Path TargetHeader = Paths.get(".", "main.h");
    public static final Path TargetSource = Paths.get(".", "main.c");

    @Test
    void target_header_content() throws IOException {
        runImpl();

        var content = Files.readString(TargetHeader);
        assertEquals("#ifndef main_h\n" +
                "#define main_h\n" +
                "struct _main_ {}" +
                "struct _main_ __main__();" +
                "#endif\n", content);
    }

    @Test
    void target_source_content() throws IOException {
        runImpl();

        var content = Files.readString(TargetSource);
        assertEquals("struct _main_ __main__(){" +
                "struct _main_ this={};" +
                "return this;" +
                "}", content);
    }

    @Test
    void target_header() throws IOException {
        runImpl();
        assertTrue(Files.exists(TargetHeader));
    }

    @Test
    void target_source() throws IOException {
        runImpl();
        assertTrue(Files.exists(TargetSource));
    }

    private void runImpl() throws IOException {
        Files.createFile(Source);
        new Application(Source).run();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Source);
        Files.deleteIfExists(TargetHeader);
        Files.deleteIfExists(TargetSource);
    }
}
