package com.meti.app.compile.function;

import com.meti.api.collect.java.List;
import com.meti.app.compile.cache.Cache;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.node.EmptyNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionTransformerTest {
    private Implementation createFunction(String name, Node value) {
        var identity = new EmptyField(new RootText(name), Primitive.Void, List.createList());
        return new Implementation(identity, value, List.createList());
    }

    @Test
    void inner() throws CompileException {
        var inner = createFunction("inner", new Block());
        var outer = createFunction("outer", new Cache(new Block(), List.apply(inner)));
        var output = new FunctionTransformer(outer).process().orElse(EmptyNode.EmptyNode_);
        assertTrue(output.is(Node.Type.Cache));
    }
}