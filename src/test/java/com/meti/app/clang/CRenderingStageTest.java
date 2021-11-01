package com.meti.app.clang;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.feature.IntegerNode;
import com.meti.app.compile.feature.Return;
import com.meti.app.compile.node.output.CompoundOutput;
import com.meti.app.compile.node.output.NodeOutput;
import com.meti.app.compile.node.output.StringOutput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRenderingStageTest {
    @Test
    void returns() throws CompileException {
        var child = new IntegerNode(420);
        var root = new Return(child);
        var expected = new CompoundOutput(List.of(
                new StringOutput("return "),
                new NodeOutput(child),
                new StringOutput(";")));
        var actual = new CRenderingStage(root).process();
        assertEquals(expected, actual);
    }
}