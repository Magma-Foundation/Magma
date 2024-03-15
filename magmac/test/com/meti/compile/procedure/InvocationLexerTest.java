package com.meti.compile.procedure;

import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvocationLexerTest {
    @Test
    void test() {
        var actual = new InvocationLexer(new JavaString("runWithSource()")).lex().orElseNull();
        assertEquals(new InvocationNode(new Content("runWithSource", 0)), actual);
    }
}