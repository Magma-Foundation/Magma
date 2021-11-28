package com.meti.app;

import org.junit.jupiter.api.Test;

import static com.meti.app.CompiledTest.assertCompile;

public class ImportTest {
    @Test
    void test_native() {
        assertCompile("import native \"windows\";", "#include <windows.h>\n");
    }
}
