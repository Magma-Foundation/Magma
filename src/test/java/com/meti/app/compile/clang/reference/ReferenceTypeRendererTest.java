package com.meti.app.compile.clang.reference;

import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.ReferenceType;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.stage.CompileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReferenceTypeRendererTest {
    @Test
    void nominal() throws CompileException {
        var innerType = new IntegerType(false, 16);
        var outerType = new ReferenceType(innerType);
        var identity = new EmptyField("x", outerType);
        var expected = new EmptyField("*x", innerType);
        var actual = new ReferenceTypeRenderer(identity).processImpl();
        assertEquals(expected, actual);
    }
}