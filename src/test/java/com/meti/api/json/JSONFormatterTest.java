package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONFormatterTest {
    @Test
    void compact() {
        var actual = new JSONFormatter("[]").toString();
        assertEquals("[]", actual);
    }

    @Test
    void compact_inner() {
        var expected = "{\n\t\"value\" : []\n}";
        var actual = new JSONFormatter(new ObjectNode()
                .add("value", new ArrayNode()))
                .toString();
        assertEquals(expected, actual);
    }
}