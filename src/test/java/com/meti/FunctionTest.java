package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    @Test
    void render() {
        var actual = new Abstraction("test", true).render();
        assertEquals("public class def test()", actual);
    }
}