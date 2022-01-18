package com.meti.app.compile.common.invoke;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Text;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;
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