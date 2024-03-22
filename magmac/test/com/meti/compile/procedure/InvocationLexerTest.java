package com.meti.compile.procedure;

import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvocationLexerTest {
    @Test
    void test() {
        Lexer lexer = new InvocationLexer(JavaString.from("runWithSource()"));
        var actual = lexer.lex().next().orElseNull();
        assertEquals(new InvocationNode(new Content("runWithSource", 0)), actual);
    }
}