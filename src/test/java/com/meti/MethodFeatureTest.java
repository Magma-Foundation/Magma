package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MethodFeatureTest extends FeatureTest {
    @Test
    void test() {
        var method = (Node) new MapNode("method", Map.of(
                "name", new StringAttribute("empty"),
                "flags", new ObjectListAttribute(Collections.<Flag>emptyList())
        ));
        var function = (Node) new MapNode("implementation", Map.of(
                "name", new StringAttribute("empty"),
                "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))))),
                "flags", new ObjectListAttribute(List.of(new Flag[]{}))
        ));

        assertCompile(new MapNode("class", Map.of(
                        "name", new StringAttribute("Test"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{method}))))),
                        "flags", new ObjectListAttribute(List.of(new Flag[]{}))
                )),
                new MapNode("implementation", Map.of(
                        "name", new StringAttribute("Test"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{function}))))),
                        "flags", new ObjectListAttribute(List.of(new Flag[]{Flag.Class}))
                )));
    }
}
