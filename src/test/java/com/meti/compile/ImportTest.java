package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class ImportTest {
    @Test
    void with_quotes() {
        assertCompile("native import \"stdio\"", "#include <stdio.h>\n");
    }

    @Test
    void without_quotes() {
        assertCompile("native import stdio", "#include <stdio.h>\n");
    }
}
