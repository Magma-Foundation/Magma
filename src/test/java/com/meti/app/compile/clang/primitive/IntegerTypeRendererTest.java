package com.meti.app.compile.clang.primitive;

import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.stage.CompileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerTypeRendererTest {
    @Test
    void test() throws CompileException {
        var type = new IntegerType(false, 16);
        var identity = new EmptyField("x", type);

        var expected = "unsigned int x";
        var actual = new IntegerTypeRenderer(identity)
                .processValid()
                .compute();

        assertEquals(expected, actual);
    }
}