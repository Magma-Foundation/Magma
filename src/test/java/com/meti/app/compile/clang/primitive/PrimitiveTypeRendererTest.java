package com.meti.app.compile.clang.primitive;

import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.primitive.Primitive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimitiveTypeRendererTest {
    @Test
    void declared() throws AttributeException {
        var identity = new Declaration("test", Primitive.Void);

        var expected = "void test";
        var actual = new PrimitiveTypeRenderer(identity)
                .processImpl()
                .compute();

        assertEquals(expected, actual);
    }
}