package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodTest {
    @Test
    void testPublic() {
        var actual = ((Node) new MapNode("method", Map.of(
                "name", new StringAttribute("test"),
                "flags", new ObjectListAttribute(Collections.singletonList(Flag.Public))
        ))).render();
        assertEquals("public void test(){}", actual);
    }

    @Test
    void testPublicStatic() {
        var actual = ((Node) new MapNode("method", Map.of(
                "name", new StringAttribute("test"),
                "flags", new ObjectListAttribute(List.of(Flag.Public, Flag.Static))
        ))).render();
        assertEquals("public static void test(){}", actual);
    }
}