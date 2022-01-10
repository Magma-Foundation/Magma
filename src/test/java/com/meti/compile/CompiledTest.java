package com.meti.compile;

import com.meti.compile.clang.CFormat;
import com.meti.source.Packaging;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompiledTest {
    public static void assertSourceCompile(String input, String output) {
        assertCompile(CFormat.Source, input, output);
    }

    static void assertHeaderCompiles(String input, String output) {
        assertCompile(CFormat.Header, input, output);
    }

    private static void assertCompile(CFormat format, String input, String output) {
        try {
            var package_ = new Packaging("Index", Collections.emptyList());
            var compiler = new MagmaCCompiler(Collections.singletonMap(package_, input));
            var actual = compiler.compile()
                    .get(package_)
                    .apply(format, "");

            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }
}
