package com.meti;

import com.meti.java.RenderableBuilder;
import org.junit.jupiter.api.Assertions;

public class CompiledTest {
    static void assertCompile(String input, String expected) {
        var actual = Compiler.compile(input);
        Assertions.assertEquals(expected, actual);
    }

    static void assertCompile(RenderableBuilder input, RenderableBuilder output) {
        assertCompile(input.build().render(), output.build().render());
    }
}
