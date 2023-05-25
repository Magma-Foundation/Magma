package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaClassTest {

    @Test
    void render() {
        var node = new JavaClass("Test", true);
        assertEquals("public class Test {}", node.render());
    }
}