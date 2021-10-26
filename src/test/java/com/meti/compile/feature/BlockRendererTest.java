package com.meti.compile.feature;

import com.meti.compile.node.output.CompoundOutput;
import com.meti.compile.node.output.EmptyOutput;
import com.meti.compile.node.output.NodeOutput;
import com.meti.compile.node.output.StringOutput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockRendererTest {

    @Test
    void renderDefined() {
        var line = new Return(new IntegerNode(420));
        var expected = new CompoundOutput(List.of(
                new StringOutput("{"),
                new NodeOutput(line),
                new StringOutput("}")));
        var actual = new BlockRenderer(new Block(List.of(line))).process()
                .orElse(new EmptyOutput());
        assertEquals(expected, actual);
    }
}