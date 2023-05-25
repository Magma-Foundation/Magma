package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JavaClassTest {

    @Test
    void render() {
        var node = new JavaClass("Test", new Block(), Definition.Flag.Public);
        assertEquals("public class Test {}", node.render());
    }
}