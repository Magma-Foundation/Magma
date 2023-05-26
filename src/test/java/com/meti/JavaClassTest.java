package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JavaClassTest {

    @Test
    void render() {
        var node = (Node) new MapNode("class", Map.of(
                "name", new StringAttribute("Test"),
                "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))), new HashMap<Node.Group, List<Object>>())),
                "flags", new ObjectListAttribute(List.of(new Flag[]{Flag.Public}))
        ), new HashMap<Node.Group, List<Object>>());
        assertEquals("public class Test {}", new MagmaRenderer(node).render().orElseThrow());
    }
}