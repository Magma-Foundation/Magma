package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.primitive.Primitive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UnionTest {
    @Test
    void isAssignableTo() throws AttributeException {
        var type = Primitive.Bool;
        var root = new Union(type);
        var state = root.isAssignableTo(type);
        assertTrue(state);
    }
}