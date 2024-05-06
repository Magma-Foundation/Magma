package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XMLParserTest {
    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void parse(String name) {
        var expected = new MapNode(name);
        var actual = XMLParser.parse("<" + name + "/>");
        assertEquals(expected, actual);
    }
}