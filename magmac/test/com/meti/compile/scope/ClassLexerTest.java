package com.meti.compile.scope;

import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassLexerTest {
    @Test
    void rule() {
        assertTrue(ClassLexer.RULE.apply(JavaString.from("Test {}")).isPresent());
    }
}