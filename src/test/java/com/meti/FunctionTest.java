package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    @Test
    void render() {
        var actual = ((Node) new MapNode("abstraction", Map.of(
                "name", new StringAttribute("test"),
                "flags", new ObjectListAttribute(List.of(new Flag[]{Flag.Public, Flag.Class}))
        ))).render();
        assertEquals("public class def test()", actual);
    }
}