package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectNodeTest {
    @Test
    void sub_format() {
        var expected = "{\n\t\"outer\" : {\n\t\t\"inner\" : 5\n\t}\n}";
        var inner = new ObjectNode().append("inner", 5);
        var outer = new ObjectNode().append("outer", inner);
        assertEquals(expected, outer.format());
    }

    @Test
    void format() {
        var expected = "{\n\t\"value\":5\n}";
        var actual = new ObjectNode().append("value", 5);
        var formatted = actual.format();
        assertEquals(expected, formatted);
    }

    @Test
    void no_format() {
        var expected = "{\"test\":5}";
        var actual = new ObjectNode().append("test", 5).toString();
        assertEquals(expected, actual);
    }
}