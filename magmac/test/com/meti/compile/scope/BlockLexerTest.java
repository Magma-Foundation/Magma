package com.meti.compile.scope;

import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockLexerTest {
    @Test
    void rule() {
        assertTrue(BlockLexer.Rule.apply(JavaString.from("{}")).isPresent());
    }
}