package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassTest extends FeatureTest {
    @Test
    void simple() {
        var input = new MapNode("class", Map.of(
                "name", new StringAttribute("Test"),
                "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute()))),
                "flags", new ObjectListAttribute()
        ), Map.of(
                Node.Group.Node, List.of("body")
        ));

        var output = new MapNode("implementation", Map.of(
                "name", new StringAttribute("Test"),
                "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute()))),
                "flags", new ObjectListAttribute(List.of(Flag.Class))
        ),Map.of(
                Node.Group.Node, List.of("body")
        ));

        assertCompile(input, output);
    }

    @Test
    void simpleAnother() {
        assertCompile(new MapNode("class", Map.of(
                        "name", new StringAttribute("Bar"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))), new HashMap<Node.Group, List<Object>>())),
                        "flags", new ObjectListAttribute(List.of(new Flag[]{}))
                ), new HashMap<Node.Group, List<Object>>()),
                new MapNode("implementation", Map.of(
                        "name", new StringAttribute("Bar"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))), new HashMap<Node.Group, List<Object>>())),
                        "flags", new ObjectListAttribute(List.of(Flag.Class))
                ), new HashMap<Node.Group, List<Object>>()));
    }

}
