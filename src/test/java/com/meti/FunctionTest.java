package com.meti;

import com.meti.node.Abstraction;
import com.meti.node.Definition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    @Test
    void render() {
        var actual = new Abstraction("test", Definition.Flag.Public, Definition.Flag.Class  ).render();
        assertEquals("public class def test()", actual);
    }
}