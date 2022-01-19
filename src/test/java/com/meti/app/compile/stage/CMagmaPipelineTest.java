package com.meti.app.compile.stage;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.cache.Cache;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.text.RootText;
import com.meti.app.source.Packaging;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CMagmaPipelineTest {

    @Test
    void perform() throws CompileException, StreamException {
        var identity = new EmptyField(new RootText("test"), Primitive.Void, Field.Flag.Def);
        var impl = new Implementation(identity, new Block());
        var root = new Block(impl);

        var expected = new Cache(new Block(EmptyNode_), impl);
        var actual = new CMagmaPipeline(new Packaging("Index"), List.apply(root))
                .apply()
                .head();

        assertEquals(expected, actual);
    }
}