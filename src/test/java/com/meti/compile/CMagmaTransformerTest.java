package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.common.block.Block;
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
    void boolean_children() {
        var input = new Block.Builder()
                .add(Boolean.True)
                .add(Boolean.False)
                .complete();
        assertTransforms(input, value -> {
            try {
                var list = value.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .foldRight(new JavaList<Node>(), JavaList::add);
                return list.first().filter(first -> first.is(Node.Type.Integer)).isPresent() &&
                       list.last().filter(last -> last.is(Node.Type.Integer)).isPresent();
            } catch (StreamException e) {
                return false;
            }
        });
    }

    @Test
    void booleans() {
        assertTransforms(Boolean.True, value -> value.is(Node.Type.Integer));
    }
}