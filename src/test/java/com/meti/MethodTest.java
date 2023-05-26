package com.meti;

import com.meti.node.Definition.Flag;
import com.meti.node.Method;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodTest {
    @Test
    void testPublic() {
        var actual = new Method("test", Collections.singletonList(Flag.Public)).render();
        assertEquals("public void test(){}", actual);
    }

    @Test
    void testPublicStatic() {
        var actual = new Method("test", List.of(Flag.Public, Flag.Static)).render();
        assertEquals("public static void test(){}", actual);
    }
}