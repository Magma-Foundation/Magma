package com.meti.compile;

import com.meti.compile.cache.Cache;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Field;
import com.meti.compile.common.Implementation;
import com.meti.compile.common.block.Block;
import com.meti.compile.node.Primitive;
import com.meti.compile.node.Text;
import com.meti.source.Packaging;
import org.junit.jupiter.api.Test;

import static com.meti.compile.node.EmptyNode.EmptyNode_;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CMagmaPipelineTest {

    @Test
    void perform() throws CompileException {
        var identity = new EmptyField(new Text("test"), Primitive.Void, Field.Flag.Def);
        var impl = new Implementation(identity, new Block());
        var root = new Block(impl);

        var expected = new Cache(new Block(EmptyNode_), impl);
        var actual = new CMagmaPipeline(new Packaging("Index")).perform(root);

        assertEquals(expected, actual);
    }
}