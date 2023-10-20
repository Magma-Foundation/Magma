package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void removePackage() {
        var output = new Compiler("package test;class Test {}")
                .compile();
        assertEquals("class def Test() => {}", output);
    }
}