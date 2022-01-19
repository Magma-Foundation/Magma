package com.meti.app.compile.common.invoke;

import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InvocationLexerTest {

    @Test
    void lex() throws CompileException {
        var actual = new InvocationLexer(new RootText("test(() => {})"))
                .lex()
                .orElse(EmptyNode_);
        var expected = new Invocation(new InputNode(new RootText("test")), new InputNode(new RootText("() => {}")));
        assertEquals(actual, expected);
    }
}