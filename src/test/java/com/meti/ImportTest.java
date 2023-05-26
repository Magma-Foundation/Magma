package com.meti;

import com.meti.node.MagmaRenderer;
import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.StringAttribute;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportTest extends FeatureTest {

    @Test
    void importAnother() {
        var value = (Node) new MapNode("import", Map.of(
                "value", new StringAttribute("foo")
        ), new HashMap<Node.Group, List<Object>>());
        assertCompile(value, value);
    }

    @Test
    void importNamespace() {
        assertCompile(new MapNode("import", Map.of(
                "value", new StringAttribute("foo.Bar")
        ), new HashMap<Node.Group, List<Object>>()), new MapNode("import", Map.of(
                "value", new StringAttribute("Bar from foo")
        ), new HashMap<Node.Group, List<Object>>()));
    }

    @Test
    void importMultiple() {
        var first = (Node) new MapNode("import", Map.of(
                "value", new StringAttribute("first")
        ), new HashMap<Node.Group, List<Object>>());
        var second = (Node) new MapNode("import", Map.of(
                "value", new StringAttribute("second")
        ), new HashMap<Node.Group, List<Object>>());
        assertCompile(new MagmaRenderer(first).render().orElseThrow() + "\n" + new MagmaRenderer(second).render().orElseThrow(),
                new MagmaRenderer(first).render().orElseThrow() + "\n" + new MagmaRenderer(second).render().orElseThrow());
    }
}