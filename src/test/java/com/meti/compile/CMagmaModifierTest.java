package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.common.Declaration;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Implementation;
import com.meti.compile.common.ValuedField;
import com.meti.compile.common.binary.BinaryOperation;
import com.meti.compile.common.block.Block;
import com.meti.compile.common.bool.Boolean;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.common.returns.Return;
import com.meti.compile.common.variable.Variable;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.compile.node.Text;
import com.meti.core.F1;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CMagmaModifierTest {
    @Test
    void boolean_child() {
        assertTransforms(new Return(Boolean.False), node -> node.apply(Attribute.Type.Value)
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
        assertTransforms(input, value -> {
            var list = value.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .foldRight(new JavaList<Node>(), JavaList::add);
            return list.first().filter(first -> first.is(Node.Type.Integer)).isPresent() &&
                   list.last().filter(last -> last.is(Node.Type.Integer)).isPresent();
        });
    }

    @Test
    void boolean_declared() {
        var oldIdentity = new ValuedField(Collections.emptySet(), new Text("test"), Primitive.Bool, Boolean.True);
        assertTransforms(new Declaration(oldIdentity), newIdentity -> newIdentity
                .apply(Attribute.Type.Identity).asNode()
                .apply(Attribute.Type.Value).asNode()
                .is(Node.Type.Integer));
    }

    @Test
    void booleans() {
        assertTransforms(Boolean.True, value -> value.is(Node.Type.Integer));
    }

    @Test
    void inner() {
        var identity = new EmptyField(Collections.emptySet(), new Text("inner"), Primitive.Void);
        var function = new Implementation(identity, Collections.emptySet(), new Block());
        var input = new Block(JavaList.apply(function));
        var output = new Cache(new Block(), JavaList.apply(function));
        assertTransforms(input, output);
    }

    private void assertTransforms(Node input, Node output) {
        try {
            var actual = new CMagmaModifier().transformNodeAST(input);
            assertEquals(output, actual);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void line() {
        assertTransforms(new Block.Builder()
                .add(new BinaryOperation(new Variable("="), new IntegerNode(10), new IntegerNode(20)))
                .build(), value -> value.apply(Attribute.Type.Children).asStreamOfNodes1()
                .first()
                .filter(child -> child.is(Node.Type.Line))
                .isPresent());
    }
}