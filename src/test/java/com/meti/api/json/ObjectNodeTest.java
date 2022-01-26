package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectNodeTest {
    @Test
    void sub_format() {
        var expected = "{\n\t\"outer\" : {\n\t\t\"inner\" : 5\n\t}\n}";
        var inner = new ObjectNode().add("inner", 5);
        var outer = new ObjectNode().add("outer", inner);
        assertEquals(expected, new JSONFormatter(outer.toString()).format());
    }

    @Test
    void format() {
        var expected = "{\n\t\"value\" : 5\n}";
        var actual = new ObjectNode().add("value", 5);
        var formatted = new JSONFormatter(actual.toString()).format();
        assertEquals(expected, formatted);
    }

    @Test
    void no_format() {
        var expected = "{\"test\":5}";
        var actual = new ObjectNode().add("test", 5).toString();
        assertEquals(expected, actual);
    }
}