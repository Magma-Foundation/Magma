package com.meti.compile;

import com.meti.api.collect.JavaList;
import com.meti.compile.cache.Cache;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Implementation;
import com.meti.compile.common.block.Block;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.compile.node.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionTransformerTest {
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