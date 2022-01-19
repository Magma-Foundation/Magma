package com.meti.app.compile;

import com.meti.api.collect.java.JavaMap;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.source.Packaging;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CompiledTest {
    private static final Packaging Package_ = new Packaging("Index", Collections.emptyList());

    public static void assertDoesNotCompile(String input) {
        assertThrows(CompileException.class, () -> compileImpl(input));
    }

    private static Map<Packaging, Target<String>> compileImpl(String input) throws CompileException {
        var packagingStringMap = Collections.singletonMap(Package_, input);
        var input1 = new JavaMap<>(packagingStringMap);
        var compiler = new CMagmaCompiler(input1);
        return compiler.compile();
    }

    public static void assertHeaderCompiles(String input, String output) {
        try {
            var compile = compileImpl(input);
            var actual = compile.get(Package_).apply(CFormat.Header, "");
            var separator = actual.indexOf("#define");
            var toSplit = actual.indexOf('\n', separator);
            var content = actual.substring(toSplit + 1, actual.length() - "\n#endif\n".length());
            assertEquals(output, content);
        } catch (CompileException e) {
            fail(e);
        }
    }

    public static void assertSourceCompile(String input, String output) {
        try {
            var package_ = new Packaging("Index", Collections.emptyList());
            var compile = compileImpl(input);
            var before = compile.get(package_).apply(CFormat.Source, "");
            var separator = before.indexOf('\"', before.indexOf('\"') + 1);
            var after = before.substring(separator + 1).trim();
            assertEquals(output, after);
        } catch (CompileException e) {
            fail(e);
        }
    }
}
