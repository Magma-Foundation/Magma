package com.meti.app.compile.extern;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.java.JavaMap;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.stage.CMagmaCompiler;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.source.Packaging;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.meti.app.compile.CompiledTest.assertHeaderCompiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportTest {
    @Test
    void extern() {
        assertHeaderCompiles("extern import stdio", "#include <stdio.h>\n");
    }

    @Test
    void parent() throws CompileException, CollectionException {
        var index = new Packaging("inner", "Index");
        var index1 = Map.of(
                index, "import Parent",
                new Packaging("Parent"), ""
        );

        var output = new CMagmaCompiler(new JavaMap<>(index1)).compile();
        var expected = """      
                #ifndef Index_inner

                #define Index_inner
                #include "../Parent.h"

                #endif
                """;

        var actual = output.apply(index).apply(CFormat.Header, "");
        assertEquals(expected, actual);
    }

    @Test
    void sibling() throws CompileException, CollectionException {
        var index = new Packaging("Index");
        final Map<Packaging, String> index1 = Map.of(
                index, "import Sibling",
                new Packaging("Sibling"), ""
        );
        var output = new CMagmaCompiler(new JavaMap<>(index1)).compile();

        var expected = """   
                #ifndef Index

                #define Index
                #include "Sibling.h"

                #endif
                """;
        var actual = output.apply(index).apply(CFormat.Header, "");

        assertEquals(expected, actual);
    }
}
