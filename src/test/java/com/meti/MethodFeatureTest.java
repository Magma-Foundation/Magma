package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodFeatureTest extends FeatureTest {
    @Test
    void test() {
        var method = (Node) new MapNode("method", Map.of(
                "name", new StringAttribute("empty"),
                "flags", new ObjectListAttribute(Collections.<Flag>emptyList())
        ), new HashMap<Node.Group, List<Object>>());
        var function = (Node) new MapNode("implementation", Map.of(
                "name", new StringAttribute("empty"),
                "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))), new HashMap<Node.Group, List<Object>>())),
                "flags", new ObjectListAttribute(List.of(new Flag[]{}))
        ), new HashMap<Node.Group, List<Object>>());

        assertCompile(new MapNode("class", Map.of(
                        "name", new StringAttribute("Test"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{method}))), new HashMap<Node.Group, List<Object>>())),
                        "flags", new ObjectListAttribute(List.of(new Flag[]{}))
                ), new HashMap<Node.Group, List<Object>>()),
                new MapNode("implementation", Map.of(
                        "name", new StringAttribute("Test"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{function}))), new HashMap<Node.Group, List<Object>>())),
                        "flags", new ObjectListAttribute(List.of(new Flag[]{Flag.Class}))
                ), new HashMap<Node.Group, List<Object>>()));
    }
}
