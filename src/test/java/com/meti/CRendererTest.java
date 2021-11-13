package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRendererTest {
    @Test
    void testFunction() {
        var output = new CRenderer("test", "U16", 0).render();
        assertEquals("unsigned int test(){return 0;}", output);
    }

    @Test
    void testFunctionBody() {
        var output = new CRenderer("test", "I16", 420).render();
        assertEquals("int test(){return 420;}", output);
    }
}