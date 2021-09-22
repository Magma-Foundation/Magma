package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final Path Target = Paths.get(".", "index.c");
    public static final Path Source = Paths.get(".", "index.mgs");
    public static final Path Header = Paths.get(".", "index.h");

    @Test
    void declaration_header() throws IOException {
        run("const x : I16 = 10;");
        assertEquals("#ifndef __index_header__\n" +
                "#define __index_header__\n" +
                "struct __index_type__ {\n" +
                "\tint x;\n" +
                "}\n" +
                "struct __index_type__ __index_main__();\n" +
                "#endif\n", Files.readString(Header));
    }

    @Test
    void target_source_content() throws IOException {
        runEmpty();
        assertEquals("struct __index_type__ __index_main__(){\n" +
                "\tstruct __index_type__ this={};\n" +
                "\treturn this;\n" +
                "}\n", Files.readString(Target));
    }

    @Test
    void target_header_content() throws IOException {
        runEmpty();
        assertEquals("#ifndef __index_header__\n" +
                "#define __index_header__\n" +
                "struct __index_type__ {\n}\n" +
                "struct __index_type__ __index_main__();\n" +
                "#endif\n", Files.readString(Header));
    }

    @Test
    void target_header() throws IOException {
        runEmpty();
        assertTrue(Files.exists(Header));
    }

    @Test
    void target_source() throws IOException {
        runEmpty();
        assertTrue(Files.exists(Target));
    }

    private void runEmpty() throws IOException {
        run("");
    }

    private void run(String input) throws IOException {
        Files.writeString(Source, input);
        new Application(Source).run();
    }

    @Test
    void no_target() throws IOException {
        new Application(Source).run();
        assertFalse(Files.exists(Target));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Header);
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Source);
    }
}
