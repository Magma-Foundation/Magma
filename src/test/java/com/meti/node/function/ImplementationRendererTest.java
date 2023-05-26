package com.meti.node.function;

import com.meti.node.Content;
import com.meti.node.MapNode;
import com.meti.node.NodeAttribute;
import com.meti.node.StringAttribute;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImplementationRendererTest {
    @Test
    void test() {
        var node = new MapNode("implementation", Map.of(
                "name", new StringAttribute("foo"),
                "body", new NodeAttribute(new Content("{}"))
        ));

        var actual = new ImplementationRenderer(node).render().orElseThrow();
        assertEquals("def foo() => {}", actual);
    }
}