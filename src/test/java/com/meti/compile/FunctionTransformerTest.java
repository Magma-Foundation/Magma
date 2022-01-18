package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.cache.Cache;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Implementation;
import com.meti.compile.common.block.Block;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.compile.node.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionTransformerTest {
    @Test
    void double_inner() throws CompileException, StreamException {
        var child = createFunction("child", new Block());
        var parent = createFunction("parent", new Block());
        var cache = new Cache(new Block(), JavaList.apply(child, parent));
        var grandParent = createFunction("grandParent", cache);

        var output = new FunctionTransformer(grandParent).transform().orElse(EmptyNode.EmptyNode_);
        var outputCache = output.apply(Attribute.Type.Children).asStreamOfNodes1()
                .foldRight(new JavaList<Node>(), JavaList::add);

        assertEquals(child, outputCache.apply(0));
        assertEquals(parent, outputCache.apply(1));
    }

    private Implementation createFunction(String name, Node value) {
        var identity = new EmptyField(new Text(name), Primitive.Void, new JavaList<>());
        return new Implementation(identity, value, new JavaList<>());
    }

    @Test
    void inner() throws CompileException {
        var inner = createFunction("inner", new Block());
        var outer = createFunction("outer", new Cache(new Block(), JavaList.apply(inner)));
        var output = new FunctionTransformer(outer).transform().orElse(EmptyNode.EmptyNode_);
        assertTrue(output.is(Node.Type.Cache));
    }
}