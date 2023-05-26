package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    @Test
    void render() {
        Node node = ((Node) new MapNode("abstraction", Map.of(
                "name", new StringAttribute("test"),
                "flags", new ObjectListAttribute(List.of(new Flag[]{Flag.Public, Flag.Class}))
        ), new HashMap<Node.Group, List<Object>>()));
        var actual = new MagmaRenderer(node).render().orElseThrow();
        assertEquals("public class def test()", actual);
    }
}