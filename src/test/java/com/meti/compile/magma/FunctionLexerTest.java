package com.meti.compile.magma;

import com.meti.collect.JavaList;
import com.meti.compile.CompileException;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Implementation;
import com.meti.compile.node.Content;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionLexerTest {

    @Test
    void lambda() throws CompileException {
        var node = new FunctionLexer(new Text("() => {}"))
                .lex();
        var identity = new EmptyField(new JavaList<>(), new Text(""), ImplicitType.ImplicitType_);
        var expected = new Implementation(identity, new JavaList<>(), new Content(new Text("{}")));
        var actual = node.orElse(EmptyNode.EmptyNode_);
        assertEquals(expected, actual);
    }
}