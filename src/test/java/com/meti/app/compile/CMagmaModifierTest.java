package com.meti.app.compile;

import com.meti.api.collect.java.List;
import com.meti.api.core.F1;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.common.Declaration;
import com.meti.app.compile.common.ValuedField;
import com.meti.app.compile.common.binary.BinaryOperation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.bool.Boolean;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.common.returns.Return;
import com.meti.app.compile.common.variable.Variable;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CMagmaModifierTest {
    @Test
    void boolean_child() {
        assertTransforms(new Return(Boolean.False), (F1<Node, java.lang.Boolean, ?>) node -> node.apply(Attribute.Type.Value)
                .asNode()
                .is(Node.Type.Integer));
    }

    private void assertTransforms(Node input, F1<Node, java.lang.Boolean, ?> predicate) {
        try {
            var transformed = new CMagmaModifier().transformNodeAST(input);
            var state = predicate.apply(transformed);
            assertTrue(state);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void boolean_children() {
        var input = new Block.Builder()
                .add(Boolean.True)
                .add(Boolean.False)
                .build();
        assertTransforms(input, (F1<Node, java.lang.Boolean, ?>) value -> {
            var list = value.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .foldRight(new List<Node>(), List::add);
            return list.first().filter(first -> first.is(Node.Type.Integer)).isPresent() &&
                   list.last().filter(last -> last.is(Node.Type.Integer)).isPresent();
        });
    }

    @Test
    void boolean_declared() {
        var oldIdentity = new ValuedField(new List<>(), new Text("test"), Primitive.Bool, Boolean.True);
        assertTransforms(new Declaration(oldIdentity), (F1<Node, java.lang.Boolean, ?>) newIdentity -> newIdentity
                .apply(Attribute.Type.Identity).asNode()
                .apply(Attribute.Type.Value).asNode()
                .is(Node.Type.Integer));
    }

    @Test
    void booleans() {
        assertTransforms(Boolean.True, (F1<Node, java.lang.Boolean, ?>) value -> value.is(Node.Type.Integer));
    }

    @Test
    void line() {
        assertTransforms(new Block.Builder()
                .add(new BinaryOperation(new Variable("="), new IntegerNode(10), new IntegerNode(20)))
                .build(), (F1<Node, java.lang.Boolean, ?>) value -> value.apply(Attribute.Type.Children).asStreamOfNodes1()
                .first()
                .filter(child -> child.is(Node.Type.Line))
                .isPresent());
    }
}