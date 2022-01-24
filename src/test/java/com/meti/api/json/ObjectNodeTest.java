package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectNodeTest {
    @Test
    void formattedInner() {
        var expected = "{\n\t\"test\" : 5\n}";
        var actual = new ObjectNode()
                .append("test", 5)
                .format();
        assertEquals(expected, actual);
    }

    @Test
    void unformatted() {
        var expected = "{\"test\":5}";
        var actual = new ObjectNode()
                .append("test", 5)
                .toString();
        assertEquals(expected, actual);
    }
}