package com.meti.app.compile.stage;

import com.meti.api.collect.java.JavaList;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CMagmaPipelineTest {
    @Test
    void inner_function() throws CompileException, StreamException {
        var innerIdentity = new EmptyField(new RootText("inner"), Primitive.Void);
        var inner = new Implementation(innerIdentity, new Block());

        var outerIdentity = new EmptyField(new RootText("outer"), Primitive.Void);
        var outer = new Implementation(outerIdentity, new Block(inner));

        var expected = Collections.emptyList();
        var actual = JavaList.toNativeList(new CMagmaPipeline(outer)
                .pipe()
                .foldRight(List.<Node>createList(), List::add));
        assertIterableEquals(expected, actual);
    }
}