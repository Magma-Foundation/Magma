package com.meti.compile.common.invoke;

import com.meti.compile.CompileException;
import com.meti.compile.node.Content;
import com.meti.compile.node.Text;
import org.junit.jupiter.api.Test;

import static com.meti.compile.node.EmptyNode.EmptyNode_;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InvocationLexerTest {

    @Test
    void lex() throws CompileException {
        var actual = new InvocationLexer(new Text("test(() => {})"))
                .lex()
                .orElse(EmptyNode_);
        var expected = new Invocation(new Content(new Text("test")), new Content(new Text("() => {}")));
        assertEquals(actual, expected);
    }
}