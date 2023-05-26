package com.meti;

import com.meti.node.Block;
import com.meti.node.Definition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaClassTest {

    @Test
    void render() {
        var node = new JavaClass("Test", new Block(), Definition.Flag.Public);
        assertEquals("public class Test {}", node.render());
    }
}