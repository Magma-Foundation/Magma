package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONFormatterTest {
    @Test
    void simple(){
        var expected = "{\n\t\"value\" : 100\n}";
        var actual = new JSONFormatter(new ObjectNode()
                .add("value", 100))
                .toString();
        assertEquals(expected, actual);
    }

    @Test
    void preserve() {
        var inner = new ObjectNode().add("value", 100);
        var root = new ArrayNode.Builder()
                .addObject(inner)
                .build();
        var expected = """
                [
                \t{
                \t\t"value" : 100
                \t}
                ]""";
        var actual = new JSONFormatter(root).toString();
        assertEquals(expected, actual);
    }

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