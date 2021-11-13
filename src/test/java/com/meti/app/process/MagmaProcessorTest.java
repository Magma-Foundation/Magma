package com.meti.app.process;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaProcessorTest {

    @Test
    void render() {
        var actual = new MagmaRenderer("test", "U16").render();
        assertEquals("def test() : U16 => {return 0;}", actual);
    }
}