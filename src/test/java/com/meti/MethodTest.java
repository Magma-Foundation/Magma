package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodTest {
    @Test
    void testPublic() {
        Node node = ((Node) new MapNode("method", Map.of(
                "name", new StringAttribute("test"),
                "flags", new ObjectListAttribute(Collections.singletonList(Flag.Public))
        ), new HashMap<Node.Group, List<Object>>()));
        var actual = new MagmaRenderer(node).render().orElseThrow();
        assertEquals("public void test(){}", actual);
    }

    @Test
    void testPublicStatic() {
        Node node = ((Node) new MapNode("method", Map.of(
                "name", new StringAttribute("test"),
                "flags", new ObjectListAttribute(List.of(Flag.Public, Flag.Static))
        ), new HashMap<Node.Group, List<Object>>()));
        var actual = new MagmaRenderer(node).render().orElseThrow();
        assertEquals("public static void test(){}", actual);
    }
}