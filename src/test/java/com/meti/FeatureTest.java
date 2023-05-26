package com.meti;

import com.meti.node.JavaRenderer;
import com.meti.node.MagmaRenderer;
import com.meti.node.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureTest {
    protected static void assertCompile(String input, String expected) {
        var output = new Compiler(input).compile();
        assertEquals(expected, output);
    }

    protected static void assertCompile(Node input, Node output) {
        FeatureTest.assertCompile(
                new JavaRenderer(input).render().orElseThrow(),
                new MagmaRenderer(output).render().orElseThrow());
    }
}
