package com.meti.compile;

import com.meti.compile.clang.CFormat;
import com.meti.source.Packaging;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportTest {
    @Test
    void parent() throws CompileException {
        var index = new Packaging("inner", "Index");
        var output = new MagmaCCompiler(Map.of(
                index, "import Parent",
                new Packaging("Parent"), ""
        )).compile();

        var expected = "#include \"../Parent.h\"";
        var actual = output.get(index).apply(CFormat.Header, "");

        assertEquals(expected, actual);
    }

    @Test
    void sibling() throws CompileException {
        var index = new Packaging("Index");
        var output = new MagmaCCompiler(Map.of(
                index, "import Sibling",
                new Packaging("Sibling"), ""
        )).compile();

        var expected = "#include \"Sibling.h\"";
        var actual = output.get(index).apply(CFormat.Header, "");

        assertEquals(expected, actual);
    }
}
