package com.meti;

import com.meti.Definition.Flag;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodTest {
    @Test
    void testPublic() {
        var actual = new Method("test", Collections.singleton(Flag.Public)).render();
        assertEquals("public void test(){}", actual);
    }

    @Test
    void testPublicStatic() {
        var actual = new Method("test", Set.of(Flag.Public, Flag.Static)).render();
        assertEquals("public static void test(){}", actual);
    }
}