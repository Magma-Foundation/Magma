package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectNodeTest {
    @Test
    void unformatted() {
        var expected = "{\"test\":5}";
        var actual = new ObjectNode()
                .append("test", 5)
                .toString();
        assertEquals(expected, actual);
    }
}