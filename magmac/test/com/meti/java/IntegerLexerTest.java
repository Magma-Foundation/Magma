package com.meti.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerLexerTest {
    @Test
    void valid(){
        assertTrue(new IntegerLexer("123")
                .lex()
                .isPresent());
    }

    @Test
    void invalid() {
        assertFalse(new IntegerLexer("test")
                .lex()
                .isPresent());
    }
}