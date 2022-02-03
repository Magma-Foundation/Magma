package com.meti.app.compile.stage;

import com.meti.api.collect.java.List;
import com.meti.api.core.F1;
import com.meti.app.compile.common.DefinitionNode;
import com.meti.app.compile.common.Initialization;
import com.meti.app.compile.common.binary.BinaryOperation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.bool.Boolean;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.feature.function.Return;
import com.meti.app.compile.feature.scope.Variable;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.primitive.Primitive;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CMagmaModificationStageTest {
    @Test
    void boolean_child() {
        assertTransforms(new Return(Boolean.False), (F1<Node, java.lang.Boolean, ?>) node -> node.apply(Attribute.Type.Value)
                .asNode()
                .is(Node.Category.Integer));
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
                    .asStreamOfNodes()
                    .foldRight(List.<Node>createList(), List::add);
            return list.first().filter(first -> first.is(Node.Category.Integer)).isPresent() &&
                   list.last().filter(last -> last.is(Node.Category.Integer)).isPresent();
        });
    }

    @Test
    void boolean_declared() {
        var oldIdentity = new Initialization(new RootText("test"), Primitive.Bool, Boolean.True, List.createList());
        assertTransforms(new DefinitionNode(oldIdentity), (F1<Node, java.lang.Boolean, ?>) newIdentity -> newIdentity
                .apply(Attribute.Type.Identity).asNode()
                .apply(Attribute.Type.Value).asNode()
                .is(Node.Category.Integer));
    }

    @Test
    void booleans() {
        assertTransforms(Boolean.True, (F1<Node, java.lang.Boolean, ?>) value -> value.is(Node.Category.Integer));
    }

    @Test
    void line() {
        assertTransforms(new Block.Builder()
                .add(new BinaryOperation(new Variable("="), new IntegerNode(10), new IntegerNode(20)))
                .build(), (F1<Node, java.lang.Boolean, ?>) value -> value.apply(Attribute.Type.Children).asStreamOfNodes()
                .first()
                .filter(child -> child.is(Node.Category.Line))
                .isPresent());
    }
}