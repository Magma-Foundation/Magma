package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

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
