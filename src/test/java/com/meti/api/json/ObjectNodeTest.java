package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectNodeTest {
    @Test
    void formatted() {
        var expected = "{\n\t\"test\" : 5\n}";
        var actual = new ObjectNode()
                .appendObject("test", 5)
                .format();
        assertEquals(expected, actual);
    }

    @Test
    void inner_formatted() {
        var inner = new ObjectNode()
                .appendString("value", "test");

        var expected = "{\n\t\"inner\" : {\n\t\t\"value\" : \"test\"\n\t}\n}";
        var actual = new ObjectNode()
                .appendObject("inner", inner)
                .format();

        assertEquals(expected, actual);
    }

    @Test
    void unformatted() {
        var expected = "{\"test\":5}";
        var actual = new ObjectNode()
                .appendObject("test", 5)
                .toString();
        assertEquals(expected, actual);
    }
}