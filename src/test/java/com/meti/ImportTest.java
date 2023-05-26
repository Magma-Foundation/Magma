package com.meti;

import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.StringAttribute;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportTest extends FeatureTest {

    @Test
    void importAnother() {
        var value = (Node) new MapNode("import", Map.of(
                "value", new StringAttribute("foo")
        ));
        assertCompile(value, value);
    }

    @Test
    void importNamespace() {
        assertCompile(new MapNode("import", Map.of(
                "value", new StringAttribute("foo.Bar")
        )), new MapNode("import", Map.of(
                "value", new StringAttribute("Bar from foo")
        )));
    }

    @Test
    void importMultiple() {
        var first = (Node) new MapNode("import", Map.of(
                "value", new StringAttribute("first")
        ));
        var second = (Node) new MapNode("import", Map.of(
                "value", new StringAttribute("second")
        ));
        assertCompile(first.render() + "\n" + second.render(),
                first.render() + "\n" + second.render());
    }
}