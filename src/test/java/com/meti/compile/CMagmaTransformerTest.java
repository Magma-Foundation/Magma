package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.common.bool.Boolean;
import com.meti.compile.common.returns.Return;
import com.meti.compile.node.Node;
import com.meti.core.F1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CMagmaTransformerTest {
    @Test
    void boolean_child() {
        assertTransforms(new Return(Boolean.False), node -> node.apply(Attribute.Type.Value)
                .asNode()
                .is(Node.Type.Integer));
    }

    private void assertTransforms(Node input, F1<Node, java.lang.Boolean, CompileException> predicate) {
        try {
            var transformed = new CMagmaTransformer().transform(input);
            var state = predicate.apply(transformed);
            assertTrue(state);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void booleans() {
        assertTransforms(Boolean.True, value -> value.is(Node.Type.Integer));
    }
}