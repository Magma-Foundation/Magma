package com.meti.api.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONFormatterTest {
    @Test
    void empty(){
        var actual = new JSONFormatter("[]").toString();
        assertEquals("[]", actual);
    }
}