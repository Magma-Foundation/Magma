package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRendererTest {

    @Test
    void render() {
        var output = new CRenderer("test", "U16").render();
        assertEquals("unsigned int test(){return 0;}", output);
    }
}