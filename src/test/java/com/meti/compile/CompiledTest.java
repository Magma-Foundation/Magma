package com.meti.compile;

import com.meti.compile.clang.CFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompiledTest {
    public static void assertCompile(String input, String output) {
        try {
            var compiler = new MagmaCCompiler(input);
            var actual = compiler.compile().apply(CFormat.Source, "");
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }
}
