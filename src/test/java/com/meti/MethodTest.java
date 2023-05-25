package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodTest {
    @Test
    void test() {
        var actual = new Method("test", true).render();
        assertEquals("public void test(){}", actual);
    }
}